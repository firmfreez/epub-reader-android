package com.firmfreez.app.common.data.di

import com.firmfreez.app.common.domain.repositories.LoggerRepository
import com.firmfreez.app.di.domain.CoroutineDispatchersType
import com.firmfreez.app.di.domain.CoroutineQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Module
class CoroutinesModule {
    @Single
    fun provideCoroutineExceptionHandler(
        @Provided logger: LoggerRepository
    ): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            logger.d(TAG, "CoroutineExceptionHandler got $exception")
        }

    @Factory
    @CoroutineQualifiers(CoroutineDispatchersType.Io)
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Factory
    @CoroutineQualifiers(CoroutineDispatchersType.Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Factory
    @CoroutineQualifiers(CoroutineDispatchersType.Main)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Single
    fun provideAppScope(@Provided exceptionHandler: CoroutineExceptionHandler): CoroutineScope =
        CoroutineScope(SupervisorJob() + exceptionHandler + CoroutineName(TAG))


    companion object {
        private const val TAG = "App"
    }
}