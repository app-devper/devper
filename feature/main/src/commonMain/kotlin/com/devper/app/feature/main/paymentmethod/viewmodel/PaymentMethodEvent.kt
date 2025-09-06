package com.devper.app.feature.main.paymentmethod.viewmodel

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent

sealed class PaymentMethodEvent {
    data class OnUpdateSelectedPaymentMethod(
        val value: Int,
    ) : PaymentMethodEvent()

    data object OnRemoveHeadFromQueue : PaymentMethodEvent()

    data class Error(
        val uiComponent: UIComponent,
    ) : PaymentMethodEvent()

    data object OnRetryNetwork : PaymentMethodEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState,
    ) : PaymentMethodEvent()
}
