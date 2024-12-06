plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.kotlinMultiplatform)
}


kotlin {
    androidTarget()

    jvmToolchain(17)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.navigation)

                implementation(libs.koin.android.compose)

                implementation(project.dependencies.platform(libs.compose.bom))
                implementation(libs.bundles.compose.ui)
                implementation(libs.bundles.compose.additions)

                implementation(libs.compose.navigation)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.desingsystem"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.toString()
    }

    packaging {
        jniLibs {
            keepDebugSymbols.add("**/libandroidx.graphics.path.so")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
