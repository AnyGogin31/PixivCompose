plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

dependencies {

    // JUnit 5
    testImplementation(libs.junit.jupiter)

    // Kotlin Test
    testImplementation(libs.kotlin.test)

    // Android-specific dependencies
    testImplementation(libs.android.test.core)
    testImplementation(libs.android.test.ext)
}

android {
    namespace = "neilt.mobile.pixiv.domain"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}
