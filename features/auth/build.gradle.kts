plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        val desktopMain by getting

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
            implementation(projects.features.main)
            implementation(projects.resources)
        }

        desktopMain.dependencies {
            implementation(libs.compose.desktop.browser.wtf)
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.auth"
}
