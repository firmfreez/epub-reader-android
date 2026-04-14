plugins {
    id(ConfigPlugins.AndroidLibrary)
}

android.namespace = "com.firmfreez.app.core.utils"

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
