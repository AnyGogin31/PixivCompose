plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.data)
                implementation(projects.domain)

                implementation(projects.core.navigation.navigationApi)
                implementation(projects.core.desingsystem)
                implementation(projects.core.state)

                implementation(projects.features.details.detailsNavigation)
                implementation(projects.features.search.searchNavigation)

                implementation(projects.resources)

                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.coil.compose)

                implementation(libs.kotlin.coroutines.core)
                implementation(libs.android.lifecycle.viewmodel)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.details"
}
