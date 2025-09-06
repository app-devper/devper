package com.devper.app.feature.main.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devper.app.feature.main.address.AddressScreen
import com.devper.app.feature.main.address.viewmodel.AddressViewModel
import com.devper.app.feature.main.editprofile.EditProfileScreen
import com.devper.app.feature.main.editprofile.viewmodel.EditProfileViewModel
import com.devper.app.feature.main.mycoupons.MyCouponsScreen
import com.devper.app.feature.main.mycoupons.viewmodel.MyCouponsViewModel
import com.devper.app.feature.main.myorders.MyOrdersScreen
import com.devper.app.feature.main.myorders.viewmodel.MyOrdersViewModel
import com.devper.app.feature.main.paymentmethod.PaymentMethodScreen
import com.devper.app.feature.main.paymentmethod.viewmodel.PaymentMethodViewModel
import com.devper.app.feature.main.profile.viewmodel.ProfileViewModel
import com.devper.app.feature.main.settings.viewmodel.SettingsViewModel
import com.devper.app.feature.navigation.ProfileNavigation
import org.koin.compose.koinInject
import presentation.ui.main.settings.SettingsScreen

@Composable
fun ProfileNav(logout: () -> Unit) {
    val navigator = rememberNavController()
    NavHost(
        startDestination = ProfileNavigation.Profile.route,
        navController = navigator,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(route = ProfileNavigation.Profile.route) {
            val viewModel: ProfileViewModel = koinInject()
            ProfileScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                navigateToAddress = {
                    navigator.navigate(ProfileNavigation.Address.route)
                },
                navigateToEditProfile = {
                    navigator.navigate(ProfileNavigation.EditProfile.route)
                },
                navigateToPaymentMethod = {
                    navigator.navigate(ProfileNavigation.PaymentMethod.route)
                },
                navigateToMyOrders = {
                    navigator.navigate(ProfileNavigation.MyOrders.route)
                },
                navigateToMyCoupons = {
                    navigator.navigate(ProfileNavigation.MyCoupons.route)
                },
                navigateToMyWallet = {
                    navigator.navigate(ProfileNavigation.MyWallet.route)
                },
                navigateToSettings = {
                    navigator.navigate(ProfileNavigation.Settings.route)
                },
            )
        }
        composable(route = ProfileNavigation.Settings.route) {
            val viewModel: SettingsViewModel = koinInject()
            SettingsScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                logout = logout,
                popup = {
                    navigator.popBackStack()
                },
            )
        }
        composable(route = ProfileNavigation.MyCoupons.route) {
            val viewModel: MyCouponsViewModel = koinInject()
            MyCouponsScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }
        }
        composable(route = ProfileNavigation.MyWallet.route) {
            /*val viewModel: MyWalletViewModel = koinInject()
            MyWalletScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }*/
        }
        composable(route = ProfileNavigation.MyOrders.route) {
            val viewModel: MyOrdersViewModel = koinInject()
            MyOrdersScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }
        }
        composable(route = ProfileNavigation.PaymentMethod.route) {
            val viewModel: PaymentMethodViewModel = koinInject()
            PaymentMethodScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }
        }
        composable(route = ProfileNavigation.EditProfile.route) {
            val viewModel: EditProfileViewModel = koinInject()
            EditProfileScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }
        }
        composable(route = ProfileNavigation.Address.route) {
            val viewModel: AddressViewModel = koinInject()
            AddressScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
            }
        }
    }
}
