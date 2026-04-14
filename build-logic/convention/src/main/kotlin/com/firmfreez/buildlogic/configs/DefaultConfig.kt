package com.firmfreez.buildlogic.configs

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.firmfreez.buildlogic.constants.BuildType
import com.firmfreez.buildlogic.constants.FLAVOUR_DIMENSION_NAME
import com.firmfreez.buildlogic.constants.Flavour
import com.firmfreez.buildlogic.extensions.libs
import com.firmfreez.buildlogic.extensions.projectJavaVersion
import org.gradle.api.Project

@OptIn(ExperimentalStdlibApi::class)
internal fun Project.defaultConfig(commonExtension: ApplicationExtension, useFlavours: Boolean = true) {

    commonExtension.apply {
        compileSdk = libs.versions.compileSdk.get().toInt()

        defaultConfig {
            minSdk = libs.versions.minSdk.get().toInt()
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        compileOptions {
            sourceCompatibility = projectJavaVersion
            targetCompatibility = projectJavaVersion
        }

        if (useFlavours) {
            flavorDimensions += FLAVOUR_DIMENSION_NAME
            productFlavors {
                Flavour.entries.forEach {
                    maybeCreate(it.value).dimension = FLAVOUR_DIMENSION_NAME
                }
            }
        }

        buildTypes {
            getByName(BuildType.DEBUG.value) {
                isMinifyEnabled = false
                isShrinkResources = false
            }
            getByName(BuildType.RELEASE.value) {
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }

        buildFeatures.resValues = true
    }
}


@OptIn(ExperimentalStdlibApi::class)
internal fun Project.defaultConfig(commonExtension: LibraryExtension, useFlavours: Boolean = true) {

    commonExtension.apply {
        compileSdk = libs.versions.compileSdk.get().toInt()

        defaultConfig {
            minSdk = libs.versions.minSdk.get().toInt()
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }

        compileOptions {
            sourceCompatibility = projectJavaVersion
            targetCompatibility = projectJavaVersion
        }

        if (useFlavours) {
            flavorDimensions += FLAVOUR_DIMENSION_NAME
            productFlavors {
                Flavour.entries.forEach {
                    maybeCreate(it.value).dimension = FLAVOUR_DIMENSION_NAME
                }
            }
        }

        buildTypes {
            getByName(BuildType.DEBUG.value) {
                isMinifyEnabled = false
                isShrinkResources = false
            }
            getByName(BuildType.RELEASE.value) {
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }

        buildFeatures.resValues = true
    }
}
