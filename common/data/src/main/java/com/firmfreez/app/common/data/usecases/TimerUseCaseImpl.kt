package com.firmfreez.app.common.data.usecases

import com.firmfreez.app.common.usecases.TimerUseCase
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import org.koin.core.annotation.Single
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Single(binds = [TimerUseCase::class])
class TimerUseCaseImpl : TimerUseCase {

    override fun invoke(duration: Duration, interval: Duration) = flow {
        var timeLeft = duration
        while (timeLeft >= 0.seconds) {
            emit(timeLeft)
            delay(interval)
            timeLeft -= interval
        }
    }

    override fun infinity(interval: Duration) = flow {
        var elapsed = Duration.ZERO
        val ctx = currentCoroutineContext()
        while (ctx.isActive) {
            emit(elapsed)
            delay(interval)
            elapsed += interval
        }
    }
}
