package com.devper.app.feature.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Cart

@Serializable
data object Checkout

@Serializable
data object Address

@Serializable
data class CartDetail(val id: String)


