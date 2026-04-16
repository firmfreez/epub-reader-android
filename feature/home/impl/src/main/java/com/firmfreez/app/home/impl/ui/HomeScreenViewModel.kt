package com.firmfreez.app.home.impl.ui


import android.net.Uri
import com.firmfreez.app.common.domain.models.errors.AppThrowable
import com.firmfreez.app.common.domain.models.result_wrapper.handleFail
import com.firmfreez.app.common.domain.repositories.ErrorsRepository
import com.firmfreez.app.common.domain.repositories.LoggerRepository
import com.firmfreez.app.common.ui.view_model.BaseViewModel
import com.firmfreez.app.di.domain.CoroutineDispatchersType
import com.firmfreez.app.di.domain.CoroutineQualifiers
import com.firmfreez.app.home.impl.domain.DeleteBookUseCase
import com.firmfreez.app.home.impl.domain.ImportBookUseCase
import com.firmfreez.app.home.impl.domain.ObserveBooksUseCase
import com.firmfreez.app.home.impl.ui.models.Action
import com.firmfreez.app.home.impl.ui.models.UiEvent
import com.firmfreez.app.home.impl.ui.models.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class HomeScreenViewModel(
    @Provided @param:CoroutineQualifiers(CoroutineDispatchersType.Io)
    private val dispatcherIo: CoroutineDispatcher,
    @Provided private val errorsRepository: ErrorsRepository,
    @Provided private val loggerRepository: LoggerRepository,

    @Provided private val observeBooks: ObserveBooksUseCase,
    @Provided private val importBook: ImportBookUseCase,
    @Provided private val deleteBook: DeleteBookUseCase,
) : BaseViewModel<UiState, UiEvent>() {

    override val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.empty())

    init {
        launchSafely(context = dispatcherIo) {
            observeBooks().collect { booksList ->
                updateUiState {
                    copy(books = booksList)
                }
            }
        }
    }

    fun onAction(action: Action) {
        when (action) {
            is Action.OnBookPicked -> onBookPickedAction(uri = action.uri)
            is Action.ShowError -> errorsRepository.emit(AppThrowable.custom(action.text))
            is Action.OnOpenBookClick -> emitEventDebounced(UiEvent.OpenDocumentPicker)
            is Action.OnBookClicked -> onBookClickedAction(id = action.id)
            is Action.OnBookDeleteClicked -> onBookDeleteClickedAction(id = action.id)
        }
    }

    private fun onBookDeleteClickedAction(id: String) {
        launchSafely(context = dispatcherIo) {
            deleteBook(id = id)
                .handleFail(errorsRepository::emit)
        }
    }

    private fun onBookClickedAction(id: String) {
        emitEventDebounced(UiEvent.OpenDocumentReader(bookId = id))
    }

    private fun onBookPickedAction(uri: Uri) {
        launchSafely(context = dispatcherIo) {
            importBook(uri = uri)
                .handleFail(errorsRepository::emit)
        }
    }
}