package com.devper.app.feature.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Search

@Serializable
data class SearchDetail(
    val id: Int,
)
