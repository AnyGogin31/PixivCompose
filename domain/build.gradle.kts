plugins {
    alias(libs.plugins.neilt.mobile.android.library)
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
}
