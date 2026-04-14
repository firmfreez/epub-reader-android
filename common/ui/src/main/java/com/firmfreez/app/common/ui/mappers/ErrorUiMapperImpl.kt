package com.firmfreez.app.common.ui.mappers

import android.content.Context
import com.firmfreez.app.common.domain.models.errors.AppThrowable
import com.firmfreez.app.common.domain.models.errors.AppThrowable.Companion.UNKNOWN_ERROR
import com.firmfreez.app.common.domain.models.errors.CustomError
import com.firmfreez.app.common.domain.models.errors.RuntimeError
import com.firmfreez.app.core.strings.R
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [ErrorUiMapper::class])
class ErrorUiMapperImpl(
    @Provided private val context: Context
) : ErrorUiMapper {

    override fun map(throwable: Throwable): String {
        val appThrowable = throwable as? AppThrowable
        val runtimeError = (appThrowable?.data as? RuntimeError)
        val customError = (appThrowable?.data as? CustomError)

        if (customError != null) {
            return customError.message.takeIf { it.isNotBlank() } ?: context.getString(R.string.unknown_network_error)
        }

        val message = (runtimeError?.message ?: appThrowable?.message ?: throwable.message)?.takeIf { it.isNotBlank() } ?: UNKNOWN_ERROR

        val localizedMessage = when (message) {
            UNKNOWN_ERROR -> context.getString(R.string.unknown_network_error)
            else -> message
        }

        return localizedMessage
    }
}
