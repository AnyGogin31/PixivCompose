plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.neilt.mobile.android.compose)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.data)
                implementation(projects.domain)

                implementation(projects.core.navigation)
                implementation(projects.core.desingsystem)
                implementation(projects.core.state)

                implementation(libs.coil.compose)
                implementation(libs.coil.network)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.illustration"
}
