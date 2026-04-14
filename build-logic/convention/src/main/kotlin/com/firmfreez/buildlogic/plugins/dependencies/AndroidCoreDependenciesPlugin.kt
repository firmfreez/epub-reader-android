package com.firmfreez.buildlogic.plugins.dependencies

import com.firmfreez.buildlogic.extensions.implementation
import com.firmfreez.buildlogic.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * This plugin is responsible for adding core Android dependencies to the Android project.
 * It applies the necessary dependencies for using core Android features in an Android environment.
 */
internal class AndroidCoreDependenciesPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("*** AndroidCoreDependenciesPlugin invoked ***")

        with (target) {
            dependencies {
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.lifecycle.runtime.ktx)
            }
        }
    }
}
