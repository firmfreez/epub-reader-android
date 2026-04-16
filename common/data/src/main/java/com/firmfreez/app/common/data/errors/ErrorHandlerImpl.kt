package com.firmfreez.app.common.data.errors

import com.firmfreez.app.common.domain.models.errors.AppThrowable
import com.firmfreez.app.common.domain.models.result_wrapper.ErrorHandler
import org.koin.core.annotation.Single

@Single(binds = [ErrorHandler::class])
class ErrorHandlerImpl() : ErrorHandler {

    override fun handleError(throwable: Throwable): AppThrowable {
        return AppThrowable.custom(throwable)
    }
}
