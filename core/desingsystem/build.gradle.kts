plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.neilt.mobile.android.compose)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.domain)

                implementation(projects.core.navigation)

                implementation(libs.coil.compose)
                implementation(libs.coil.network)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.desingsystem"
}
