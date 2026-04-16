import com.firmfreez.buildlogic.configs.appNameConfig
import com.firmfreez.buildlogic.configs.applicationIdConfig
import com.firmfreez.buildlogic.constants.Flavour

plugins {
    id(ConfigPlugins.AndroidApplicationCompose)
    id(DependenciesPlugins.AndroidCore)
    id(DependenciesPlugins.ComposeUi)
    id(DependenciesPlugins.KoinApp)
    id(DependenciesPlugins.VoyagerUi)
}

applicationIdConfig {
    when (this) {
        Flavour.DEV -> "com.firmfreez.epubtesttask.android.dev"
        Flavour.PROD -> "com.firmfreez.epubtesttask.android"
    }
}

appNameConfig {
    when (this) {
        Flavour.DEV -> "Epub Reader [DEV]"
        Flavour.PROD -> "Epub Reader"
    }
}


android {
    namespace = "com.firmfreez.epubtesttask.android"
    compileOptions.isCoreLibraryDesugaringEnabled = true
}

androidComponents {
    onVariants { variant ->
        variant.outputs.forEach { output ->
            val name = "epub-test-task-${output.versionName.get()}-${output.versionCode.get()}"
            base.archivesName = name
        }
    }
}


dependencies {
    // Core
    implementation(projects.core.style)
    implementation(projects.core.styleCompose)

    // Common
    implementation(projects.common.domain)
    implementation(projects.common.uiComponents)

    // Dependencies
    implementation(libs.androidx.splashscreen)

    // Desugaring
    coreLibraryDesugaring(libs.android.desugaring)
}

// DI
dependencies {
    implementation(projects.lib.di.domain)

    // Common
    implementation(projects.common.data)
    implementation(projects.common.ui)

    // Libs
    implementation(projects.lib.navigation.impl)
    implementation(projects.lib.bookImporter.impl)

    // Features
    implementation(projects.feature.splash.impl)
    implementation(projects.feature.home.impl)
    implementation(projects.feature.reader.impl)
}
