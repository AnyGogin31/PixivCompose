plugins {
    alias(libs.plugins.neilt.application.android)
    alias(libs.plugins.kotlin.serialization)
}

apply(from = "../scripts/git-hooks.gradle.kts")

dependencies {
    implementation(libs.android.activity)
    implementation(libs.koin.android)
    implementation(libs.coil.compose)
    implementation(projects.shared)
}

android {
    namespace = "neilt.mobile.pixiv"
}
