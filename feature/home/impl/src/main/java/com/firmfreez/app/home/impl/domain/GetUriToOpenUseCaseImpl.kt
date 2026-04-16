package com.firmfreez.app.home.impl.domain

import com.firmfreez.app.common.domain.repositories.AppPreferencesRepository
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [GetUriToOpenUseCase::class])
class GetUriToOpenUseCaseImpl(
    @Provided private val appPreferencesRepository: AppPreferencesRepository
) : GetUriToOpenUseCase {

    override suspend fun invoke(): String? {
        return appPreferencesRepository.uriToOpen.firstOrNull()
    }
}
