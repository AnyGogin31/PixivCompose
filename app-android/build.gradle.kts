plugins {
    alias(libs.plugins.neilt.mobile.android.application)
    alias(libs.plugins.kotlinSerialization)
}

apply(from = "../scripts/git-hooks.gradle.kts")

dependencies {

    implementation(projects.data)
    implementation(projects.domain)

    implementation(projects.core.desingsystem)
    implementation(projects.core.navigation)

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
