plugins {
    alias(libs.plugins.neilt.mobile.android.library)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()
}

android {
    namespace = "neilt.mobile.pixiv.core.state"
}
