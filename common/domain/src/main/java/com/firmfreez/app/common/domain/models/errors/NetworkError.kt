package com.firmfreez.app.common.domain.models.errors

data class NetworkError(
    val code: Int,
    val message: String
) : ErrorType {
    override fun toString() = "Code: $code. Message: $message"

    companion object {
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val NOT_FOUND = 404
        const val NOT_ALLOWED = 405
    }
}
