rootProject.name = "PixivCompose"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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

include(":app-android")

include(":core:desingsystem")
include(":core:navigation")
include(":core:state")

include(":data")
include(":domain")

include(":features:auth")
include(":features:illustration")
include(":features:main")
include(":features:root")

include(":resources")
include(":shared")
