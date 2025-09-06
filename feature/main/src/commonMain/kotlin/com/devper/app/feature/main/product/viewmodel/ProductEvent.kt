package com.devper.app.feature.main.product.viewmodel

import business.domain.main.Category
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent

sealed class ProductEvent {
    data object GetNextPage : ProductEvent()

    data class OnUpdateSelectedCategory(
        val category: Category,
    ) : ProductEvent()

    data class LikeProduct(
        val id: Int,
    ) : ProductEvent()

    data object OnRemoveHeadFromQueue : ProductEvent()

    data class Error(
        val uiComponent: UIComponent,
    ) : ProductEvent()

    data object OnRetryNetwork : ProductEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState,
    ) : ProductEvent()
}
