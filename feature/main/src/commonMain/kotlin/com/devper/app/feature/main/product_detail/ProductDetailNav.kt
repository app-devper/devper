package com.devper.app.feature.main.product_detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devper.app.feature.main.detail.ProductDetailScreen
import com.devper.app.feature.main.product_detail.viewmodel.ProductDetailEvent
import com.devper.app.feature.main.product_detail.viewmodel.ProductDetailViewModel
import com.devper.app.feature.navigation.DetailNavigation
import org.koin.compose.koinInject

@Composable
fun ProductDetailNav(id: String, popUp: () -> Unit) {
    val navigator = rememberNavController()
    NavHost(
        startDestination = DetailNavigation.Detail.route,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = DetailNavigation.Detail.route) {
            val viewModel: ProductDetailViewModel = koinInject()
            LaunchedEffect(id) {
                viewModel.onTriggerEvent(ProductDetailEvent.GetProduct(id))
            }
            ProductDetailScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                popup = {
                    popUp()
                },
            )
        }
    }
}
