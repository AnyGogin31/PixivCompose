plugins {
    alias(libs.plugins.neilt.mobile.android.library)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()

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
