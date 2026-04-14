package com.firmfreez.app.common.domain.models.errors

data class AppThrowable(
    val data: ErrorType,
    val parentThrowable: Throwable? = IllegalStateException(data.toString()),
) : Throwable(parentThrowable) {


    companion object {
        fun custom(message: String) = AppThrowable(
            data = CustomError(message = message)
        )

        fun unknown() = AppThrowable(
            data = CustomError(message = UNKNOWN_ERROR)
        )

        const val UNKNOWN_ERROR = "Unknown error"
    }
}
