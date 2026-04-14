package com.firmfreez.buildlogic.configs

import com.android.build.api.dsl.ApplicationExtension
import com.firmfreez.buildlogic.constants.BuildType
import com.firmfreez.buildlogic.constants.Flavour
import com.firmfreez.buildlogic.constants.Suffixes
import com.firmfreez.buildlogic.extensions.getKeystore
import com.firmfreez.buildlogic.extensions.getKeystoreProps
import com.firmfreez.buildlogic.extensions.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@OptIn(ExperimentalStdlibApi::class)
internal fun Project.applicationConfig(applicationExtension: ApplicationExtension) {
    applicationExtension.apply {

        signingConfigs {
            BuildType.entries.forEach { type ->
                val props = getKeystoreProps(type)
                maybeCreate(type.value).apply {
                    storeFile = getKeystore(type)
                    storePassword = props["storePassword"] as String?
                    keyAlias = props["keyAlias"] as String?
                    keyPassword = props["keyPassword"] as String?
                }
            }
        }

        defaultConfig {
            targetSdk = libs.versions.targetSdk.get().toInt()
            versionCode = libs.versions.versionCode.get().toInt()
            versionName = libs.versions.versionName.get()
        }

        buildTypes {
            getByName(BuildType.DEBUG.value) {
                applicationIdSuffix = Suffixes.DEV_APPLICATION_SUFFIX
                isDebuggable = true
                isMinifyEnabled = false
                isShrinkResources = false
                signingConfig = signingConfigs.getByName(BuildType.DEBUG.value)
            }
            getByName(BuildType.RELEASE.value) {
                isDebuggable = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
                signingConfig = signingConfigs.getByName(BuildType.RELEASE.value)
            }
        }
    }
}

@OptIn(ExperimentalStdlibApi::class)
fun Project.applicationIdConfig(block: (Flavour).() -> String) {
    extensions.configure<ApplicationExtension> {
        productFlavors {
            Flavour.entries.forEach { type ->
                getByName(type.value) {
                    applicationId = block(type)
                    manifestPlaceholders["serviceApplicationId"] = block(type)
                }
            }
        }
    }
}


@OptIn(ExperimentalStdlibApi::class)
fun Project.appNameConfig(block: (Flavour).() -> String) {
    extensions.configure<ApplicationExtension> {
        productFlavors {
            Flavour.entries.forEach { type ->
                getByName(type.value) {
                    resValue("string", "app_name", block(type))
                }
            }
        }
    }
}
