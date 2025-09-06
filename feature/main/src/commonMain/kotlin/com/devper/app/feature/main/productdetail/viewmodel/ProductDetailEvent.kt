package com.devper.app.feature.main.productdetail.viewmodel

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent

sealed class ProductDetailEvent {
    data class GetProduct(
        val id: String,
    ) : ProductDetailEvent()

    data object OnRemoveHeadFromQueue : ProductDetailEvent()

    data class Error(
        val uiComponent: UIComponent,
    ) : ProductDetailEvent()

    data object OnRetryNetwork : ProductDetailEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState,
    ) : ProductDetailEvent()
}
