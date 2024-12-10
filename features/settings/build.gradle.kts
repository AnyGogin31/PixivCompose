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

                implementation(projects.core.desingsystem)
                implementation(projects.core.navigation)
                implementation(projects.core.state)

                implementation(compose.material3)

                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)

                implementation(libs.kotlin.coroutines.core)
                implementation(libs.android.lifecycle.viewmodel)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.settings"
}
