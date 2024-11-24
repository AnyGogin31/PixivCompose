plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.neilt.mobile.android.room)
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
}
