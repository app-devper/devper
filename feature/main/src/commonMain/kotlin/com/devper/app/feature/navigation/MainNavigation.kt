package com.devper.app.feature.navigation

import com.devper.app.design.resources.Res
import com.devper.app.design.resources.cart
import com.devper.app.design.resources.cart_border
import com.devper.app.design.resources.heart2
import com.devper.app.design.resources.heart_border2
import com.devper.app.design.resources.home
import com.devper.app.design.resources.home_border
import com.devper.app.design.resources.profile
import com.devper.app.design.resources.profile_border
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
sealed class MainNavigation(
    val route: String,
    val title: String,
    val selectedIcon: DrawableResource,
    val unSelectedIcon: DrawableResource,
) {

    data object Home : MainNavigation(
        route = "home",
        title = "Home",
        selectedIcon = Res.drawable.home,
        unSelectedIcon = Res.drawable.home_border
    )

    data object Wishlist : MainNavigation(
        route = "wishlist",
        title = "Wishlist",
        selectedIcon = Res.drawable.heart2,
        unSelectedIcon = Res.drawable.heart_border2
    )

    data object Product : MainNavigation(
        route = "product",
        title = "Product",
        selectedIcon = Res.drawable.heart2,
        unSelectedIcon = Res.drawable.heart_border2
    )

    data object Cart : MainNavigation(
        route = "cart",
        title = "Cart",
        selectedIcon = Res.drawable.cart,
        unSelectedIcon = Res.drawable.cart_border
    )

    data object Profile : MainNavigation(
        route = "profile",
        title = "Profile",
        selectedIcon = Res.drawable.profile,
        unSelectedIcon = Res.drawable.profile_border
    )

}

