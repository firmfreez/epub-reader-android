package com.firmfreez.app.common.usecases

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface TimerUseCase {

    operator fun invoke(duration: Duration, interval: Duration): Flow<Duration>

    fun infinity(interval: Duration): Flow<Duration>
}