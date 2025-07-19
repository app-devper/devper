package com.devper.app.feature.main.search.viewmodel

import business.domain.main.Category
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState

sealed class SearchEvent {

    data class Search(
        val minPrice: Int? = null,
        val maxPrice: Int? = null,
        val categories: List<Category>? = null,
    ) : SearchEvent()

    data object GetNextPage : SearchEvent()

    data class OnUpdateSelectedSort(val value: Int) : SearchEvent()

    data class OnUpdatePriceRange(val value: ClosedFloatingPointRange<Float>) : SearchEvent()

    data class OnUpdateSelectedCategory(val categories: List<Category>) : SearchEvent()

    data class OnUpdateSearchText(val value: String) : SearchEvent()

    data class OnUpdateSortDialogState(val value: UIComponentState) : SearchEvent()

    data class OnUpdateFilterDialogState(val value: UIComponentState) : SearchEvent()

    data object OnRemoveHeadFromQueue : SearchEvent()

    data class Error(
        val uiComponent: UIComponent
    ) : SearchEvent()

    data object OnRetryNetwork : SearchEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState
    ) : SearchEvent()


}
