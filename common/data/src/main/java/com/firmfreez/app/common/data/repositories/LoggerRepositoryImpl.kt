package com.firmfreez.app.common.data.repositories

import com.firmfreez.app.common.domain.repositories.LoggerRepository
import org.koin.core.annotation.Single
import timber.log.Timber

@Single(binds = [LoggerRepository::class])
class LoggerRepositoryImpl : LoggerRepository {

    private var isInitialized: Boolean = false

    override fun init(isDebug: Boolean) {
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
            isInitialized = true
        }
    }

    override fun i(message: String) = runInitialized {
        Timber.i(message = message)
    }

    override fun i(tag: String, message: String) = runInitialized {
        Timber.tag(tag = tag).i(message = message)
    }

    override fun d(message: String) = runInitialized {
        Timber.d(message = message)
    }

    override fun d(tag: String, message: String) = runInitialized {
        Timber.tag(tag = tag).d(message = message)
    }

    override fun w(message: String) = runInitialized {
        Timber.w(message = message)
    }

    override fun w(tag: String, message: String) = runInitialized {
        Timber.tag(tag = tag).w(message = message)
    }

    override fun e(message: String) = runInitialized {
        Timber.e(message = message)
    }

    override fun e(error: Throwable) = runInitialized {
        Timber.e(t = error)
    }

    override fun e(tag: String, message: String) = runInitialized {
        Timber.tag(tag = tag).e(message = message)
    }

    override fun e(tag: String, error: Throwable) = runInitialized {
        Timber.tag(tag = tag).e(t = error)
    }

    override fun e(tag: String, message: String, error: Throwable) = runInitialized {
        Timber.tag(tag = tag).e(t = error, message = message)
    }


    private fun runInitialized(block: () -> Unit) {
        if (isInitialized) {
            block()
        }
    }
}