plugins {
    alias(libs.plugins.devper.kotlinMultiplatform)
    alias(libs.plugins.devper.composeMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            api(libs.kotlinx.datetime)
            api(libs.coil.compose)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.devper.app.design.resources"
    generateResClass = always
}