plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.compose)

                implementation(projects.domain)

                implementation(projects.core.navigation)

                implementation(libs.coil.compose)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.coil.network)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.desingsystem"
}
