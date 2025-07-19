package com.devper.app.feature.main.product_detail.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.devper.app.core.domain.constants.CUSTOM_TAG

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.domain.usecases.GetProductByIdUseCase
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {

    val state: MutableState<ProductDetailState> = mutableStateOf(ProductDetailState())

    fun onTriggerEvent(event: ProductDetailEvent) {
        when (event) {

            is ProductDetailEvent.GetProduct -> {
                getProduct(event.id)
            }

            is ProductDetailEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is ProductDetailEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is ProductDetailEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is ProductDetailEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    private fun getProduct(id: String) {
        viewModelScope.launch {
            val result = getProductByIdUseCase(id)
            result.fold(
                onSuccess = { product ->
                    state.value = state.value.copy(product = product)
                },
                onFailure = { error ->
                    // Handle error if needed
                }
            )
        }
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
        getProduct(state.value.product.id)
    }

    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }


}