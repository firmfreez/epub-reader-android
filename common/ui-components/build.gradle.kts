plugins {
    id(ConfigPlugins.AndroidLibraryCompose)
    id(DependenciesPlugins.AndroidCore)
    id(DependenciesPlugins.ComposeUi)
}

android.namespace = "com.firmfreez.app.common.ui_components"

dependencies {
    // Core
    implementation(projects.core.styleCompose)
    implementation(projects.core.strings)

    // Common
    implementation(projects.common.domain)
    implementation(projects.common.ui)
}
