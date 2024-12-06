plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.neilt.mobile.android.compose)
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
    namespace = "neilt.mobile.pixiv.desingsystem"
}
