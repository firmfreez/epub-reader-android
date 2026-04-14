package com.firmfreez.app.common.domain.models.api_wrapper

import com.firmfreez.app.common.domain.models.errors.AppThrowable

interface ErrorHandler {
    fun handleError(throwable: Throwable): AppThrowable
}