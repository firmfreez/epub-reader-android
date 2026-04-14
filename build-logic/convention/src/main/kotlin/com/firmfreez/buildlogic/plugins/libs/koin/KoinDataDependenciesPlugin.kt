package com.firmfreez.buildlogic.plugins.libs.koin

import com.firmfreez.buildlogic.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * KoinDependenciesPlugin is a Gradle plugin that configures Koin dependencies for a project.
 *
 * This plugin applies the Koin KSP plugin and configures the necessary dependencies for Koin.
 * It is intended to be used in Android projects that utilize Koin for dependency injection.
 */
internal class KoinDataDependenciesPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("*** KoinDataDependenciesPlugin invoked ***")

        with(target) {
            pluginManager.apply(libs.plugins.koin.compiler.get().pluginId)

            configKoinDataDependencies()
        }
    }
}
