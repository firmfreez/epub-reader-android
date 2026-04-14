package com.firmfreez.app.common.ui.screen_transitions

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.lifecycle.JavaSerializable
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.transitions.ScreenTransition

@OptIn(ExperimentalVoyagerApi::class)
object FadeTransition : ScreenTransition, JavaSerializable {

    override fun enter(lastEvent: StackEvent): EnterTransition {
        return enterFade(lastEvent = lastEvent)
    }

    override fun exit(lastEvent: StackEvent): ExitTransition {
        return exitFade(lastEvent = lastEvent)
    }
}


fun enterFade(lastEvent: StackEvent): EnterTransition {
    return fadeIn(tween(400, delayMillis = 250))
}

fun exitFade(lastEvent: StackEvent): ExitTransition {
    return fadeOut(tween(400))
}