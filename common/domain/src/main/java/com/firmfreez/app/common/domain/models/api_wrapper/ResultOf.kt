package com.firmfreez.app.common.domain.models.api_wrapper

import com.firmfreez.app.common.domain.models.errors.AppThrowable

sealed class ResultOf<out T> {
    data class Success<out R>(val data: R) : ResultOf<R>()
    data class Fail(val cause: AppThrowable) : ResultOf<Nothing>()
}

inline fun <T> ResultOf<T>.handle(
    onError: (AppThrowable) -> Unit,
    onSuccess: (T) -> Unit,
): ResultOf<T> {
    when (this) {
        is ResultOf.Fail -> onError(cause)
        is ResultOf.Success -> onSuccess(data)
    }
    return this
}

inline fun <T> ResultOf<T>.handleSuccess(
    onSuccess: (T) -> Unit,
): ResultOf<T> {
    if (this is ResultOf.Success) onSuccess(data)
    return this
}

inline fun <T> ResultOf<T>.handleFail(
    onError: (AppThrowable) -> Unit,
): ResultOf<T> {
    if (this is ResultOf.Fail) onError(cause)
    return this
}

inline fun <T, R> ResultOf<T>.mapHandling(
    onError: (AppThrowable) -> R,
    onSuccess: (T) -> R,
): R = when (this) {
    is ResultOf.Fail -> onError(cause)
    is ResultOf.Success -> onSuccess(data)
}

inline fun <T, R> ResultOf<T>.mapHandling(
    onError: R,
    onSuccess: (T) -> R,
): R = when (this) {
    is ResultOf.Fail -> onError
    is ResultOf.Success -> onSuccess(data)
}


inline fun <T, R> ResultOf<T>.mapSuccess(
    onSuccess: (T) -> R,
): ResultOf<R> = mapSuccessResult { data -> ResultOf.Success(onSuccess(data)) }


inline fun <T, R> ResultOf<T>.mapSuccessResult(
    onSuccess: (T) -> ResultOf<R>,
): ResultOf<R> = when (this) {
    is ResultOf.Fail -> this
    is ResultOf.Success -> onSuccess(data)
}


inline fun <T> ResultOf<T>.toSuccessOrNull(
    onError: (AppThrowable) -> Unit,
): T? {
    return when (this) {
        is ResultOf.Fail -> {
            onError(cause)
            null
        }
        is ResultOf.Success -> data
    }
}
