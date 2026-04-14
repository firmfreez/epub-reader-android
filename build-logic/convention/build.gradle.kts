import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.firmfreez.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.protobuf.gradlePlugin)

    // Workaround for version catalog working inside precompiled scripts
    // Issue - https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

private val projectJavaVersion: JavaVersion = JavaVersion.toVersion(libs.versions.java.get())

java {
    sourceCompatibility = projectJavaVersion
    targetCompatibility = projectJavaVersion
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
}

gradlePlugin {
    plugins {
        // Plugins for configuration
        create("androidApplicationCompose") {
            id = "android.app.compose"
            implementationClass = "com.firmfreez.buildlogic.plugins.config.AndroidApplicationComposeConfigPlugin"
        }
        create("androidApplication") {
            id = "android.app"
            implementationClass = "com.firmfreez.buildlogic.plugins.config.AndroidApplicationConfigPlugin"
        }
        create("androidLibraryCompose") {
            id = "android.library.compose"
            implementationClass = "com.firmfreez.buildlogic.plugins.config.AndroidLibraryComposeConfigPlugin"
        }
        create("androidLibrary") {
            id = "android.library"
            implementationClass = "com.firmfreez.buildlogic.plugins.config.AndroidLibraryConfigPlugin"
        }
        create("kotlinLibrary") {
            id = "kotlin.library"
            implementationClass = "com.firmfreez.buildlogic.plugins.config.KotlinLibraryPlugin"
        }

        // Dependency plugins
        create("androidComposeUiDependencies") {
            id = "android.compose.ui.dependencies"
            implementationClass = "com.firmfreez.buildlogic.plugins.dependencies.AndroidComposeUiDependenciesPlugin"
        }
        create("androidCoreDependencies") {
            id = "android.core.dependencies"
            implementationClass = "com.firmfreez.buildlogic.plugins.dependencies.AndroidCoreDependenciesPlugin"
        }
        create("koinFeatureDependencies") {
            id = "koin.feature.dependencies"
            implementationClass = "com.firmfreez.buildlogic.plugins.libs.koin.KoinFeatureDependenciesPlugin"
        }
        create("koinDataDependencies") {
            id = "koin.data.dependencies"
            implementationClass = "com.firmfreez.buildlogic.plugins.libs.koin.KoinDataDependenciesPlugin"
        }
        create("koinAppDependencies") {
            id = "koin.app.dependencies"
            implementationClass = "com.firmfreez.buildlogic.plugins.libs.koin.KoinAppDependenciesPlugin"
        }
        create("protobufDependencies") {
            id = "protobuf.dependencies"
            implementationClass = "com.firmfreez.buildlogic.plugins.libs.protobuf.ProtobufDependenciesPlugin"
        }
        create("voyagerDependencies") {
            id = "voyager.dependencies"
            implementationClass = "com.firmfreez.buildlogic.plugins.libs.voyager.VoyagerDependenciesPlugin"
        }
    }
}