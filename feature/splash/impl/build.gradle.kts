plugins {
    id(ConfigPlugins.AndroidLibraryCompose)
    id(DependenciesPlugins.AndroidCore)
    id(DependenciesPlugins.ComposeUi)
    id(DependenciesPlugins.KoinFeature)
}

android.namespace = "com.firmfreez.app.splash.impl"

dependencies {
    // Api
    api(projects.feature.splash.api)

    // Libs
    implementation(projects.lib.navigation.api)
    implementation(projects.lib.di.domain)

    // Core
    implementation(projects.core.style)
    implementation(projects.core.styleCompose)
    implementation(projects.core.strings)

    // Common
    implementation(projects.common.domain)
    implementation(projects.common.usecases)
    implementation(projects.common.ui)
    implementation(projects.common.uiComponents)

    // Dependencies
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transitions)
}
