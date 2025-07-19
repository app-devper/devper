package com.devper.app.feature.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Wishlist

@Serializable
data class WishlistDetail(val id: String)
