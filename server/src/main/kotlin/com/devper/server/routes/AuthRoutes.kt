package com.devper.server.routes

import com.devper.server.config.AuthConfig
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.koin.ktor.ext.inject

fun Route.auth() {
    val jwt by inject<AuthConfig>()

    val namePath = "auth"

    post("$namePath/login") {
       call.respond(HttpStatusCode.OK, "")
    }
}
