package com.firmfreez.buildlogic.plugins.config

import com.firmfreez.buildlogic.extensions.implementation
import com.firmfreez.buildlogic.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Plugin to configure a Kotlin library.
 *
 * This plugin applies the necessary configurations for a Kotlin library project.
 * It sets up the Kotlin plugin and adds the Kotlin standard library dependency.
 */
internal class KotlinLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("*** KotlinLibraryPlugin invoked ***")

        with(target) {
            with(pluginManager) {
                apply(libs.plugins.kotlin.jvm.get().pluginId)
            }

            dependencies {
                implementation(libs.kotlin.stdlib)
            }
        }
    }
}
