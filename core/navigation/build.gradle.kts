plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.compose.navigation)
                api(libs.kotlin.serialization.json)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.coroutines.test)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.core.navigation"
}
