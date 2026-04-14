plugins {
    id(ConfigPlugins.KotlinLibrary)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.annotations)
}

