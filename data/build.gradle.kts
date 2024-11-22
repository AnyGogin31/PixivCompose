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
}

android {
    namespace = "neilt.mobile.pixiv.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
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
