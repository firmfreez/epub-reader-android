plugins {
    id(ConfigPlugins.AndroidLibrary)
    id(DependenciesPlugins.KoinData)
}

android.namespace = "com.firmfreez.app.crash_reporter.impl"

dependencies {
    // Api
    api(projects.lib.crashReporter.api)

    // Firebase
    implementation(project.dependencies.platform(libs.android.firebase.bom))
    implementation(libs.android.firebase.crashlytics)
}
