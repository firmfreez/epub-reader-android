plugins {
    id(ConfigPlugins.AndroidLibraryCompose)
    id(DependenciesPlugins.AndroidCore)
    id(DependenciesPlugins.ComposeUi)
    id(DependenciesPlugins.KoinFeature)
    id(DependenciesPlugins.VoyagerUi)
}

android.namespace = "com.firmfreez.app.home.impl"


dependencies {
    // Api
    api(projects.feature.home.api)

    // Libs
    implementation(projects.lib.navigation.api)

    // Core
    implementation(projects.core.styleCompose)
    implementation(projects.core.strings)

    // Common
    implementation(projects.common.domain)
    implementation(projects.common.ui)
    implementation(projects.common.uiComponents)
}
