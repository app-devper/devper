package com.devper.app.feature.main.address.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState
import com.devper.app.core.domain.constants.CUSTOM_TAG

class AddressViewModel : ViewModel() {
    val state: MutableState<AddressState> = mutableStateOf(AddressState())

    fun onTriggerEvent(event: AddressEvent) {
        when (event) {
            is AddressEvent.AddAddress -> {
                addAddress(
                    country = event.country,
                    address = event.address,
                    city = event.city,
                    state = event.state,
                    zipCode = event.zipCode,
                )
            }

            is AddressEvent.OnUpdateAddAddressDialogState -> {
                onUpdateAddAddressDialogState(event.value)
            }

            is AddressEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is AddressEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is AddressEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is AddressEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    init {
        getAddresses()
    }

    private fun addAddress(
        country: String,
        address: String,
        city: String,
        state: String,
        zipCode: String,
    ) {
    }

    private fun getAddresses() {
    }

    private fun onUpdateAddAddressDialogState(value: UIComponentState) {
        state.value = state.value.copy(addAddressDialogState = value)
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
        getAddresses()
    }

    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }
}
