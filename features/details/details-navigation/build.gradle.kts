plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.navigation)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.details.navigation"
}
