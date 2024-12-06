plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.neilt.mobile.android.compose)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.data)
                implementation(projects.domain)

                implementation(projects.core.navigation)

                implementation(projects.features.main)

                implementation(libs.android.browser)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.auth"
}
