plugins {
    `kotlin-dsl`
}

group = "com.devper.app.buildlogic"

dependencies {
    compileOnly(libs.plugins.kotlin.serialization.toDep())
    compileOnly(libs.plugins.androidApplication.toDep())
    compileOnly(libs.plugins.androidLibrary.toDep())
    compileOnly(libs.plugins.jetbrainsCompose.toDep())
    compileOnly(libs.plugins.kotlinMultiplatform.toDep())
    compileOnly(libs.plugins.compose.compiler.toDep())
    compileOnly(libs.plugins.ktlint.toKtLintDep())
}

fun Provider<PluginDependency>.toDep() = map {
    "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
}

fun Provider<PluginDependency>.toKtLintDep() = map {
    "org.jlleitschuh.gradle:ktlint-gradle:${it.version}"
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = "com.devper.app.kotlinMultiplatform"
            implementationClass = "KotlinMultiPlatformConventionPlugin"
        }
        register("composeMultiplatform") {
            id = "com.devper.app.composeMultiplatform"
            implementationClass = "ComposeMultiPlatformConventionPlugin"
        }
    }
}