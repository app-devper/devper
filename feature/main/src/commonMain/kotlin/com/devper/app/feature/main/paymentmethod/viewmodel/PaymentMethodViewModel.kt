package com.devper.app.feature.main.paymentmethod.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.domain.constants.CUSTOM_TAG

class PaymentMethodViewModel : ViewModel() {
    val state: MutableState<PaymentMethodState> = mutableStateOf(PaymentMethodState())

    fun onTriggerEvent(event: PaymentMethodEvent) {
        when (event) {
            is PaymentMethodEvent.OnUpdateSelectedPaymentMethod -> {
                onUpdateSelectedPaymentMethod(event.value)
            }

            is PaymentMethodEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is PaymentMethodEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is PaymentMethodEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is PaymentMethodEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    private fun onUpdateSelectedPaymentMethod(value: Int) {
        state.value = state.value.copy(selectedPaymentMethod = value)
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        if (uiComponent is UIComponent.None) {
            println("${CUSTOM_TAG}: onTriggerEvent:  ${uiComponent.message}")
            return
        }

        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
        state.value = state.value.copy(errorQueue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove() // can throw exception if empty
            state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
            state.value = state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            println("${CUSTOM_TAG}: removeHeadMessage: Nothing to remove from DialogQueue")
        }
    }

    private fun onRetryNetwork() {
    }

    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }
}
