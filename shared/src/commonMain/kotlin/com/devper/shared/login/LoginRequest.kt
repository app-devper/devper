package com.devper.shared.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class LoginRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        @SerialName("username")
        val username: String,
        @SerialName("password")
        val password: String,
        @SerialName("system")
        val system: String
    )
}



