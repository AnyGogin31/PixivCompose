plugins {
    alias(libs.plugins.neilt.multiplatform.compose)
}

android {
    namespace = "neilt.mobile.pixiv.resources"
}

compose.resources {
    publicResClass = true
    packageOfResClass = "neilt.mobile.pixiv.resources"
    generateResClass = always
}
