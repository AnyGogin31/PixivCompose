plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.neilt.mobile.android.compose)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.desingsystem)
                implementation(projects.core.navigation)

                implementation(projects.features.auth)
                implementation(projects.features.illustration)
                implementation(projects.features.main)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.root"
}
