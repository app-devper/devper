package com.devper.shared.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SystemResponse(
    @SerialName("id")
    val id: String,
    @SerialName("clientId")
    val clientId: String,
    @SerialName("systemName")
    val systemName: String,
    @SerialName("systemCode")
    val systemCode: String,
    @SerialName("host")
    val host: String,
)
