package com.firmfreez.app.common.data.usecases.app

import com.firmfreez.app.common.domain.repositories.AppPreferencesRepository
import com.firmfreez.app.common.usecases.app.IsFirstAppLaunchUseCase
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [IsFirstAppLaunchUseCase::class])
class IsFirstAppLaunchUseCaseImpl(
    @Provided private val appPreferencesRepository: AppPreferencesRepository
) : IsFirstAppLaunchUseCase {

    override suspend fun invoke(): Boolean {
        val isFirstLaunch = appPreferencesRepository.isFirstAppLaunch()
        appPreferencesRepository.registerFirstAppLaunch()
        return isFirstLaunch
    }
}
