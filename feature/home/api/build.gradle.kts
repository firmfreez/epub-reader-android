plugins {
    id(ConfigPlugins.KotlinLibrary)
}

dependencies {
    implementation(projects.lib.navigation.api)
}
