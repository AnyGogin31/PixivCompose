plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.coil.compose)
            implementation(libs.kotlin.coroutines.core)
            implementation(libs.android.lifecycle.viewmodel)
            implementation(projects.core.desingsystem)
            implementation(projects.core.domain)
            implementation(projects.resources)
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.details"
}
