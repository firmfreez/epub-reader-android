package com.firmfreez.buildlogic.plugins.libs.koin

import com.firmfreez.buildlogic.extensions.implementation
import com.firmfreez.buildlogic.extensions.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


internal fun Project.configKoinAppDependencies() {
    dependencies {
        implementation(platform(libs.koin.bom))
        implementation(libs.koin.core)
        implementation(libs.koin.annotations)
        implementation(libs.koin.android)
        implementation(libs.koin.androidx.startup)
        implementation(libs.koin.compose)
    }
}


internal fun Project.configKoinFeatureDependencies() {
    dependencies {
        implementation(platform(libs.koin.bom))
        implementation(libs.koin.core)
        implementation(libs.koin.annotations)
        implementation(libs.koin.android)
        implementation(libs.koin.compose)
    }
}

internal fun Project.configKoinDataDependencies() {
    dependencies {
        implementation(platform(libs.koin.bom))
        implementation(libs.koin.core)
        implementation(libs.koin.annotations)
    }
}
