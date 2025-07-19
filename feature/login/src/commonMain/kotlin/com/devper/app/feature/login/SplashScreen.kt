package com.devper.app.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.discover
import com.devper.app.design.resources.enjoy
import com.devper.app.design.resources.getting_start
import com.devper.app.design.resources.live_your_perfect
import com.devper.app.design.resources.shop
import com.devper.app.design.resources.splash_title
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import com.devper.app.core.design.component.MultiStateAnimationCircleFilledCanvas
import com.devper.app.core.design.component.Spacer_32dp
import com.devper.app.core.design.theme.grey_700
import com.devper.app.core.design.theme.splashBackground
import com.devper.app.feature.login.viewmodel.LoginEvent
import com.devper.app.feature.login.viewmodel.LoginState

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun SplashScreen(
    state: LoginState,
    events: (LoginEvent) -> Unit,
    navigateToMain: () -> Unit,
    navigateToLogin: () -> Unit,
) {

    LaunchedEffect(state.navigateToMain) {
        delay(3000L)
        if (state.navigateToMain) {
            navigateToMain()
        } else {
            navigateToLogin()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(splashBackground),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter),
            contentAlignment = Alignment.BottomCenter
        ) {
            MultiStateAnimationCircleFilledCanvas()
        }

        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer_32dp()
            Text(
                stringResource(Res.string.shop),
                style = MaterialTheme.typography.displayLarge,
                color = grey_700,
                fontWeight = FontWeight.Bold,
            )
            Spacer_32dp()
            Text(
                stringResource(Res.string.discover),
                style = MaterialTheme.typography.displayLarge,
                color = grey_700,
                fontWeight = FontWeight.Bold,
            )
            Spacer_32dp()
            Text(
                stringResource(Res.string.enjoy),
                style = MaterialTheme.typography.displayLarge,
                color = grey_700,
                fontWeight = FontWeight.Bold,
            )
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(Res.string.live_your_perfect),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center
            )
            Spacer_32dp()

            Text(
                stringResource(Res.string.splash_title),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 32.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary,
            )
            Spacer_32dp()

            Text(
                stringResource(Res.string.getting_start),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}