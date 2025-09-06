package com.devper.app.feature.main.wishlist.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import business.domain.main.Category
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.domain.constants.CUSTOM_TAG

class WishlistViewModel : ViewModel() {
    val state: MutableState<WishlistState> = mutableStateOf(WishlistState())

    fun onTriggerEvent(event: WishlistEvent) {
        when (event) {
            is WishlistEvent.LikeProduct -> {
                likeProduct(id = event.id)
            }

            is WishlistEvent.GetNextPage -> {
                getNextPage()
            }

            is WishlistEvent.OnUpdateSelectedCategory -> {
                onUpdateSelectedCategory(category = event.category)
            }

            is WishlistEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is WishlistEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is WishlistEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is WishlistEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    init {
        getWishlist()
    }

    private fun likeProduct(id: Int) {
    }

    private fun getWishlist() {
    }

    private fun getNextPage() {
        state.value = state.value.copy(page = state.value.page + 1)
    }

    private fun onUpdateSelectedCategory(category: Category) {
        state.value = state.value.copy(selectedCategory = category)
        getWishlist()
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
        getWishlist()
    }

    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }
}
