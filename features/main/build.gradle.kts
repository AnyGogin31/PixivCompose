plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.data)
                implementation(projects.domain)

                implementation(projects.core.desingsystem)
                implementation(projects.core.navigation)

                implementation(projects.features.illustration)

                implementation(project.dependencies.platform(libs.compose.bom))
                implementation(libs.bundles.compose.ui)
                implementation(libs.bundles.compose.additions)
                implementation(libs.compose.navigation)

                implementation(libs.coil.compose)
                implementation(libs.coil.network)

                implementation(libs.kotlin.serialization.json)

                implementation(libs.koin.core)
                implementation(libs.koin.android)
                implementation(libs.koin.android.compose)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.main"

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
}
