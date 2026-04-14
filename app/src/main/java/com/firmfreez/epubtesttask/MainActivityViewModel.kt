package com.firmfreez.epubtesttask

import androidx.lifecycle.ViewModel
import com.firmfreez.app.common.domain.repositories.ErrorsRepository
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class MainActivityViewModel(
    @Provided private val errorsRepository: ErrorsRepository
): ViewModel() {

    val errors = errorsRepository.localizedErrors
}