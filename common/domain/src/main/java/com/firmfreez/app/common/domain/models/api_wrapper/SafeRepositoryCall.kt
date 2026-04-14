package com.firmfreez.app.common.domain.models.api_wrapper

import kotlin.coroutines.cancellation.CancellationException

interface SafeRepositoryCall {

    val errorHandler: ErrorHandler

    suspend fun <T> resultOf(request: suspend () -> T): ResultOf<T> {
        return try {
            ResultOf.Success(request())
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
            ResultOf.Fail(errorHandler.handleError(e))
        }
    }
}
