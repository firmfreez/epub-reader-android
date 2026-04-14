package com.firmfreez.buildlogic.plugins.libs.voyager

import com.firmfreez.buildlogic.extensions.implementation
import com.firmfreez.buildlogic.extensions.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configVoyagerCommonDependencies() {
    dependencies {
        implementation(libs.voyager.navigator)
        implementation(libs.voyager.transitions)
        implementation(libs.voyager.koin)
    }
}