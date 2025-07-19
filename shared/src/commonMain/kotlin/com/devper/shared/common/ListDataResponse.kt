package com.devper.shared.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListDataResponse<T>(
    @SerialName("data")
    val data: List<T>
)