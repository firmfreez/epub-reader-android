package com.firmfreez.app.splash.impl.ui

import com.firmfreez.app.common.domain.models.errors.AppThrowable
import com.firmfreez.app.common.domain.repositories.ErrorsRepository
import com.firmfreez.app.common.domain.repositories.LoggerRepository
import com.firmfreez.app.common.ui.view_model.BaseViewModel
import com.firmfreez.app.common.usecases.app.IsFirstAppLaunchUseCase
import com.firmfreez.app.di.domain.CoroutineDispatchersType
import com.firmfreez.app.di.domain.CoroutineQualifiers
import com.firmfreez.app.splash.impl.ui.models.UiEvent
import com.firmfreez.app.splash.impl.ui.models.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Provided

private const val TAG = "SplashViewModel"

@KoinViewModel
class SplashViewModel(
    @Provided private val isFirstAppLaunchUseCase: IsFirstAppLaunchUseCase,
    @Provided @param:CoroutineQualifiers(CoroutineDispatchersType.Io)
    private val dispatcherIo: CoroutineDispatcher,
    @Provided private val logger: LoggerRepository,
    @Provided private val errorsRepository: ErrorsRepository,
) : BaseViewModel<UiState, UiEvent>() {

    override val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState)

    init {
        launchSafely(context = dispatcherIo) {
            if (isFirstAppLaunchUseCase()) {
                logger.d(TAG, "This is user first App launch!")
                errorsRepository.emit(AppThrowable.custom("This is user first App launch!"))
            } else {
                logger.d(TAG, "This is not user first App launch!")
                errorsRepository.emit(AppThrowable.custom("This is not user first App launch!"))
            }
        }

        launchSafely(context = dispatcherIo) {
            delay(1000)

            emitEvent(UiEvent.OpenHomeScreen)
        }
    }
}
