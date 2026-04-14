package com.firmfreez.buildlogic.configs

import com.firmfreez.buildlogic.extensions.kotlinJvmCompilerOptions
import com.firmfreez.buildlogic.extensions.projectJavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal fun Project.kotlinJvmTargetConfig() {
    kotlinJvmCompilerOptions {
        jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
    }
}