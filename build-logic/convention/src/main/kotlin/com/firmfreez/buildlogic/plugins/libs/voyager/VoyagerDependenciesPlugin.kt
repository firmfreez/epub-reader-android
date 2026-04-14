package com.firmfreez.buildlogic.plugins.libs.voyager

import org.gradle.api.Plugin
import org.gradle.api.Project


internal class VoyagerDependenciesPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("*** VoyagerDependenciesPlugin invoked ***")

        with(target) {
            configVoyagerCommonDependencies()
        }
    }
}
