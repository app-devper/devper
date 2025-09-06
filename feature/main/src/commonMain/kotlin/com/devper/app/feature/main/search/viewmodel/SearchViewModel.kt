package com.devper.app.feature.main.search.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import business.domain.main.Category
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState
import com.devper.app.core.domain.constants.CUSTOM_TAG

class SearchViewModel : ViewModel() {
    val state: MutableState<SearchState> = mutableStateOf(SearchState())

    fun onTriggerEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.Search -> {
                search(
                    minPrice = event.minPrice,
                    maxPrice = event.maxPrice,
                    categories = event.categories,
                )
            }

            is SearchEvent.OnUpdateSelectedSort -> {
                onUpdateSelectedSort(event.value)
            }

            is SearchEvent.OnUpdatePriceRange -> {
                onUpdatePriceRange(event.value)
            }

            is SearchEvent.OnUpdateSortDialogState -> {
                onUpdateSortDialogState(event.value)
            }

            is SearchEvent.OnUpdateFilterDialogState -> {
                onUpdateFilterDialogState(event.value)
            }

            is SearchEvent.OnUpdateSearchText -> {
                onUpdateSearchText(event.value)
            }

            is SearchEvent.GetNextPage -> {
                getNextPage()
            }

            is SearchEvent.OnUpdateSelectedCategory -> {
                onUpdateSelectedCategory(categories = event.categories)
            }

            is SearchEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is SearchEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is SearchEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is SearchEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    init {
        getSearchFilter()
    }

    private fun onUpdateSelectedSort(value: Int) {
        state.value = state.value.copy(selectedSort = value)
    }

    private fun onUpdatePriceRange(value: ClosedFloatingPointRange<Float>) {
        state.value = state.value.copy(selectedRange = value)
    }

    private fun onUpdateSortDialogState(value: UIComponentState) {
        state.value = state.value.copy(sortDialogState = value)
    }

    private fun onUpdateFilterDialogState(value: UIComponentState) {
        state.value = state.value.copy(filterDialogState = value)
    }

    private fun onUpdateSearchText(value: String) {
        state.value = state.value.copy(searchText = value)
    }

    private fun getSearchFilter() {
    }

    private fun search(
        minPrice: Int? = null,
        maxPrice: Int? = null,
        categories: List<Category>? = null,
    ) {
        resetPaging()
    }

    private fun resetPaging() {
        state.value = state.value.copy(page = 1)
        state.value = state.value.copy(hasNextPage = true)
    }

    private fun getNextPage() {
        state.value = state.value.copy(page = state.value.page + 1)
    }

    private fun onUpdateSelectedCategory(categories: List<Category>) {
        state.value = state.value.copy(selectedCategory = categories)
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
        getSearchFilter()
    }

    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }
}
