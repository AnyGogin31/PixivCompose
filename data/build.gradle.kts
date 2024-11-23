plugins {

    // Android
    alias(libs.plugins.androidLibrary)

    // Kotlin
    alias(libs.plugins.kotlinAndroid)

    // KSP
    alias(libs.plugins.ksp)

    // Room
    alias(libs.plugins.room)
}

dependencies {

    implementation(project(":domain"))

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit4)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)

    // OkHttp
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)

    // Moshi
    implementation(libs.moshi)
    ksp(libs.moshi.kotlin.codegen)

    // Room
    implementation(libs.room)
    implementation(libs.room.kotlin)
    ksp(libs.room.compiler)

    // JUnit 5
    testImplementation(libs.junit.jupiter)

    // Kotlin Test
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.coroutines.test)

    // Mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)

    // Android-specific dependencies
    androidTestImplementation(libs.android.test.core)
    androidTestImplementation(libs.android.test.ext)
    androidTestImplementation(libs.android.test.runner)
}

android {
    namespace = "neilt.mobile.pixiv.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}
