plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.neilt.mobile.android.compose)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.desingsystem)
                implementation(projects.core.navigation)

                implementation(projects.features.root)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.shared"
}
