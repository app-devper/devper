package com.devper.app.feature.main.categories.viewmodel

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent

sealed class CategoriesEvent {
    data object OnRemoveHeadFromQueue : CategoriesEvent()

    data class Error(
        val uiComponent: UIComponent,
    ) : CategoriesEvent()

    data object OnRetryNetwork : CategoriesEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState,
    ) : CategoriesEvent()
}
