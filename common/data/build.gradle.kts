plugins {
    id(ConfigPlugins.AndroidLibrary)
    id(DependenciesPlugins.KoinData)
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

    // Dependencies
    implementation(libs.timber)
}
