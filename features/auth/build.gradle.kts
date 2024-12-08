plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.neilt.mobile.android.compose)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.compose)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.data)
                implementation(projects.domain)

                implementation(projects.core.navigation)
                implementation(projects.core.desingsystem)
                implementation(projects.core.state)

                implementation(projects.features.main)

                implementation(compose.material3)

                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.coil.compose)

                implementation(libs.kotlin.coroutines.core)
                implementation(libs.android.lifecycle.viewmodel)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.android.browser)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.auth"
}
