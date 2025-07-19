package com.devper.app.feature.navigation

import androidx.navigation.NamedNavArgument

sealed class ProfileNavigation(
    val route: String, val arguments: List<NamedNavArgument>
) {
   data object Profile : ProfileNavigation(route = "profile", arguments = emptyList())
   data object Address : ProfileNavigation(route = "address", arguments = emptyList())
   data object EditProfile : ProfileNavigation(route = "edit-profile", arguments = emptyList())
   data object PaymentMethod : ProfileNavigation(route = "payment-method", arguments = emptyList())
   data object MyOrders : ProfileNavigation(route = "my-orders", arguments = emptyList())
   data object MyCoupons : ProfileNavigation(route = "my-coupons", arguments = emptyList())
   data object MyWallet : ProfileNavigation(route = "my-wallet", arguments = emptyList())
   data object Settings : ProfileNavigation(route = "settings", arguments = emptyList())

}

