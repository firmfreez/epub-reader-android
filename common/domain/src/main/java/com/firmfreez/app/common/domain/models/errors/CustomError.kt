package com.firmfreez.app.common.domain.models.errors

data class CustomError(
    val message: String
) : ErrorType {

    override fun toString(): String = message
}