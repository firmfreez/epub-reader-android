package com.firmfreez.app.common.ui.mappers

interface ErrorUiMapper {

    fun map(throwable: Throwable): String
}