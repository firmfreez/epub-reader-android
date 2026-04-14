package com.firmfreez.app.common.data.di

import com.firmfreez.app.common.data.data_store.di.ProtoMappersModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module

@Module(
    includes = [
        UseCasesModule::class,
        RepositoriesModule::class,
        ProtoMappersModule::class,
        CoroutinesModule::class,
        DatabaseModule::class,
        SourcesModule::class,
        ErrorsModule::class,
        MappersModule::class
    ]
)
@ComponentScan("com.firmfreez")
@Configuration
class CommonDataModule
