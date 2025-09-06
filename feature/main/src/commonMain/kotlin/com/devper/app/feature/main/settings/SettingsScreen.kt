package presentation.ui.main.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.component.Spacer32dp
import com.devper.app.core.design.component.Spacer8dp
import com.devper.app.core.design.component.noRippleClickable
import com.devper.app.core.design.theme.BorderColor
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.arrow_right
import com.devper.app.design.resources.exit
import com.devper.app.feature.main.settings.viewmodel.SettingsEvent
import com.devper.app.feature.main.settings.viewmodel.SettingsState
import org.jetbrains.compose.resources.painterResource

@Composable
fun SettingsScreen(
    state: SettingsState,
    events: (SettingsEvent) -> Unit,
    popup: () -> Unit,
    logout: () -> Unit,
) {
    LaunchedEffect(key1 = state.logout) {
        if (state.logout) {
            logout()
        }
    }

    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(SettingsEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(SettingsEvent.OnRetryNetwork) },
        titleToolbar = "Setting",
        startIconToolbar = Icons.AutoMirrored.Filled.ArrowBack,
        onClickStartIconToolbar = popup,
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer32dp()

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .noRippleClickable {
                            events(SettingsEvent.Logout)
                        },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(Res.drawable.exit),
                    null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp),
                )

                Spacer8dp()

                Text(
                    "Logout",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(.9f),
                )

                Icon(
                    painter = painterResource(Res.drawable.arrow_right),
                    null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp),
                )
            }

            HorizontalDivider(color = BorderColor)
        }
    }
}
