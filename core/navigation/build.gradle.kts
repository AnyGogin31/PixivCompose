plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlin.serialization.json)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            api(libs.compose.navigation)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.coroutines.test)
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.core.navigation"
}
