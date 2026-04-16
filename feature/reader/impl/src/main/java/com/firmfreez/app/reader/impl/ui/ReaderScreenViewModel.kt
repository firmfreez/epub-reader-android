package com.firmfreez.app.reader.impl.ui


import com.firmfreez.app.common.domain.models.errors.AppThrowable
import com.firmfreez.app.common.domain.models.result_wrapper.handleFail
import com.firmfreez.app.common.domain.models.result_wrapper.handleSuccess
import com.firmfreez.app.common.domain.repositories.ErrorsRepository
import com.firmfreez.app.common.ui.view_model.BaseViewModel
import com.firmfreez.app.common.usecases.TimerUseCase
import com.firmfreez.app.di.domain.CoroutineDispatchersType
import com.firmfreez.app.di.domain.CoroutineQualifiers
import com.firmfreez.app.reader.impl.domain.GetPublicationByBookIdUseCase
import com.firmfreez.app.reader.impl.domain.OpenBookForReadingUseCase
import com.firmfreez.app.reader.impl.domain.RemovePublicationByBookIdUseCase
import com.firmfreez.app.reader.impl.domain.SetLastLocatorPositionUseCase
import com.firmfreez.app.reader.impl.ui.models.Action
import com.firmfreez.app.reader.impl.ui.models.UiEvent
import com.firmfreez.app.reader.impl.ui.models.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Provided
import org.readium.r2.shared.publication.Locator
import org.readium.r2.shared.publication.Publication
import kotlin.time.Duration.Companion.seconds

@KoinViewModel
class ReaderScreenViewModel(
    @Provided @param:CoroutineQualifiers(CoroutineDispatchersType.Io)
    private val dispatcherIo: CoroutineDispatcher,
    @Provided private val errorsRepository: ErrorsRepository,
    @Provided private val openBookForReading: OpenBookForReadingUseCase,
    @Provided private val removePublicationByBookId: RemovePublicationByBookIdUseCase,
    @Provided private val getPublicationByBookId: GetPublicationByBookIdUseCase,
    @Provided private val timerUseCase: TimerUseCase,
    @Provided private val setLastLocatorPosition: SetLastLocatorPositionUseCase,
) : BaseViewModel<UiState, UiEvent>() {

    private var overlayTimerJob: Job? = null

    override val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.empty())

    fun onAction(action: Action) {
        when (action) {
            is Action.OnBackButtonClick -> emitEventDebounced(UiEvent.ScreenUiEvent.NavigateUp)
            is Action.InitBookId -> onInitBookIdAction(id = action.bookId)
            is Action.UpdatePageData -> onUpdatePageDataAction(
                currentPage = action.currentPage,
                totalPages = action.totalPages,
                locator = action.locator
            )

            is Action.OnPageClicked -> onPageClickedAction()
            is Action.OnNextPageButtonClicked -> onNextPageClickedAction()
            is Action.OnPrevPageButtonClicked -> onPrevPageClickedAction()
            is Action.FailedToOpenPublication -> onFailedToOpenPublication(message = action.message)
            is Action.SetBookIsReady -> updateUiState { copy(bookIsReady = action.isReady) }
        }
    }

    fun getPublication(bookId: String): Publication? {
        val publication = getPublicationByBookId(bookId = bookId)
        return publication
    }

    private fun onFailedToOpenPublication(message: String) {
        errorsRepository.emit(AppThrowable.custom(message))
        emitEvent(UiEvent.ScreenUiEvent.NavigateUp)
    }

    private fun onPrevPageClickedAction() {
        showOverlay()
        emitEvent(UiEvent.ReaderUiEvent.GoPrevPage)
    }

    private fun onNextPageClickedAction() {
        showOverlay()
        emitEvent(UiEvent.ReaderUiEvent.GoNextPage)
    }

    private fun onPageClickedAction() {
        if (uiState.value.isOverlayShown) {
            updateUiState {
                copy(isOverlayShown = false)
            }
            overlayTimerJob?.cancel()
            return
        }

        showOverlay()
    }

    private fun showOverlay() {
        updateUiState {
            copy(isOverlayShown = true)
        }
        overlayTimerJob?.cancel()
        overlayTimerJob = launchSafely(context = dispatcherIo) {
            timerUseCase.invoke(5.seconds, 1.seconds).collect {
                if (it <= 1.seconds) {
                    updateUiState {
                        copy(
                            isOverlayShown = false
                        )
                    }
                }
            }
        }
    }

    private fun onUpdatePageDataAction(
        currentPage: Int,
        totalPages: Int,
        locator: Locator
    ) {
        updateUiState {
            copy(
                currentPage = currentPage,
                totalPages = totalPages
            )
        }

        val bookId = uiState.value.currentBookId ?: return
        launchSafely(context = dispatcherIo) {
            setLastLocatorPosition(bookId = bookId, locator = locator)
        }
    }


    private fun onInitBookIdAction(id: String) {
        launchSafely(context = dispatcherIo) {
            uiState.value.currentBookId?.let {
                removePublicationByBookId(bookId = it)
            }
            updateUiState {
                UiState.empty()
            }

            openBookForReading(bookId = id)
                .handleFail {
                    errorsRepository.emit(it)
                    emitEvent(UiEvent.ScreenUiEvent.NavigateUp)
                }.handleSuccess {
                    updateUiState {
                        copy(
                            currentBookId = id,
                            initialLocator = it.locator
                        )
                    }
                }
        }
    }
}