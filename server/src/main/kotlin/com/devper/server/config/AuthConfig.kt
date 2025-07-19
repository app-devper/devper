package com.devper.server.config

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.devper.server.model.User
import java.util.*

open class AuthConfig(config: Config) {
    private val validityInMs = 3_600_000 * 2
    private val algorithm = Algorithm.HMAC256(config.secretKey)

    val verifier: JWTVerifier = JWT.require(algorithm)
        .withIssuer("")
        .withAudience("")
        .build()

    fun sign(user: User): String = JWT.create()
        .withExpiresAt(expiresAt())
        .withSubject(user.id.toString())
        .withIssuer("")
        .withAudience("")
        .sign(algorithm)

    private fun expiresAt() = Date(System.currentTimeMillis() + validityInMs)

}
