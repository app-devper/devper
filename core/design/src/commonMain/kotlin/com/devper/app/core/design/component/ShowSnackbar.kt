package com.devper.app.core.design.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ShowSnackbar(
    modifier: Modifier,
    snackbarVisibleState: Boolean,
    title: String,
    onDismiss: () -> Unit
) {

    if (snackbarVisibleState) {
        Snackbar(
            modifier = modifier.padding(16.dp),
            action = {
                Button(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(text = "OK")
                }
            }
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}