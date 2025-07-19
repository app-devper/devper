package com.devper.app.core.design.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties

@Composable
fun CreateUIComponentDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit,
) {
    val shouldDismiss = remember {
        mutableStateOf(false)
    }

    if (shouldDismiss.value) return

    GenericDialog(
        title = title,
        description = description,
        onDismiss = {
            shouldDismiss.value = true
            onDismiss()
        },
    )

}

@Composable
fun GenericDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = {
            onDismiss()
        },
        modifier = modifier,
        confirmButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(text = "OK")
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = description)
        }
    )
}




