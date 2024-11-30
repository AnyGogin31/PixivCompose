plugins {
    alias(libs.plugins.neilt.mobile.android.library)
}

dependencies {

    // Navigation
    implementation(libs.compose.navigation)
}

android {
    namespace = "neilt.mobile.core.navigation"
}
