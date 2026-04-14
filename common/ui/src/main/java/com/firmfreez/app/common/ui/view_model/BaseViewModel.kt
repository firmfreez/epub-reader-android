package com.firmfreez.app.common.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firmfreez.app.core.utils.extensions.debounceFlowReversed
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseViewModel<State, Event> : ViewModel() {

    private val defaultErrorHandler = CoroutineExceptionHandler { _, e -> onError(e) }

    protected abstract val _uiState: MutableStateFlow<State>
    val uiState get() = _uiState.asStateFlow()

    private val _uiEvents = Channel<Event>(capacity = Channel.CONFLATED)
    val uiEvents = _uiEvents.receiveAsFlow()

    private val _debouncedUiEvents = Channel<Event>(capacity = Channel.CONFLATED)
    val debouncedUiEvents =
        _debouncedUiEvents.receiveAsFlow().debounceFlowReversed(EVENT_DEBOUNCE_DURATION)

    protected open fun onLoading(inProgress: Boolean) {}

    protected open fun onError(exception: Throwable) {}

    protected fun launchSafely(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        onLoading: (Boolean) -> Unit = ::onLoading,
        onError: (Throwable) -> Unit = ::onError,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return viewModelScope.launch(context + defaultErrorHandler, start) {
            try {
                onLoading(true)
                block()
            } catch (e: Throwable) {
                if (e is CancellationException) throw e
                e.printStackTrace()
                onError(e)
            } finally {
                onLoading(false)
            }
        }
    }

    @JvmName("replaceIfStateIsTo")
    protected inline fun <reified Old : State, reified New : State> replaceIfStateIs(
        crossinline afterUpdate: (currentState: New) -> Unit = {},
        newState: (currentState: Old) -> New,
    ) {
        updateState(
            afterUpdate = { state ->
                if (state is New) afterUpdate(state)
            },
        ) { currentState ->
            if (currentState is Old) {
                newState(currentState)
            } else {
                currentState
            }
        }
    }

    protected inline fun <reified T : State> replaceIfStateIs(
        crossinline afterUpdate: (currentState: T) -> Unit = {},
        newState: (currentState: T) -> T,
    ) {
        updateState(
            afterUpdate = { state ->
                if (state is T) afterUpdate(state)
            },
        ) { currentState ->
            if (currentState is T) {
                newState(currentState)
            } else {
                currentState
            }
        }
    }

    protected inline fun updateState(
        afterUpdate: (State) -> Unit = {},
        function: (State) -> State,
    ) {
        _uiState.update(function)
        afterUpdate(_uiState.value)
    }

    protected fun updateUiState(function: State.() -> State) {
        _uiState.update(function)
    }

    protected fun emitEvent(event: Event): ChannelResult<Unit> = _uiEvents.trySend(event)

    protected fun emitEventDebounced(event: Event): ChannelResult<Unit> =
        _debouncedUiEvents.trySend(event)

    companion object {
        private const val EVENT_DEBOUNCE_DURATION = 2000L
    }
}