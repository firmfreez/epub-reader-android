plugins {
    id(ConfigPlugins.AndroidLibrary)
    id(DependenciesPlugins.AndroidCore)
    id(DependenciesPlugins.KoinData)
}

android.namespace = "com.firmfreez.app.book.importer.impl"

dependencies {
    // Api
    api(projects.lib.bookImporter.api)

    // Qualifiers
    implementation(projects.lib.di.domain)
}
