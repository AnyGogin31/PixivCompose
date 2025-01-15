plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
        }

        commonMain.dependencies {
            implementation(projects.core.data)
            implementation(projects.core.desingsystem)
            implementation(projects.core.domain)
            implementation(projects.core.navigation)
            implementation(projects.core.router)

            implementation(projects.features.auth)
            implementation(projects.features.details.detailsNavigation)
            implementation(projects.features.details.detailsPresentation)
            implementation(projects.features.main)
            implementation(projects.features.search.searchNavigation)
            implementation(projects.features.search.searchPresentation)
            implementation(projects.features.settings)

            implementation(projects.resources)

            implementation(compose.materialIconsExtended)

            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.kotlinx.coroutines.core)
        }

        desktopMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.shared"
}
