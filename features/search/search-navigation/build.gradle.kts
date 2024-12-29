plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.navigation.navigationApi)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.search.navigation"
}
