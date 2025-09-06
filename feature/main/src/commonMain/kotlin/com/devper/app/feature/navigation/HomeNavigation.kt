package com.devper.app.feature.navigation

import kotlinx.serialization.Serializable

@Serializable
data class HomeSearch(
    val categoryId: Int?,
    val sort: Int?,
)

@Serializable
data object Home

@Serializable
data object Notification

@Serializable
data object HomeCategories

@Serializable
data object Settings

@Serializable
data class HomeDetail(
    val id: Int,
)
