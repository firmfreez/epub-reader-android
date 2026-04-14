package com.firmfreez.app.common.domain.models.errors

sealed class AppNetworkError(
    open val code: Int,
    open val message: String
) : ErrorType {

    data class UnknownError(
        override val code: Int,
        override val message: String
    ) : AppNetworkError(code = code, message = message)

    // Write more busyness network errors

    override fun toString() = message
}