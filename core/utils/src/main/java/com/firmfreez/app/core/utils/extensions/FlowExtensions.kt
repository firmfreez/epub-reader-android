package com.firmfreez.app.core.utils.extensions

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

fun <T> Flow<T>.debounceFlow(waitMillis: Long) = flow {
    coroutineScope {
        val context = coroutineContext
        var delayPost: Deferred<Unit>? = null
        collect {
            delayPost?.cancel()
            delayPost = async(Dispatchers.Default) {
                delay(waitMillis)
                withContext(context) {
                    emit(it)
                }
            }
        }
    }
}

fun <T> Flow<T>.debounceFlowReversed(waitMillis: Long) = flow {
    coroutineScope {
        val context = coroutineContext
        var delayPost: Deferred<Unit>? = null
        collect {
            if (delayPost == null || delayPost?.isCompleted == true || delayPost?.isCancelled == true) {
                delayPost = async(Dispatchers.Default) {
                    withContext(context) {
                        emit(it)
                    }
                    delay(waitMillis)
                }
            }
        }
    }
}
