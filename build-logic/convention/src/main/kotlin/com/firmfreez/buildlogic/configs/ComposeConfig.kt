package com.firmfreez.buildlogic.configs

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project

internal fun Project.composeConfig(commonExtension: ApplicationExtension) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }
    }
}

internal fun Project.composeConfig(commonExtension: LibraryExtension) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }
    }
}
