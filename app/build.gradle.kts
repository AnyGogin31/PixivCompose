plugins {
    alias(libs.plugins.neilt.application.android)
}

apply(from = "../scripts/git-hooks.gradle.kts")

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.android.activity)
            implementation(libs.koin.android)
            implementation(projects.features.auth)
        }

        commonMain.dependencies {
            implementation(libs.coil.compose)
            implementation(projects.shared)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "neilt.mobile.pixiv.app"
}

compose.desktop {
    application {
        mainClass = "neilt.mobile.pixiv.MainKt"
    }
}
