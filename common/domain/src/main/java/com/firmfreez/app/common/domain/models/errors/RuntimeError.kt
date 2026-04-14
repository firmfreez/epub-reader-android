package com.firmfreez.app.common.domain.models.errors

data class RuntimeError(
    val message: String?,
    val localizedMessage: String?,
    val cause: Throwable?
) : ErrorType {

    override fun toString() = "$message"
}
