package com.devper.app.feature.main.detail.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.devper.app.core.domain.constants.CUSTOM_TAG

import androidx.lifecycle.ViewModel
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import presentation.ui.main.detail.view_model.DetailEvent
import presentation.ui.main.detail.view_model.DetailState


class DetailViewModel(

) : ViewModel() {

    val state: MutableState<DetailState> = mutableStateOf(DetailState())

    fun onTriggerEvent(event: DetailEvent) {
        when (event) {

            is DetailEvent.Like -> {
                likeProduct(id = event.id)
            }

            is DetailEvent.AddBasket -> {
                addBasket(id = event.id)
            }

            is DetailEvent.OnUpdateSelectedImage -> {
                onUpdateSelectedImage(event.value)
            }

            is DetailEvent.GetProduct -> {
                getProduct(event.id)
            }

            is DetailEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is DetailEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is DetailEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is DetailEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    private fun onUpdateSelectedImage(value: String) {
        state.value = state.value.copy(selectedImage = value)
    }


    private fun likeProduct(id: Int) {

    }


    private fun addBasket(id: Int) {

    }


    private fun updateLike() {

    }

    private fun getProduct(id: String) {

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
        getProduct(state.value.product.id.toString())
    }

    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }


}