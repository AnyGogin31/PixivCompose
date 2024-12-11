plugins {
    alias(libs.plugins.neilt.multiplatform)
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()
}

android {
    namespace = "neilt.mobile.pixiv.core.state"
}
