package com.devper.app.feature.main.cart.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.domain.constants.CUSTOM_TAG

class CartViewModel : ViewModel() {
    val state: MutableState<CartState> = mutableStateOf(CartState())

    fun onTriggerEvent(event: CartEvent) {
        when (event) {
            is CartEvent.AddProduct -> {
                addProduct(id = event.id)
            }

            is CartEvent.DeleteFromBasket -> {
                deleteFromBasket(id = event.id)
            }

            is CartEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is CartEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is CartEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is CartEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    init {
        getCart()
    }

    private fun getCart() {
    }

    private fun deleteFromBasket(id: Int) {
    }

    private fun addProduct(id: Int) {
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
        getCart()
    }

    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }
}
