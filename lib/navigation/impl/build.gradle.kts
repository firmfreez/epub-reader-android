plugins {
    id(ConfigPlugins.AndroidLibraryCompose)
    id(DependenciesPlugins.ComposeUi)
    id(DependenciesPlugins.KoinData)
}

android.namespace = "com.firmfreez.app.navigation"

dependencies {
    api(projects.lib.navigation.api)

    implementation(projects.feature.splash.api)
    implementation(projects.feature.home.api)
    implementation(projects.feature.reader.api)
}
