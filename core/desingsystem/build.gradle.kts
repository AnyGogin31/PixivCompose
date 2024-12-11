plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.compose)

                implementation(projects.core.navigation)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.desingsystem"
}
