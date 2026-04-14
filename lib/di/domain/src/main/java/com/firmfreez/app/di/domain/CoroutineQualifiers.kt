package com.firmfreez.app.di.domain

import org.koin.core.annotation.Named

@Named
annotation class CoroutineQualifiers(val dispatcher: CoroutineDispatchersType)

enum class CoroutineDispatchersType {
    Main,
    Io,
    Default
}
