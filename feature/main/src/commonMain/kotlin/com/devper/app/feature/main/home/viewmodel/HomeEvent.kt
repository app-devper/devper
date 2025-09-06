package com.devper.app.feature.main.home.viewmodel

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent

sealed class HomeEvent {
    data object OnRemoveHeadFromQueue : HomeEvent()

    data class Error(
        val uiComponent: UIComponent,
    ) : HomeEvent()

    data object OnRetryNetwork : HomeEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState,
    ) : HomeEvent()

    data class Like(
        val id: Int,
    ) : HomeEvent()
}
