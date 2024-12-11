plugins {
    alias(libs.plugins.neilt.application.android)
    alias(libs.plugins.kotlin.serialization)
}

apply(from = "../scripts/git-hooks.gradle.kts")

dependencies {

    implementation(projects.data)
    implementation(projects.domain)

    implementation(projects.core.desingsystem)
    implementation(projects.core.navigation.navigationApi)

    implementation(projects.features.auth)
    implementation(projects.features.main)
    implementation(projects.features.root)

    implementation(projects.shared)

    // Koin
    implementation(libs.koin.android)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network)

    // AndroidX
    implementation(libs.android.core.splashscreen)
}

android {
    namespace = "neilt.mobile.pixiv"
}
