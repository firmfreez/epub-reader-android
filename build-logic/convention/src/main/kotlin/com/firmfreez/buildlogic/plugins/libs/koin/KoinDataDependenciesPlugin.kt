package com.firmfreez.buildlogic.plugins.libs.koin

import com.firmfreez.buildlogic.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.koin.compiler.plugin.KoinGradleExtension

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

            extensions.configure<KoinGradleExtension> {
                compileSafety.set(false)
                skipDefaultValues.set(true)
                unsafeDslChecks.set(true)
                debugLogs.set(true)
                userLogs.set(true)
            }

            configKoinDataDependencies()
        }
    }
}
