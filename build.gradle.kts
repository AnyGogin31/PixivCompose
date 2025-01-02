// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    // Android
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false

    // Kotlin
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false

    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.kotlin.serialization) apply false

    // Static Analyzer
    alias(libs.plugins.ktlint) apply false
}