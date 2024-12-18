plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.compose)

                implementation(projects.domain)

                implementation(projects.core.navigation.navigationApi)

                implementation(projects.resources)

                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.desingsystem"
}
