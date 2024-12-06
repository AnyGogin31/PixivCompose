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

    implementation(projects.features.auth)
    implementation(projects.features.main)
    implementation(projects.features.root)

    implementation(projects.shared)

    // AndroidX
    implementation(libs.android.core.splashscreen)

    // Navigation
    implementation(libs.compose.navigation)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)
}

android {
    namespace = "neilt.mobile.pixiv"
}
