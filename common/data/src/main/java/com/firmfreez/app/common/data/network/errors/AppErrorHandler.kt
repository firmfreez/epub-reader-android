package com.firmfreez.app.common.data.network.errors

import com.firmfreez.app.common.domain.models.api_wrapper.ErrorHandler
import com.firmfreez.app.common.domain.models.errors.AppThrowable
import org.koin.core.annotation.Single

@Single(binds = [ErrorHandler::class])
class AppErrorHandler() : ErrorHandler {

    override fun handleError(throwable: Throwable): AppThrowable {
        TODO("Not yet implemented")
    }
}