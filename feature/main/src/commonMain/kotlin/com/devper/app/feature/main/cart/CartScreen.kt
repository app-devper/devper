package com.devper.app.feature.main.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import business.domain.main.Basket
import com.devper.app.core.design.component.DEFAULT__BUTTON_SIZE
import com.devper.app.core.design.component.DefaultButton
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.component.Spacer_16dp
import com.devper.app.core.design.component.Spacer_4dp
import com.devper.app.core.design.component.noRippleClickable
import com.devper.app.core.design.component.rememberCustomImagePainter
import com.devper.app.core.design.theme.BorderColor
import com.devper.app.design.resources.basket_is_empty
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.ui.main.cart.view_model.CartEvent
import presentation.ui.main.cart.view_model.CartState
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.proceed_to_checkout
import com.devper.app.design.resources.total_cost


@OptIn(ExperimentalResourceApi::class)
@Composable
fun CartScreen(
    state: CartState,
    events: (CartEvent) -> Unit,
    navigateToDetail: (Int) -> Unit,
    navigateToCheckout: () -> Unit,
) {


    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(CartEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(CartEvent.OnRetryNetwork) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize().align(Alignment.Center)) {
                items(state.baskets) {
                    CartBox(
                        it,
                        addMoreProduct = {
                            events(CartEvent.AddProduct(it.productId))
                        },
                        navigateToDetail = navigateToDetail
                    ) {
                        events(CartEvent.DeleteFromBasket(it.productId))
                    }
                }
            }

            if (state.baskets.isNotEmpty()) {
                Box(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()) {
                    ProceedButtonBox(
                        state.totalCost
                    ) {
                        navigateToCheckout()
                    }
                }
            }


            if (state.baskets.isEmpty()) {
                Box(
                    modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(Res.string.basket_is_empty),
                        style = MaterialTheme.typography.labelLarge,
                        color = BorderColor,
                    )
                }
            }


        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProceedButtonBox(totalCost: String, onClick: () -> Unit) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(Res.string.total_cost), style = MaterialTheme.typography.titleMedium)
                Text(totalCost, style = MaterialTheme.typography.titleLarge)
            }

            Spacer_16dp()

            DefaultButton(
                modifier = Modifier.fillMaxWidth().height(DEFAULT__BUTTON_SIZE),
                text = stringResource(Res.string.proceed_to_checkout)
            ) {
                onClick()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartBox(
    basket: Basket,
    navigateToDetail: (Int) -> Unit,
    addMoreProduct: () -> Unit,
    deleteFromBasket: () -> Unit
) {
    var show by remember { mutableStateOf(true) }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                deleteFromBasket()
                show = false
                true
            } else false
        }
    )

    AnimatedVisibility(
        show, exit = fadeOut(spring())
    ) {
        SwipeToDismissBox(
            state = dismissState,
            modifier = Modifier,
            backgroundContent = {
                DismissBackground(dismissState)
            },
            content = {
                DismissCartContent(
                    basket,
                    addMoreProduct = addMoreProduct,
                    navigateToDetail = navigateToDetail
                )
            })
    }
}

@Composable
fun DismissCartContent(
    basket: Basket,
    addMoreProduct: () -> Unit,
    navigateToDetail: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                .height(150.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier.fillMaxHeight().padding(vertical = 16.dp)
                    .weight(.3f)
                    .clip(MaterialTheme.shapes.small)
                    .noRippleClickable {
                        navigateToDetail(basket.productId)
                    }
            ) {
                Image(
                    painter = rememberCustomImagePainter(basket.image),
                    null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer_16dp()

            Column(modifier = Modifier.weight(.4f)) {
                Text(
                    basket.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer_4dp()
                Text(
                    basket.category.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer_4dp()
                Text(
                    basket.getPrice(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Row(
                modifier = Modifier.fillMaxHeight()
                    .weight(.3f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier.size(25.dp),
                    shape = MaterialTheme.shapes.small,
                    onClick = {},
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("-")
                    }
                }
                Spacer_4dp()
                Text(basket.count.toString())
                Spacer_4dp()
                Card(
                    modifier = Modifier.size(25.dp),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        addMoreProduct()
                    },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            "+",
                        )
                    }
                }

            }
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = MaterialTheme.colorScheme.primary.copy(alpha = .2f)
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        if (direction == SwipeToDismissBoxValue.EndToStart) Icon(
            Icons.Default.Delete,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
        Spacer(modifier = Modifier)
    }
}


