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
                api(compose.components.resources)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.resources"
}

compose.resources {
    publicResClass = true
    packageOfResClass = "neilt.mobile.pixiv.resources"
    generateResClass = always
}
