package com.devper.app.feature.main.address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import business.domain.main.Address
import com.devper.app.core.design.state.UIComponentState
import com.devper.app.core.design.component.AddAddressDialog
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.component.Spacer_12dp
import com.devper.app.core.design.component.Spacer_4dp
import com.devper.app.core.design.component.Spacer_8dp
import com.devper.app.core.design.component.TextWithIcon
import com.devper.app.core.design.theme.BorderColor
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.address
import com.devper.app.design.resources.delete
import com.devper.app.design.resources.earth
import com.devper.app.design.resources.location2
import com.devper.app.design.resources.mail
import com.devper.app.design.resources.no_address
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.ui.main.address.view_model.AddressEvent
import presentation.ui.main.address.view_model.AddressState


@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddressScreen(state: AddressState, events: (AddressEvent) -> Unit, popup: () -> Unit) {

    if (state.addAddressDialogState == UIComponentState.Show) {
        AddAddressDialog(onDismissRequest = {
            events(AddressEvent.OnUpdateAddAddressDialogState(UIComponentState.Hide))
        },
            onExecute = { address, country, city, state, zipCode ->
                events(
                    AddressEvent.AddAddress(
                        country = country,
                        address = address,
                        city = city,
                        state = state,
                        zipCode = zipCode
                    )
                )
            })
    }

    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(AddressEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(AddressEvent.OnRetryNetwork) },
        titleToolbar = stringResource(Res.string.address),
        startIconToolbar = Icons.AutoMirrored.Filled.ArrowBack,
        onClickStartIconToolbar = popup,
        endIconToolbar = Icons.Filled.Add,
        onClickEndIconToolbar = { events(AddressEvent.OnUpdateAddAddressDialogState(UIComponentState.Show)) }) {

        Column(modifier = Modifier.fillMaxSize()) {

            if (state.addresses.isEmpty()) {
                Text(
                    stringResource(Res.string.no_address),
                    style = MaterialTheme.typography.titleLarge,
                    color = BorderColor,
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }

            LazyColumn {
                items(state.addresses, key = { it.id }) {
                    AddressBox(address = it, modifier = Modifier.fillMaxWidth())
                }
            }

        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun AddressBox(address: Address, modifier: Modifier) {
    Box(modifier = Modifier.padding(8.dp), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.height(160.dp).fillMaxWidth().padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    address.address,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(.9f)
                )
                Icon(
                    painterResource(Res.drawable.delete),
                    null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer_12dp()

            TextWithIcon(text = address.country, icon = Res.drawable.earth)
            Spacer_4dp()
            TextWithIcon(text = address.getFullAddress(), icon = Res.drawable.location2)
            Spacer_4dp()
            TextWithIcon(text = address.zipCode, icon = Res.drawable.mail)

            Spacer_8dp()
            HorizontalDivider()
        }
    }
}

