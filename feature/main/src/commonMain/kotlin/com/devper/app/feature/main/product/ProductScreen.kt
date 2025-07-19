package com.devper.app.feature.main.wishlist

import androidx.compose.runtime.Composable
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.feature.component.ProductsScreen
import com.devper.app.feature.main.product.viewmodel.ProductEvent
import com.devper.app.feature.main.product.viewmodel.ProductState
import org.koin.compose.koinInject

@Composable
fun ProductScreen(
    state: ProductState,
    events: (ProductEvent) -> Unit,
    navigateToDetail: (String) -> Unit
) {

    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(ProductEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(ProductEvent.OnRetryNetwork) }
    ) {

        ProductsScreen(
            viewModel = koinInject(),
            onSelected = { product ->
                navigateToDetail(product.id)
            },
            onMenu = { },
        )

    }
}



