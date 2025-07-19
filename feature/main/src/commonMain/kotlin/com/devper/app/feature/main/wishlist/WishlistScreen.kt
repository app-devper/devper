package com.devper.app.feature.main.wishlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devper.app.core.domain.constants.PAGINATION_PAGE_SIZE
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.component.Spacer_8dp
import com.devper.app.core.design.theme.BorderColor
import com.devper.app.feature.component.CategoryChipsBox
import com.devper.app.feature.component.ProductBox
import com.devper.app.feature.main.wishlist.viewmodel.WishlistEvent
import com.devper.app.feature.main.wishlist.viewmodel.WishlistState

@Composable
fun WishlistScreen(
    state: WishlistState,
    events: (WishlistEvent) -> Unit,
    navigateToDetail: (String) -> Unit
) {

    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(WishlistEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(WishlistEvent.OnRetryNetwork) }
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(state.wishlist.categories) {
                    CategoryChipsBox(category = it, isSelected = it == state.selectedCategory) {
                        events(WishlistEvent.OnUpdateSelectedCategory(it))
                    }
                }
            }

            Spacer_8dp()

            if (state.wishlist.products.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Wishlist is empty!",
                        style = MaterialTheme.typography.labelLarge,
                        color = BorderColor,
                    )
                }
            }


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                contentPadding = PaddingValues(8.dp),
                content = {
                    itemsIndexed(state.wishlist.products) { index, product ->

                        if ((index + 1) >= (state.page * PAGINATION_PAGE_SIZE) &&
                            state.progressBarState == ProgressBarState.Idle &&
                            state.hasNextPage
                        ) {
                            events(WishlistEvent.GetNextPage)
                        }

                        ProductBox(
                            product = product,
                            modifier = Modifier.fillMaxWidth(.5f),
                            onLikeClick = {
                                events(WishlistEvent.LikeProduct(product.id))
                            }
                        ) {
                            navigateToDetail(product.id.toString())
                        }
                    }
                }
            )

        }
    }
}



