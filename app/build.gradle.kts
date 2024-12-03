plugins {
    alias(libs.plugins.neilt.mobile.android.application)
    alias(libs.plugins.kotlinSerialization)
}

apply(from = "../scripts/git-hooks.gradle.kts")

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(project(":core:navigation"))

    implementation(project(":desingsystem"))

    // AndroidX
    implementation(libs.android.browser)
    implementation(libs.android.core.splashscreen)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    // Navigation
    implementation(libs.compose.navigation)

    // Kotlin
    implementation(libs.kotlin.serialization.json)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)
}

android {
    namespace = "neilt.mobile.pixiv"
}
