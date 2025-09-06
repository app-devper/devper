package com.devper.app.feature.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.devper.app.core.design.component.DEFAULT__BUTTON_SIZE_EXTRA
import com.devper.app.core.design.component.DefaultButton
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.component.PasswordTextField
import com.devper.app.core.design.component.SimpleImageButton
import com.devper.app.core.design.component.Spacer16dp
import com.devper.app.core.design.component.Spacer32dp
import com.devper.app.core.design.component.Spacer4dp
import com.devper.app.core.design.component.Spacer8dp
import com.devper.app.core.design.theme.DefaultCheckBoxTheme
import com.devper.app.core.design.theme.DefaultTextFieldTheme
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.agree_with
import com.devper.app.design.resources.already_have_account
import com.devper.app.design.resources.apple
import com.devper.app.design.resources.create_account
import com.devper.app.design.resources.email
import com.devper.app.design.resources.enter_valid_email
import com.devper.app.design.resources.enter_valid_password
import com.devper.app.design.resources.facebook
import com.devper.app.design.resources.google
import com.devper.app.design.resources.name
import com.devper.app.design.resources.or_sign_up_with
import com.devper.app.design.resources.password
import com.devper.app.design.resources.register_title
import com.devper.app.design.resources.sign_in
import com.devper.app.design.resources.sign_up
import com.devper.app.design.resources.terms_condition
import com.devper.app.feature.login.viewmodel.LoginEvent
import com.devper.app.feature.login.viewmodel.LoginState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RegisterScreen(
    state: LoginState,
    events: (LoginEvent) -> Unit,
    navigateToMain: () -> Unit,
    popUp: () -> Unit,
) {
    LaunchedEffect(state.navigateToMain) {
        if (state.navigateToMain) {
            navigateToMain()
        }
    }

    var isUsernameError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(false) }

    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(LoginEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                stringResource(Res.string.create_account),
                style = MaterialTheme.typography.displaySmall,
            )
            Spacer16dp()
            Text(
                stringResource(Res.string.register_title),
                style = MaterialTheme.typography.labelMedium,
            )
            Spacer32dp()

            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(stringResource(Res.string.name))
                Spacer4dp()
                TextField(
                    value = state.nameRegister,
                    onValueChange = {
                        events(LoginEvent.OnUpdateNameRegister(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = DefaultTextFieldTheme(),
                    shape = MaterialTheme.shapes.small,
                    singleLine = true,
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                        ),
                )
                Spacer8dp()

                Text(stringResource(Res.string.email))
                Spacer4dp()
                TextField(
                    isError = isUsernameError,
                    value = state.usernameLogin,
                    onValueChange = {
                        if (it.length < 32) {
                            events(LoginEvent.OnUpdateUsernameLogin(it))
                            isUsernameError = it.isEmpty()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = DefaultTextFieldTheme(),
                    shape = MaterialTheme.shapes.small,
                    singleLine = true,
                    keyboardOptions =
                        KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Email,
                        ),
                )
                AnimatedVisibility(visible = isUsernameError) {
                    Text(
                        stringResource(Res.string.enter_valid_email),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
                Spacer8dp()

                Text(stringResource(Res.string.password))
                Spacer4dp()
                PasswordTextField(
                    value = state.passwordLogin,
                    onValueChange = {
                        events(LoginEvent.OnUpdatePasswordLogin(it))
                        isPasswordError = it.length < 8
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                AnimatedVisibility(visible = isPasswordError) {
                    Text(
                        stringResource(Res.string.enter_valid_password),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }

            Spacer8dp()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = DefaultCheckBoxTheme(),
                )
                Text(
                    text =
                        buildAnnotatedString {
                            append(stringResource(Res.string.agree_with))
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                append(stringResource(Res.string.terms_condition))
                            }
                            append(".")
                        },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer32dp()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DefaultButton(
                    progressBarState = state.progressBarState,
                    text = stringResource(Res.string.sign_up),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(DEFAULT__BUTTON_SIZE_EXTRA),
                    onClick = { events(LoginEvent.Register) },
                )

                Spacer(Modifier.height(32.dp))
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    HorizontalDivider(modifier = Modifier.width(75.dp))
                    Text(text = stringResource(Res.string.or_sign_up_with))
                    HorizontalDivider(modifier = Modifier.width(75.dp))
                }
                Spacer32dp()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    SimpleImageButton(Res.drawable.facebook)
                    SimpleImageButton(Res.drawable.apple)
                    SimpleImageButton(Res.drawable.google)
                }
            }

            Spacer32dp()

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(Res.string.already_have_account),
                )
                Spacer4dp()
                Text(
                    modifier =
                        Modifier.clickable {
                            popUp()
                        },
                    text = stringResource(Res.string.sign_in),
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                )
            }
        }
    }
}
