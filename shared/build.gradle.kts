plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.data)
                implementation(projects.domain)

                implementation(projects.core.desingsystem)
                implementation(projects.core.navigation.navigationApi)
                implementation(projects.core.navigation.navigationImpl)

                implementation(projects.features.auth)
                implementation(projects.features.details)
                implementation(projects.features.main)
                implementation(projects.features.search)
                implementation(projects.features.settings)

                implementation(projects.resources)

                implementation(libs.koin.core)
                implementation(libs.koin.compose.viewmodel)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.shared"
}
