plugins {
    alias(libs.plugins.neilt.multiplatform)
}

kotlin {
    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.domain"
}
