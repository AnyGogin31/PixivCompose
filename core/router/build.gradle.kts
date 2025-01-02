plugins {
    alias(libs.plugins.neilt.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(projects.core.domain)
            implementation(projects.core.navigation)
            implementation(projects.features.auth)
            implementation(projects.features.main)
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.core.router"
}
