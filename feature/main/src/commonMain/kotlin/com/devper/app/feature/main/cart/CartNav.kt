package com.devper.app.feature.main.cart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.koin.compose.koinInject
import com.devper.app.feature.main.address.AddressScreen
import presentation.ui.main.address.view_model.AddressViewModel
import presentation.ui.main.cart.view_model.CartViewModel
import com.devper.app.feature.main.checkout.CheckoutScreen
import com.devper.app.feature.navigation.Address
import com.devper.app.feature.navigation.Cart
import com.devper.app.feature.navigation.CartDetail
import com.devper.app.feature.navigation.Checkout
import presentation.ui.main.checkout.view_model.CheckoutViewModel
import com.devper.app.feature.main.detail.DetailNav

@Composable
fun CartNav() {
    val navigator = rememberNavController()
    NavHost(
        startDestination = Cart,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Cart> {
            val viewModel: CartViewModel = koinInject()
            CartScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                navigateToDetail = {
                    navigator.popBackStack()
                    navigator.navigate(CartDetail("$it"))
                }, navigateToCheckout = {
                    navigator.navigate(Checkout)
                })
        }
        composable<Checkout> {
            val viewModel: CheckoutViewModel = koinInject()
            CheckoutScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                navigateToAddress = {
                    navigator.navigate(Address)
                },
                popup = { navigator.popBackStack() },
            )
        }
        composable<Address> {
            val viewModel: AddressViewModel = koinInject()
            AddressScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                popup = { navigator.popBackStack() },
            )
        }
        composable<CartDetail> { backStackEntry ->
            val detail: CartDetail = backStackEntry.toRoute()
            DetailNav(detail.id) {
                navigator.popBackStack()
            }
        }
    }
}
