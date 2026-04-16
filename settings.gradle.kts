pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "EpubTestTask"
include(
    ":app",
    ":core:style",
    ":core:style-compose",
    ":core:strings",
    ":core:utils",
    ":common:domain",
    ":common:data",
    ":common:data:datastore",
    ":common:usecases",
    ":common:ui",
    ":common:ui-components",
    ":lib:navigation:api",
    ":lib:navigation:impl",
    ":lib:di:domain",
    ":lib:book-importer:api",
    ":lib:book-importer:impl",
    ":feature:splash:api",
    ":feature:splash:impl",
    ":feature:home:api",
    ":feature:home:impl",
    ":feature:reader:api",
    ":feature:reader:impl",
)
