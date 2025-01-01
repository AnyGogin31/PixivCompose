plugins {
    alias(libs.plugins.neilt.multiplatform)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.android.browser)
        }

        commonMain.dependencies {
            implementation(libs.android.annotation)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.domain"
}
