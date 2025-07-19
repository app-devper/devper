package com.devper.app.feature.main.home.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devper.app.core.domain.constants.CUSTOM_TAG
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.domain.usecases.GetProductsUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    val state: MutableState<HomeState> = mutableStateOf(HomeState())

    fun getProduct() {
        viewModelScope.launch {
            val result = getProductsUseCase(Unit)
            result.fold(
                onSuccess = { products ->
                },
                onFailure = { error ->
                }
            )
        }
    }

    fun onTriggerEvent(event: HomeEvent) {
        when (event) {

            is HomeEvent.Like -> {
                likeProduct(id = event.id)
            }

            is HomeEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is HomeEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is HomeEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is HomeEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    init {
        getHome()
    }


    private fun likeProduct(id: Int) {

    }


    private fun updateLike(id: Int) {

        updateMostSaleProductLike(id = id)
        updateNewestProductLike(id = id)
        updateFlashSaleProductLike(id = id)

    }

    private fun updateFlashSaleProductLike(id: Int) {

        val tmpFlashSale = state.value.home.flashSale.products.toMutableList()
        var currentItemFlashSale = tmpFlashSale.find { it.id == id }
        val indexCurrentItemFlashSale = tmpFlashSale.indexOf(currentItemFlashSale)
        val newLikes3 =
            if (currentItemFlashSale?.isLike == true) currentItemFlashSale.likes.minus(1) else currentItemFlashSale?.likes?.plus(
                1
            )
        currentItemFlashSale = currentItemFlashSale?.copy(
            isLike = !currentItemFlashSale.isLike,
            likes = newLikes3 ?: 0
        )
        if (currentItemFlashSale != null) {
            tmpFlashSale[indexCurrentItemFlashSale] = currentItemFlashSale
        }

        state.value =
            state.value.copy(
                home = state.value.home.copy(
                    flashSale = state.value.home.flashSale.copy(
                        products = tmpFlashSale
                    )
                )
            )
    }

    private fun updateNewestProductLike(id: Int) {
        val tmpNewestProduct = state.value.home.newestProduct.toMutableList()
        var currentItemNewestProduct = tmpNewestProduct.find { it.id == id }
        val indexCurrentItemNewestProduct = tmpNewestProduct.indexOf(currentItemNewestProduct)

        val newLikes2 =
            if (currentItemNewestProduct?.isLike == true) currentItemNewestProduct.likes.minus(1) else currentItemNewestProduct?.likes?.plus(
                1
            )

        currentItemNewestProduct = currentItemNewestProduct?.copy(
            isLike = !currentItemNewestProduct.isLike,
            likes = newLikes2 ?: 0
        )


        if (currentItemNewestProduct != null) {
            tmpNewestProduct[indexCurrentItemNewestProduct] = currentItemNewestProduct
        }

        state.value =
            state.value.copy(home = state.value.home.copy(newestProduct = tmpNewestProduct))
    }

    private fun updateMostSaleProductLike(id: Int) {
        val tmpMostSale = state.value.home.mostSale.toMutableList()
        var currentItemMostSale = tmpMostSale.find { it.id == id }
        val indexCurrentItemMostSale = tmpMostSale.indexOf(currentItemMostSale)

        val newLikes1 =
            if (currentItemMostSale?.isLike == true) currentItemMostSale.likes.minus(1) else currentItemMostSale?.likes?.plus(
                1
            )

        currentItemMostSale =
            currentItemMostSale?.copy(isLike = !currentItemMostSale.isLike, likes = newLikes1 ?: 0)

        if (currentItemMostSale != null) {
            tmpMostSale[indexCurrentItemMostSale] = currentItemMostSale
        }

        state.value =
            state.value.copy(home = state.value.home.copy(mostSale = tmpMostSale))
    }


    private fun getHome() {

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
        getHome()
    }


    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }


}