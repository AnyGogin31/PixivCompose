plugins {
    alias(libs.plugins.neilt.mobile.android.application)
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))

    // AndroidX
    implementation(libs.android.browser)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)
}

android {
    namespace = "neilt.mobile.pixiv"
}
