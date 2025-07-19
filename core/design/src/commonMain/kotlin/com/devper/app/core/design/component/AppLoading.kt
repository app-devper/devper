package com.devper.app.core.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.devper.app.design.resources.Res
import com.devper.app.design.resources.loading
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProgressLoading(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag(stringResource(Res.string.loading)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}