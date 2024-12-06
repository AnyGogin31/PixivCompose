plugins {
    alias(libs.plugins.neilt.mobile.android.library)
}

kotlin {
    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.junit.jupiter)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.domain"
}
