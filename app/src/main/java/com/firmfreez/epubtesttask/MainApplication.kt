package com.firmfreez.epubtesttask

import android.app.Application
import com.firmfreez.app.common.domain.repositories.LoggerRepository
import com.firmfreez.epubtesttask.android.BuildConfig
import com.firmfreez.epubtesttask.di.AppGraph
import com.firmfreez.epubtesttask.di.mainActivityModule
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.logger.Level
import org.koin.plugin.module.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
class MainApplication : Application(), KoinStartup {

    override fun onKoinStartup() = koinConfiguration<AppGraph> {
        androidLogger(Level.INFO)
        androidContext(this@MainApplication)
        modules(mainActivityModule)
    }

    override fun onCreate() {
        super.onCreate()
        val logger: LoggerRepository = get()

        logger.init(isDebug = BuildConfig.DEBUG)
    }
}
