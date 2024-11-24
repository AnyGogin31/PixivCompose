plugins {
    alias(libs.plugins.neilt.mobile.android.application)
}

dependencies {

    implementation(project(":data"))
    implementation(project(":domain"))

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)
}

android {
    namespace = "neilt.mobile.pixiv"
}
