package com.firmfreez.app.common.ui.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.firmfreez.app.common.ui.mappers")
@Configuration
class MappersModule
