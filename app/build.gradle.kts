plugins {

    // Android
    alias(libs.plugins.androidApplication)

    // Kotlin
    alias(libs.plugins.kotlinAndroid)
}

dependencies {

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)
}

android {
    namespace = "neilt.mobile.pixiv"
    compileSdk = 35

    defaultConfig {
        applicationId = "neilt.mobile.pixiv"

        minSdk = 24
        targetSdk = 35

        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}