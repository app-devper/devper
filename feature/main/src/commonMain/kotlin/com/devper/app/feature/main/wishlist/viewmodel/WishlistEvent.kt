package com.devper.app.feature.main.wishlist.viewmodel

import business.domain.main.Category
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent

sealed class WishlistEvent {

    data object GetNextPage : WishlistEvent()

    data class OnUpdateSelectedCategory(val category: Category) : WishlistEvent()

    data class LikeProduct(val id: Int) : WishlistEvent()

    data object OnRemoveHeadFromQueue : WishlistEvent()

    data class Error(
        val uiComponent: UIComponent
    ) : WishlistEvent()

    data object OnRetryNetwork : WishlistEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState
    ) : WishlistEvent()

}
