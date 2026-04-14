plugins {
    id(ConfigPlugins.AndroidLibraryCompose)
}

android.namespace = "com.firmfreez.app.core.style.compose"

dependencies {
    implementation(projects.core.style)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
}
