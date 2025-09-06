package com.devper.app.feature.main.notifications.viewmodel

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent

sealed class NotificationsEvent {
    data object OnRemoveHeadFromQueue : NotificationsEvent()

    data class Error(
        val uiComponent: UIComponent,
    ) : NotificationsEvent()

    data object OnRetryNetwork : NotificationsEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState,
    ) : NotificationsEvent()
}
