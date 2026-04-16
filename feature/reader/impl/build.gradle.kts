plugins {
    id(ConfigPlugins.AndroidLibraryCompose)
    id(DependenciesPlugins.AndroidCore)
    id(DependenciesPlugins.ComposeUi)
    id(DependenciesPlugins.KoinFeature)
    id(DependenciesPlugins.VoyagerUi)
}

android.namespace = "com.firmfreez.app.reader.impl"


dependencies {
    // Api
    api(projects.feature.reader.api)

    // Libs
    implementation(projects.lib.di.domain)
    implementation(projects.lib.navigation.api)

    // Core
    implementation(projects.core.styleCompose)
    implementation(projects.core.strings)

    // Common
    implementation(projects.common.domain)
    implementation(projects.common.usecases)
    implementation(projects.common.ui)
    implementation(projects.common.uiComponents)

    // Readium
    implementation(libs.readium.shared)
    implementation(libs.readium.streamer)
    implementation(libs.readium.navigator)

    // FragmentCompose
    implementation(libs.androidx.fragment.compose)
}
