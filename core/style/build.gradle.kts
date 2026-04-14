plugins {
    id(ConfigPlugins.AndroidLibrary)
}

android.namespace = "com.firmfreez.app.core.style"

dependencies {
    implementation(libs.material)
    implementation(libs.androidx.splashscreen)
}