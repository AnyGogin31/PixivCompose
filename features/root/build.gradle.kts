plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.kotlinCompose)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.desingsystem)
                implementation(projects.core.navigation)

                implementation(projects.features.auth)
                implementation(projects.features.illustration)
                implementation(projects.features.main)

                implementation(project.dependencies.platform(libs.compose.bom))
                implementation(libs.bundles.compose.ui)
                implementation(libs.bundles.compose.additions)
                implementation(libs.compose.navigation)

                implementation(libs.koin.core)
                implementation(libs.koin.android)
                implementation(libs.koin.android.compose)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.features.root"

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
