package com.firmfreez.app.home.impl.domain

import com.firmfreez.app.common.domain.repositories.AppPreferencesRepository
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [ClearUriToOpenUseCase::class])
class ClearUriToOpenUseCaseImpl(
    @Provided private val appPreferencesRepository: AppPreferencesRepository
) : ClearUriToOpenUseCase {

    override suspend fun invoke() {
        appPreferencesRepository.setUriToOpen(null)
    }
}