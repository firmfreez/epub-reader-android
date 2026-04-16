package com.firmfreez.epubreader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firmfreez.app.common.domain.repositories.AppPreferencesRepository
import com.firmfreez.app.common.domain.repositories.ErrorsRepository
import kotlinx.coroutines.launch
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class MainActivityViewModel(
    @Provided private val errorsRepository: ErrorsRepository,
    @Provided private val appPreferencesRepository: AppPreferencesRepository,
): ViewModel() {

    val errors = errorsRepository.localizedErrors

    val uriToOpen = appPreferencesRepository.uriToOpen

    fun setUriToOpen(uri: String) {
        viewModelScope.launch {
            appPreferencesRepository.setUriToOpen(uri = uri)
        }
    }
}
