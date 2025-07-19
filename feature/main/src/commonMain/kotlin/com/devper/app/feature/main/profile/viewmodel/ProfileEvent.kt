package com.devper.app.feature.main.profile.viewmodel

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent

sealed class ProfileEvent {
   data object OnRemoveHeadFromQueue : ProfileEvent()

    data class Error(
        val uiComponent: UIComponent
    ) : ProfileEvent()

   data object OnRetryNetwork : ProfileEvent()
    data class OnUpdateNetworkState(
        val networkState: NetworkState
    ) : ProfileEvent()

}
