package com.firmfreez.app.common.domain.repositories

import kotlinx.coroutines.flow.Flow

interface ErrorsRepository {

    val errors: Flow<Throwable>
    val localizedErrors: Flow<String>

    fun tryEmit(error: Throwable)
    fun emit(error: Throwable)
}
