plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.kotlinSerialization)
}

dependencies {

    // Navigation
    implementation(libs.compose.navigation)

    // Kotlin
    implementation(libs.kotlin.serialization.json)
}

android {
    namespace = "neilt.mobile.core.navigation"
}
