plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.neilt.mobile.android.compose)
    alias(libs.plugins.compose)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(libs.koin.compose)

                implementation(projects.core.navigation)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.desingsystem"
}
