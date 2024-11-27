// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    // Android
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false

    // Kotlin
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinCompose) apply false
    alias(libs.plugins.kotlinSerialization) apply false

    // KSP
    alias(libs.plugins.ksp) apply false

    // Room
    alias(libs.plugins.room) apply false

    // Static Analyzer
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ktlint) apply false
}