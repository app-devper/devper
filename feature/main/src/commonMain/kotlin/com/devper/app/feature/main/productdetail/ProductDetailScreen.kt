package com.devper.app.feature.main.detail

import androidx.compose.runtime.Composable
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.feature.component.ProductDetailWidget
import com.devper.app.feature.main.productdetail.viewmodel.ProductDetailEvent
import com.devper.app.feature.main.productdetail.viewmodel.ProductDetailState

@Composable
fun ProductDetailScreen(
    popup: () -> Unit,
    state: ProductDetailState,
    events: (ProductDetailEvent) -> Unit,
) {
    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(ProductDetailEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(ProductDetailEvent.OnRetryNetwork) },
    ) {
        ProductDetailWidget(
            product = state.product,
            onBack = { popup() },
            onEdit = {
            },
            onClickEdit = {
            },
        )
    }
}
