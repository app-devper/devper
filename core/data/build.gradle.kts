plugins {
    alias(libs.plugins.devper.kotlinMultiplatform)
}

kotlin {
    sourceSets {

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        commonMain.dependencies {
            implementation(libs.bundles.ktor.common)

            implementation(libs.multiplatform.settings)

            implementation(projects.shared)
            implementation(projects.core.domain)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.java)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}