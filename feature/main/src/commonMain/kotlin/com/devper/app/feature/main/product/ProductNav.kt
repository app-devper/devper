package com.devper.app.feature.main.product

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.devper.app.feature.main.detail.ProductDetailScreen
import com.devper.app.feature.main.product.viewmodel.ProductViewModel
import com.devper.app.feature.main.product_detail.viewmodel.ProductDetailEvent
import com.devper.app.feature.main.product_detail.viewmodel.ProductDetailViewModel
import com.devper.app.feature.main.wishlist.ProductScreen
import com.devper.app.feature.navigation.Product
import com.devper.app.feature.navigation.ProductDetail
import org.koin.compose.koinInject

@Composable
fun ProductNav() {
    val navigator = rememberNavController()
    NavHost(
        startDestination = Product,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Product> {
            val viewModel: ProductViewModel = koinInject()
            ProductScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent
            ) {
                navigator.navigate(ProductDetail(it))
            }
        }

        composable<ProductDetail> { backStackEntry ->
            val argument: ProductDetail = backStackEntry.toRoute()
            val id = argument.id
            id.let {
                val viewModel: ProductDetailViewModel = koinInject()
                LaunchedEffect(id) {
                    viewModel.onTriggerEvent(ProductDetailEvent.GetProduct(id))
                }
                ProductDetailScreen(
                    state = viewModel.state.value,
                    events = viewModel::onTriggerEvent,
                    popup = {
                        navigator.popBackStack()
                    },
                )
            }
        }
    }
}