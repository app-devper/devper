plugins {
    alias(libs.plugins.devper.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.common)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kermit)
        }
    }
}