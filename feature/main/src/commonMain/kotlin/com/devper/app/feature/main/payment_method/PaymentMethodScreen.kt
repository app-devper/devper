package com.devper.app.feature.main.payment_method

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.component.Spacer_16dp
import com.devper.app.core.design.component.Spacer_32dp
import com.devper.app.core.design.component.Spacer_8dp
import com.devper.app.core.design.component.noRippleClickable
import com.devper.app.core.design.theme.BorderColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.ui.main.payment_method.view_model.PaymentMethodEvent
import presentation.ui.main.payment_method.view_model.PaymentMethodState
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.apple
import com.devper.app.design.resources.cash
import com.devper.app.design.resources.google
import com.devper.app.design.resources.paypal
import com.devper.app.design.resources.wallet


@OptIn(ExperimentalResourceApi::class)
@Composable
fun PaymentMethodScreen(
    state: PaymentMethodState,
    events: (PaymentMethodEvent) -> Unit,
    popup: () -> Unit
) {
    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(PaymentMethodEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(PaymentMethodEvent.OnRetryNetwork) },
        titleToolbar = "Payment Method",
        startIconToolbar = Icons.AutoMirrored.Filled.ArrowBack,
        onClickStartIconToolbar = popup
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            Spacer_32dp()

            Text("Cash", style = MaterialTheme.typography.titleMedium)
            Spacer_8dp()
            ChipsCardBox(
                text = "Cash",
                image = Res.drawable.cash,
                isSelected = state.selectedPaymentMethod == 0,
                onSelectExecute = { events(PaymentMethodEvent.OnUpdateSelectedPaymentMethod(0)) })

            Spacer_16dp()

            Text("Wallet", style = MaterialTheme.typography.titleMedium)
            Spacer_8dp()
            ChipsCardBox(
                text = "Wallet",
                image = Res.drawable.wallet,
                isSelected = state.selectedPaymentMethod == 1,
                onSelectExecute = { events(PaymentMethodEvent.OnUpdateSelectedPaymentMethod(1)) })



            Spacer_16dp()

            Text("More Payment Options", style = MaterialTheme.typography.titleMedium)
            Spacer_8dp()


            Card(
                modifier = Modifier,
                border = BorderStroke(1.dp, BorderColor),
                shape = MaterialTheme.shapes.small
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth().noRippleClickable {
                            events(PaymentMethodEvent.OnUpdateSelectedPaymentMethod(2))
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.paypal),
                                null,
                                modifier = Modifier.size(24.dp)
                            )
                            Text("Paypal", style = MaterialTheme.typography.bodyLarge)
                        }

                        Switch(checked = state.selectedPaymentMethod == 2, onCheckedChange = {
                            events(PaymentMethodEvent.OnUpdateSelectedPaymentMethod(2))
                        })

                    }
                    HorizontalDivider(color = BorderColor)
                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth().noRippleClickable {
                            events(PaymentMethodEvent.OnUpdateSelectedPaymentMethod(3))
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.apple), null,
                                modifier = Modifier.size(24.dp)
                            )
                            Text("Apple Pay", style = MaterialTheme.typography.bodyLarge)
                        }

                        Switch(checked = state.selectedPaymentMethod == 3, onCheckedChange = {
                            events(PaymentMethodEvent.OnUpdateSelectedPaymentMethod(3))
                        })

                    }
                    HorizontalDivider(color = BorderColor)
                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth().noRippleClickable {
                            events(PaymentMethodEvent.OnUpdateSelectedPaymentMethod(4))
                        },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.google), null,
                                modifier = Modifier.size(24.dp)
                            )
                            Text("Google Pay", style = MaterialTheme.typography.bodyLarge)
                        }

                        Switch(checked = state.selectedPaymentMethod == 4, onCheckedChange = {
                            events(PaymentMethodEvent.OnUpdateSelectedPaymentMethod(4))
                        })

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ChipsCardBox(
    text: String,
    image: DrawableResource,
    isSelected: Boolean,
    onSelectExecute: () -> Unit
) {

    Card(
        onClick = onSelectExecute,
        modifier = Modifier,
        border = BorderStroke(1.dp, BorderColor),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(image), null,
                    modifier = Modifier.size(24.dp)
                )
                Text(text, style = MaterialTheme.typography.bodyLarge)
            }

            Switch(checked = isSelected, onCheckedChange = { onSelectExecute() })

        }
    }


}
