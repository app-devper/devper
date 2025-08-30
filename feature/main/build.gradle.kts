plugins {
    alias(libs.plugins.devper.kotlinMultiplatform)
    alias(libs.plugins.devper.composeMultiplatform)
}

kotlin {

    sourceSets {
        commonMain.dependencies {

            implementation(projects.core.common)
            implementation(projects.core.domain)
            implementation(projects.core.design)

            implementation(libs.kotlinx.datetime)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.savedstate)
        }
    }
}