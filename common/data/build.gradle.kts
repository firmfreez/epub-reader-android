plugins {
    id(ConfigPlugins.AndroidLibrary)
    id(DependenciesPlugins.KoinData)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android.namespace = "com.firmfreez.app.common.data"

dependencies {
    // Core
    implementation(projects.core.strings)

    // Common
    implementation(projects.common.domain)
    implementation(projects.common.usecases)
    implementation(projects.common.ui)
    implementation(projects.common.data.datastore)

    // Libs
    implementation(projects.lib.di.domain)

    // Logs
    implementation(libs.timber)

    // DB
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}