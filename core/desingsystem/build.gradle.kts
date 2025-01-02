plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.compose)
                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor)
                implementation(projects.core.domain)
                implementation(projects.core.navigation)
                implementation(projects.resources)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.desingsystem"
}
