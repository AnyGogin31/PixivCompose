plugins {
    alias(libs.plugins.neilt.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.koin.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.json)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.sqldelight.coroutines)
                implementation(projects.core.domain)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.coroutines.test)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.driver)
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.mockito.core)
                implementation(libs.mockito.kotlin)
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

sqldelight {
    databases {
        create("PixivDatabase") {
            packageName.set("neilt.mobile.pixiv.data.local.db")
        }
    }
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
