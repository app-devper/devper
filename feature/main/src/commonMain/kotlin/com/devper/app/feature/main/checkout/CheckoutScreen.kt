package com.devper.app.feature.main.checkout

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import business.domain.main.Address
import com.devper.app.core.design.state.UIComponentState
import com.devper.app.core.design.component.DEFAULT__BUTTON_SIZE
import com.devper.app.core.design.component.DefaultButton
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.component.Spacer_12dp
import com.devper.app.core.design.component.Spacer_16dp
import com.devper.app.core.design.component.Spacer_32dp
import com.devper.app.core.design.component.Spacer_8dp
import com.devper.app.core.design.component.noRippleClickable
import com.devper.app.core.design.theme.BorderColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.ui.main.checkout.view_model.CheckoutEvent
import presentation.ui.main.checkout.view_model.CheckoutState
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.change
import com.devper.app.design.resources.checkout
import com.devper.app.design.resources.choose_shipping_type
import com.devper.app.design.resources.home
import com.devper.app.design.resources.location2
import com.devper.app.design.resources.shipping
import com.devper.app.design.resources.shipping_address
import com.devper.app.design.resources.shipping_cost
import com.devper.app.design.resources.submit
import com.devper.app.design.resources.total_cost
import com.devper.app.feature.component.SelectShippingDialog


@OptIn(ExperimentalResourceApi::class)
@Composable
fun CheckoutScreen(
    state: CheckoutState,
    events: (CheckoutEvent) -> Unit,
    navigateToAddress: () -> Unit,
    popup: () -> Unit
) {

    LaunchedEffect(key1 = state.buyingSuccess) {
        if (state.buyingSuccess) {
            popup()
        }
    }

    if (state.selectShippingDialogState == UIComponentState.Show) {
        SelectShippingDialog(state = state, events = events)
    }

    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(CheckoutEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(CheckoutEvent.OnRetryNetwork) },
        titleToolbar = stringResource(Res.string.checkout),
        startIconToolbar = Icons.AutoMirrored.Filled.ArrowBack,
        onClickStartIconToolbar = popup
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp).align(Alignment.TopCenter)) {

                Spacer_32dp()

                Text(stringResource(Res.string.shipping_address), style = MaterialTheme.typography.titleLarge)
                Spacer_12dp()
                ShippingBox(
                    title = stringResource(Res.string.home),
                    image = Res.drawable.location2, detail = state.selectedAddress.getShippingAddress()
                ) {
                    navigateToAddress()
                }

                Spacer_16dp()
                HorizontalDivider(color = BorderColor)
                Spacer_16dp()

                Text(stringResource(Res.string.choose_shipping_type), style = MaterialTheme.typography.titleLarge)
                Spacer_12dp()
                ShippingBox(
                    title = state.selectedShipping.title,
                    image = Res.drawable.shipping,
                    detail = state.selectedShipping.getEstimatedDay(),
                ) {
                    events(CheckoutEvent.OnUpdateSelectShippingDialogState(UIComponentState.Show))
                }


            }

            Box(modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()) {
                CheckoutButtonBox(
                    "$ ${state.totalCost}",
                    "$ ${state.selectedShipping.price}",
                    selectedAddress = state.selectedAddress,
                ) {
                    events(CheckoutEvent.BuyProduct)
                }
            }
        }

    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun CheckoutButtonBox(
    totalCost: String,
    shippingCost: String,
    selectedAddress: Address,
    onClick: () -> Unit
) {

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
                Text(stringResource(Res.string.shipping_cost), style = MaterialTheme.typography.titleMedium)
                Text(shippingCost, style = MaterialTheme.typography.titleLarge)
            }
            Spacer_8dp()

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
                text = stringResource(Res.string.submit),
                enabled = selectedAddress != Address()
            ) {
                onClick()
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ShippingBox(title: String, image: DrawableResource, detail: String, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Icon(
            painter = painterResource(image),
            null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer_8dp()
        Column(modifier = Modifier.fillMaxWidth(.7f)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(detail, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer_8dp()
        Box(modifier = Modifier.wrapContentHeight(), contentAlignment = Alignment.CenterEnd) {
            Box(
                modifier = Modifier.border(
                    1.dp,
                    color = BorderColor,
                    MaterialTheme.shapes.medium
                ).noRippleClickable { onClick() }
            ) {
                Text(
                    stringResource(Res.string.change),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}
