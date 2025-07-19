package com.devper.app.core.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.devper.app.core.design.component.WindowSize

private val darkColorPalette = darkColorScheme(
    primary = PrimaryColor,
    primaryContainer = PrimaryVariantColor,
    secondary = AccentColor,
)

private val lightColorPalette = lightColorScheme(
    primary = PrimaryColor,
    primaryContainer = PrimaryVariantColor,
    secondary = AccentColor,
    background = Color.White,
    surfaceVariant = Color.White,
    surface = lightSurface,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorPalette
    } else {
        lightColorPalette
    }

    val windowSize = WindowSize.COMPACT

    CompositionLocalProvider(
        LocalWindowSize provides windowSize
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = LatoTypography(),
            shapes = Shapes,
            content = content
        )
    }

}

val LocalWindowSize = compositionLocalOf { WindowSize.COMPACT }

