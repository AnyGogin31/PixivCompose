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
include(":core:navigation:navigation-api")
include(":core:navigation:navigation-impl")
include(":core:router")
include(":core:state")

include(":data")
include(":domain")

include(":features:auth")
include(":features:details:details-navigation")
include(":features:details:details-presentation")
include(":features:main")
include(":features:search:search-navigation")
include(":features:search:search-presentation")
include(":features:settings")

include(":resources")
include(":shared")
