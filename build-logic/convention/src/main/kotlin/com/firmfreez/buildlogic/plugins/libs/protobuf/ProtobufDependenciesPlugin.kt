package com.firmfreez.buildlogic.plugins.libs.protobuf

import com.firmfreez.buildlogic.extensions.api
import com.firmfreez.buildlogic.extensions.libs
import com.google.protobuf.gradle.ProtobufExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * ProtobufDependenciesPlugin is a Gradle plugin that configures Protobuf dependencies for a project.
 *
 * This plugin applies the Protobuf Gradle plugin and configures the necessary dependencies for Protobuf,
 * including the Protobuf compiler and Datastore Proto.
 */
internal class ProtobufDependenciesPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("*** ProtobufDependenciesPlugin invoked ***")

        with(target) {
            with(pluginManager) {
                apply(libs.plugins.protobuf.get().pluginId)
            }

            dependencies {
                api(libs.protobuf)
                api(libs.datastore.proto)
            }

            extensions.configure<ProtobufExtension> {
                protoc {
                    artifact = libs.protoc.get().toString()
                }

                generateProtoTasks {
                    all().configureEach {
                        builtins {
                            named("java") {
                                option("lite")
                            }
                        }
                    }
                }
            }
        }
    }
}
