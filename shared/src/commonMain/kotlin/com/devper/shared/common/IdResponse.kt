package com.devper.shared.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IdResponse(
    @SerialName("id")
    val id: String,
)
