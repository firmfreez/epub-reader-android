package com.firmfreez.buildlogic.plugins.config

import com.android.build.api.dsl.ApplicationExtension
import com.firmfreez.buildlogic.configs.applicationConfig
import com.firmfreez.buildlogic.configs.composeConfig
import com.firmfreez.buildlogic.configs.defaultConfig
import com.firmfreez.buildlogic.configs.kotlinJvmTargetConfig
import com.firmfreez.buildlogic.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Plugin to configure Android application with Compose support.
 *
 * This plugin applies the necessary configurations for an Android application project
 * that uses Jetpack Compose. It sets up the Android application plugin, Kotlin plugin,
 * and Compose plugin, along with default configurations and Compose-specific settings.
 */
internal class AndroidApplicationComposeConfigPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("*** AndroidApplicationComposeConfigPlugin invoked ***")

        with (target) {
            with(pluginManager) {
                apply(libs.plugins.android.application.get().pluginId)
                apply(libs.plugins.kotlin.compose.get().pluginId)
            }

            extensions.configure<ApplicationExtension> {
                buildFeatures.buildConfig = true
                defaultConfig(this)
                applicationConfig(this)
                composeConfig(this)
                kotlinJvmTargetConfig()
            }
        }
    }
}
