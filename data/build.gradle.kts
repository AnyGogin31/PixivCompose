plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    androidTarget()

    jvmToolchain(17)

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.domain)
                implementation(libs.koin.core)
                implementation(libs.moshi)
                implementation(libs.room)
                implementation(libs.room.kotlin)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.coroutines.test)
                implementation(libs.koin.test)
                implementation(libs.mockito.core)
                implementation(libs.mockito.kotlin)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.retrofit)
                implementation(libs.retrofit.converter.moshi)
                implementation(libs.okhttp3)
                implementation(libs.okhttp3.logging.interceptor)
            }
        }

        val androidInstrumentedTest by getting {
            dependencies {
                implementation(libs.junit.jupiter)
                implementation(libs.mockito.core)
                implementation(libs.mockito.kotlin)
                implementation(libs.android.test.core)
                implementation(libs.android.test.ext)
                implementation(libs.android.test.runner)
            }
        }
    }
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspAndroid", libs.moshi.kotlin.codegen)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
    arg("room.generateKotlin", "true")
}

room {
    schemaDirectory("$projectDir/schemas")
}

android {
    namespace = "neilt.mobile.pixiv.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

