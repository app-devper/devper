package com.devper.server

import com.devper.server.config.AuthConfig
import com.devper.server.di.appModule
import com.devper.server.di.authModule
import com.devper.server.exception.NotFoundException
import com.devper.server.routes.users
import com.devper.server.service.UserService
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.slf4j.event.Level

fun main() {
    embeddedServer(
        Netty,
        port = 8089,
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    log.info("Starting application")
    val authConfig by inject<AuthConfig>()
    val userService by inject<UserService>()

    install(Compression)

    install(CallLogging) {
        level = Level.INFO

    }

    install(Koin) {
        modules(
            listOf(
                authModule,
                appModule,
            )
        )
    }

    install(ContentNegotiation) {
        json(
            contentType = ContentType.Application.Json,
            json = Json {
                ignoreUnknownKeys = true
            }
        )
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, "Internal Server Error")
            throw cause
        }
        exception<NotFoundException> { call, cause ->
            call.respond(HttpStatusCode.NotFound, "Not Found")
            throw cause
        }
    }

    install(CORS) {
        anyHost()
        allowCredentials = true
        allowNonSimpleContentTypes = true
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Options)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlAllowHeaders)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
    }

    authentication {
        jwt {
            verifier(authConfig.verifier)
            validate {
                val id = it.payload.getClaim("user").asString()
                if (id != null) JWTPrincipal(it.payload) else null
            }
        }
    }

    routing {
        route("api/v2") {
            users(userService)
        }
    }
}