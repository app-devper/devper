package com.devper.shared.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountResponse(
    @SerialName("count")
    val count: Long,
)
