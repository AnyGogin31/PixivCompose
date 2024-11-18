plugins {

    // Android
    alias(libs.plugins.androidApplication)

    // Kotlin
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
}

dependencies {

    // Compose
    implementation(platform(libs.compose.bom))

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)

    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.compose.activity)
    implementation(libs.compose.material)

    // AndroidX
    implementation(libs.android.core)
    implementation(libs.android.lifecycle.runtime)

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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.toString()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    packaging {
        jniLibs {
            keepDebugSymbols.add("**/libandroidx.graphics.path.so")
        }
    }
}