package com.firmfreez.buildlogic.plugins.config

import com.android.build.api.dsl.LibraryExtension
import com.firmfreez.buildlogic.configs.defaultConfig
import com.firmfreez.buildlogic.configs.kotlinJvmTargetConfig
import com.firmfreez.buildlogic.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Plugin to configure Android library with Compose support.
 *
 * This plugin applies the necessary configurations for an Android library project
 * that uses Jetpack Compose. It sets up the Android library plugin, Kotlin plugin,
 * and Compose plugin, along with default configurations and Compose-specific settings.
 */
internal class AndroidLibraryConfigPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("*** AndroidLibraryConfigPlugin invoked ***")

        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.library.get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                defaultConfig(this)
                kotlinJvmTargetConfig()
            }
        }
    }
}
