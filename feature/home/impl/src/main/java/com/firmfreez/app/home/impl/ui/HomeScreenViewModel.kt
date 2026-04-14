package com.firmfreez.app.home.impl.ui


import android.net.Uri
import com.firmfreez.app.common.domain.models.errors.AppThrowable
import com.firmfreez.app.common.domain.repositories.ErrorsRepository
import com.firmfreez.app.common.domain.repositories.LoggerRepository
import com.firmfreez.app.common.ui.view_model.BaseViewModel
import com.firmfreez.app.home.impl.ui.models.Action
import com.firmfreez.app.home.impl.ui.models.UiEvent
import com.firmfreez.app.home.impl.ui.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class HomeScreenViewModel(
    @Provided private val errorsRepository: ErrorsRepository,
    @Provided private val loggerRepository: LoggerRepository
) : BaseViewModel<UiState, UiEvent>() {

    override val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState)

    fun onAction(action: Action) {
        when (action) {
            is Action.OnBookPicked -> onBookPickedAction(uri = action.uri)
            is Action.ShowError -> errorsRepository.emit(AppThrowable.custom(action.text))
            is Action.OnOpenBookClick -> emitEventDebounced(UiEvent.OpenDocumentPicker)
        }
    }

    private fun onBookPickedAction(uri: Uri) {
        loggerRepository.d("KEK", "Получили URI: $uri")
    }
}