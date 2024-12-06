plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.neilt.mobile.android.room)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.domain)
                implementation(libs.koin.core)
                implementation(libs.moshi)
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
    add("kspAndroid", libs.moshi.kotlin.codegen)
}

android {
    namespace = "neilt.mobile.pixiv.data"

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}
