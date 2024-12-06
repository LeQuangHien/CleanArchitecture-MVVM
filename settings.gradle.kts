pluginManagement {
    includeBuild("build-logic") // include build-logic module
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

rootProject.name = "CleanArchitecture"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":sync")
include(":feature:news")
include(":core:data")
include(":core:model")
include(":core:network")
include(":core:database")
include(":core:common")
