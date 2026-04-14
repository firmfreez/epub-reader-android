package com.firmfreez.buildlogic.plugins.dependencies

import com.firmfreez.buildlogic.extensions.debugImplementation
import com.firmfreez.buildlogic.extensions.implementation
import com.firmfreez.buildlogic.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * This plugin is responsible for adding Compose UI dependencies to the Android project.
 * It applies the necessary dependencies for using Jetpack Compose UI in an Android environment.
 */
internal class AndroidComposeUiDependenciesPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("*** AndroidComposeUiDependenciesPlugin invoked ***")

        with (target) {
            dependencies {
                implementation(libs.androidx.activity.compose)

                implementation(platform(libs.androidx.compose.bom))
                implementation(libs.androidx.ui)
                implementation(libs.androidx.ui.graphics)
                debugImplementation(libs.androidx.ui.tooling)
                implementation(libs.androidx.ui.tooling.preview)
                implementation(libs.androidx.material3)
                implementation(libs.androidx.material.icons)
            }
        }
    }
}
