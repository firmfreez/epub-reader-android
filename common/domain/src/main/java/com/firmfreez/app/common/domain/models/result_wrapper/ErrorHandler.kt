package com.firmfreez.app.common.domain.models.result_wrapper

import com.firmfreez.app.common.domain.models.errors.AppThrowable

interface ErrorHandler {
    fun handleError(throwable: Throwable): AppThrowable
}