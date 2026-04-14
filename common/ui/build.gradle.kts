plugins {
    id(ConfigPlugins.AndroidLibraryCompose)
    id(DependenciesPlugins.AndroidCore)
    id(DependenciesPlugins.ComposeUi)
    id(DependenciesPlugins.KoinData)
}

android.namespace = "com.firmfreez.app.common.ui"

dependencies {
    // Core
    implementation(projects.core.utils)
    implementation(projects.core.strings)

    // Common
    implementation(projects.common.domain)

    // Voyager
    implementation(libs.voyager.transitions)
}
