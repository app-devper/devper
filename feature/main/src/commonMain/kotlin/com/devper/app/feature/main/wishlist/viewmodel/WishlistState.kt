package com.devper.app.feature.main.wishlist.viewmodel

import business.domain.main.Category
import business.domain.main.Wishlist
import business.domain.main.categoryAll
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent

data class WishlistState(
    val categoryId: Int? = null,
    val page: Int = 1,
    val hasNextPage: Boolean = true,
    val wishlist: Wishlist = Wishlist(),
    val selectedCategory: Category = categoryAll,
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val networkState: NetworkState = NetworkState.Good,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)
