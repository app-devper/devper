package com.devper.app.feature.main.wishlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.devper.app.feature.main.detail.DetailNav
import com.devper.app.feature.main.wishlist.viewmodel.WishlistViewModel
import com.devper.app.feature.navigation.Wishlist
import com.devper.app.feature.navigation.WishlistDetail
import org.koin.compose.koinInject

@Composable
fun WishlistNav() {
    val navigator = rememberNavController()
    NavHost(
        startDestination = Wishlist,
        navController = navigator,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable<Wishlist> {
            val viewModel: WishlistViewModel = koinInject()
            WishlistScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
            ) {
                navigator.popBackStack()
                navigator.navigate(WishlistDetail(it))
            }
        }

        composable<WishlistDetail> { backStackEntry ->
            val argument: WishlistDetail = backStackEntry.toRoute()
            val id = argument.id
            DetailNav(id) {
                navigator.popBackStack()
            }
        }
    }
}
