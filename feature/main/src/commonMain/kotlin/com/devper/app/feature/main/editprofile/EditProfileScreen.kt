package com.devper.app.feature.main.editprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.devper.app.core.design.component.CircleImage
import com.devper.app.core.design.component.DEFAULT__BUTTON_SIZE
import com.devper.app.core.design.component.DefaultButton
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.component.GeneralAlertDialog
import com.devper.app.core.design.component.ImageOptionDialog
import com.devper.app.core.design.component.Spacer16dp
import com.devper.app.core.design.component.Spacer32dp
import com.devper.app.core.design.component.Spacer8dp
import com.devper.app.core.design.state.UIComponentState
import com.devper.app.core.design.theme.DefaultTextFieldTheme
import com.devper.app.feature.main.editprofile.viewmodel.EditProfileEvent
import com.devper.app.feature.main.editprofile.viewmodel.EditProfileState

@Composable
fun EditProfileScreen(
    state: EditProfileState,
    events: (EditProfileEvent) -> Unit,
    popup: () -> Unit,
) {
    val imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchGallery by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }

    if (state.imageOptionDialog == UIComponentState.Show) {
        ImageOptionDialog(onDismissRequest = {
            events(EditProfileEvent.OnUpdateImageOptionDialog(UIComponentState.Hide))
        }, onGalleryRequest = {
            launchGallery = true
        }, onCameraRequest = {
            launchCamera = true
        })
    }

    if (state.permissionDialog == UIComponentState.Show) {
        GeneralAlertDialog(
            title = "Permission Required",
            message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
            positiveButtonText = "Settings",
            negativeButtonText = "Cancel",
            onDismissRequest = {
                events(EditProfileEvent.OnUpdatePermissionDialog(UIComponentState.Hide))
            },
            onPositiveClick = {
                launchSetting = true
            },
            onNegativeClick = {
            },
        )
    }

    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(EditProfileEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(EditProfileEvent.OnRetryNetwork) },
        titleToolbar = "Edit Profile",
        startIconToolbar = Icons.AutoMirrored.Filled.ArrowBack,
        onClickStartIconToolbar = popup,
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer32dp()

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (imageBitmap == null) {
                    CircleImage(state.image, modifier = Modifier.size(120.dp))
                } else {
                    CircleImage(imageBitmap, modifier = Modifier.size(120.dp))
                }
                Spacer8dp()
                DefaultButton(text = "Select") {
                    events(EditProfileEvent.OnUpdateImageOptionDialog(UIComponentState.Show))
                }
            }

            Spacer32dp()

            TextField(
                value = state.name,
                onValueChange = {
                    if (it.length < 32) {
                        events(EditProfileEvent.OnUpdateName(it))
                    }
                },
                label = {
                    Text("Name")
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

            Spacer16dp()
            TextField(
                value = state.age,
                onValueChange = {
                    if (it.length < 3) {
                        events(EditProfileEvent.OnUpdateAge(it))
                    }
                },
                label = {
                    Text("Age")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = DefaultTextFieldTheme(),
                shape = MaterialTheme.shapes.small,
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    ),
            )

            Spacer16dp()

            TextField(
                value = state.email,
                onValueChange = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                colors = DefaultTextFieldTheme(),
                shape = MaterialTheme.shapes.small,
                label = {
                    Text("Email")
                },
                singleLine = true,
                keyboardOptions =
                    KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                    ),
            )

            Spacer32dp()

            DefaultButton(
                modifier = Modifier.fillMaxWidth().height(DEFAULT__BUTTON_SIZE),
                progressBarState = state.progressBarState,
                text = "Submit",
            ) {
                events(EditProfileEvent.UpdateProfile(imageBitmap))
            }
        }
    }
}
