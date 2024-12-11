plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.desingsystem)
                implementation(projects.core.navigation)

                implementation(projects.features.root)
            }
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.shared"
}
