// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    // Android
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false

    // Kotlin
    alias(libs.plugins.kotlinAndroid) apply false
}