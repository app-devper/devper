package com.devper.app.feature.main.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import business.domain.main.Notification
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.component.Spacer_8dp
import com.devper.app.core.design.theme.BorderColor
import com.devper.app.core.design.theme.grey_050
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.bell
import org.jetbrains.compose.resources.painterResource
import presentation.ui.main.notifications.view_model.NotificationsEvent
import presentation.ui.main.notifications.view_model.NotificationsState

@Composable
fun NotificationsScreen(
    state: NotificationsState,
    events: (NotificationsEvent) -> Unit,
    popup: () -> Unit
) {

    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(NotificationsEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(NotificationsEvent.OnRetryNetwork) },
        titleToolbar = "Notifications",
        startIconToolbar = Icons.AutoMirrored.Filled.ArrowBack,
        onClickStartIconToolbar = popup
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Text(
                "Mark all as read",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(16.dp),
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notifications) {
                    NotificationBox(notification = it)
                }
            }

        }
    }
}

@Composable
fun NotificationBox(notification: Notification) {

    Column(
        modifier = Modifier.fillMaxWidth()
            .background(if (notification.isRead) MaterialTheme.colorScheme.background else grey_050)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Box(
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.primary.copy(.2f),
                    CircleShape
                ).size(75.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(Res.drawable.bell),
                    null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(35.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(.9f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(notification.title, style = MaterialTheme.typography.titleLarge)
                Text(
                    notification.desc,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )

            }

            Text(notification.createAt, style = MaterialTheme.typography.labelMedium)
        }
        Spacer_8dp()
        HorizontalDivider(color = BorderColor)
    }

}
