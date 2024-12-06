plugins {
    alias(libs.plugins.neilt.mobile.android.library)
    alias(libs.plugins.kotlinCompose)
}

kotlin {
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
