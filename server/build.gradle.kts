plugins {
    alias(libs.plugins.kotlinJvm)
    application
}

application {
    mainClass.set("com.devper.server.ApplicationKt")
}

dependencies {
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.compression)
    implementation(libs.ktor.server.content.negotiation)

    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.koin.ktor)

    implementation(libs.logback.classic)

    implementation(libs.mongodb.driver.kotlin.coroutine)

    implementation(projects.shared)

}