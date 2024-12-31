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
                implementation(projects.core.router)

                implementation(projects.features.auth)
                implementation(projects.features.details.detailsNavigation)
                implementation(projects.features.details.detailsPresentation)
                implementation(projects.features.main)
                implementation(projects.features.search.searchNavigation)
                implementation(projects.features.search.searchPresentation)
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
