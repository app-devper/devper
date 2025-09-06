package com.devper.app.feature.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devper.app.core.design.component.CircleImage
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.component.Spacer12dp
import com.devper.app.core.design.component.Spacer16dp
import com.devper.app.core.design.component.Spacer32dp
import com.devper.app.core.design.component.Spacer8dp
import com.devper.app.core.design.component.noRippleClickable
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.arrow_right
import com.devper.app.design.resources.coupon
import com.devper.app.design.resources.location2
import com.devper.app.design.resources.order
import com.devper.app.design.resources.payment
import com.devper.app.design.resources.profile2
import com.devper.app.design.resources.setting2
import com.devper.app.design.resources.warning
import com.devper.app.feature.main.profile.viewmodel.ProfileEvent
import com.devper.app.feature.main.profile.viewmodel.ProfileState
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileScreen(
    state: ProfileState,
    events: (ProfileEvent) -> Unit,
    navigateToAddress: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToPaymentMethod: () -> Unit,
    navigateToMyOrders: () -> Unit,
    navigateToMyCoupons: () -> Unit,
    navigateToMyWallet: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(ProfileEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(ProfileEvent.OnRetryNetwork) },
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer16dp()

            Text("Profile", style = MaterialTheme.typography.titleLarge)

            Spacer16dp()

            CircleImage(
                image = state.profile.profileUrl,
                modifier = Modifier.size(120.dp),
            )

            Spacer16dp()

            Text(state.profile.name, style = MaterialTheme.typography.headlineMedium)

            Spacer32dp()

            Column(modifier = Modifier.fillMaxWidth()) {
                ProfileItemBox(title = "Edit Profile", image = Res.drawable.profile2) {
                    navigateToEditProfile()
                }
                ProfileItemBox(
                    title = "Manage Address",
                    image = Res.drawable.location2,
                ) {
                    navigateToAddress()
                }
                ProfileItemBox(
                    title = "Payment Methods",
                    image = Res.drawable.payment,
                ) {
                    navigateToPaymentMethod()
                }
                ProfileItemBox(
                    title = "My Orders",
                    image = Res.drawable.order,
                ) {
                    navigateToMyOrders()
                }
                ProfileItemBox(
                    title = "My Coupons",
                    image = Res.drawable.coupon,
                ) {
                    navigateToMyCoupons()
                }
                ProfileItemBox(
                    title = "Settings",
                    image = Res.drawable.setting2,
                ) {
                    navigateToSettings()
                }
                ProfileItemBox(
                    title = "Help Center",
                    image = Res.drawable.warning,
                    isLastItem = true,
                ) {}
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ProfileItemBox(
    title: String,
    image: DrawableResource,
    isLastItem: Boolean = false,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .noRippleClickable { onClick() },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painterResource(image),
                    null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(35.dp),
                )
                Spacer8dp()
                Text(title, style = MaterialTheme.typography.bodyLarge)
            }

            Icon(
                painterResource(Res.drawable.arrow_right),
                null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = .7f),
                modifier = Modifier.size(30.dp),
            )
        }

        if (!isLastItem) {
            Spacer12dp()
            HorizontalDivider()
            Spacer12dp()
        }
    }
}
