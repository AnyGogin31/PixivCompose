plugins {
    alias(libs.plugins.neilt.application.android)
    alias(libs.plugins.kotlin.serialization)
}

apply(from = "../scripts/git-hooks.gradle.kts")

dependencies {

    implementation(projects.data)
    implementation(projects.domain)

    implementation(projects.features.auth)
    implementation(projects.features.main)

    implementation(projects.shared)

    // AndroidX
    implementation(libs.android.activity)

    // Koin
    implementation(libs.koin.android)

    // Coil
    implementation(libs.coil.compose)
}

android {
    namespace = "neilt.mobile.pixiv"
}
