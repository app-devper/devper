package com.devper.app.feature.main.search.viewmodel

import business.domain.main.Category
import business.domain.main.Search
import business.domain.main.SearchFilter
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState

data class SearchState(
    val selectedCategory: List<Category> = listOf(),
    val selectedRange: ClosedFloatingPointRange<Float> = 0f..10f,
    val page: Int = 1,
    val hasNextPage: Boolean = true,
    val searchText: String = "",
    val searchFilter: SearchFilter = SearchFilter(),
    val search: Search = Search(),
    val selectedSort: Int = 0,
    val filterDialogState: UIComponentState = UIComponentState.Hide,
    val sortDialogState: UIComponentState = UIComponentState.Hide,

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val networkState: NetworkState = NetworkState.Good,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)
