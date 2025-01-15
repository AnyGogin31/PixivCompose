plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.moko.permissions)
            implementation(libs.moko.permissions.compose)
        }

        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.coil.compose)
            implementation(libs.kotlin.coroutines.core)
            implementation(libs.android.lifecycle.viewmodel)
            implementation(projects.core.data)
            implementation(projects.core.desingsystem)
            implementation(projects.core.domain)
            implementation(projects.core.navigation)
            implementation(projects.core.state)
            implementation(projects.features.details.detailsNavigation)
            implementation(projects.features.search.searchNavigation)
            implementation(projects.resources)
        }

        iosMain.dependencies {
            implementation(libs.moko.permissions)
            implementation(libs.moko.permissions.compose)
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.details"
}
