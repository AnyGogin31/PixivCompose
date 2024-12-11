plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.data)

                implementation(projects.core.desingsystem)
                implementation(projects.core.navigation)

                implementation(projects.features.auth)
                implementation(projects.features.illustration)
                implementation(projects.features.main)
                implementation(projects.features.settings)

                implementation(projects.resources)

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
    namespace = "neilt.mobile.pixiv.features.root"
}
