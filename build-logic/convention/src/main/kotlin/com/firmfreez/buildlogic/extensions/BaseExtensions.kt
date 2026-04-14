package com.firmfreez.buildlogic.extensions

import com.firmfreez.buildlogic.constants.BuildType
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.io.File
import java.util.Properties


internal val Project.libs: LibrariesForLibs
    get() = the<LibrariesForLibs>()


internal val Project.projectJavaVersion: JavaVersion
    get() = JavaVersion.toVersion(libs.versions.java.get().toInt())


internal fun Project.kotlinJvmCompilerOptions(block: KotlinJvmCompilerOptions.() -> Unit) {
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions(block)
    }
}


private fun Project.keyStoreDir(): File = rootProject.file("build-logic/convention")

fun Project.getKeystoreProps(type: BuildType): Properties {
    val propertiesName = "${type.value}.properties"
    val keyStorePropsFile = keyStoreDir().resolve(propertiesName)

    return Properties().apply {
        if (!keyStorePropsFile.exists()) {
            logger.warn("⚠️ Keystore properties file not found: $keyStorePropsFile")
        } else {
            load(keyStorePropsFile.inputStream())
        }
    }
}

fun Project.getKeystore(type: BuildType): File {
    val keystoreName = "${type.value}.keystore"
    val keystoreFile = keyStoreDir().resolve(keystoreName)

    if (!keystoreFile.exists()) {
        logger.warn("⚠️ Keystore file not found: $keystoreFile")
    }

    return keystoreFile
}