plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
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
