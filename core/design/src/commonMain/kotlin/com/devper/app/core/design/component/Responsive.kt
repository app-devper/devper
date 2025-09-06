package com.devper.app.core.design.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devper.app.core.design.theme.LocalWindowSize
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class WindowSize {
    COMPACT,
    MEDIUM,
    EXPANDED,
    ;

    // Factory method that creates an instance of the class based on window width
    companion object {
        fun basedOnWidth(windowWidth: Dp): WindowSize =
            when {
                windowWidth < 600.dp -> COMPACT
                windowWidth < 840.dp -> MEDIUM
                else -> EXPANDED
            }
    }
}

@Composable
fun Responsive(
    mobile: @Composable () -> Unit,
    desktop: @Composable () -> Unit,
) {
    val isCompact = LocalWindowSize.current == WindowSize.COMPACT

    if (isCompact) {
        desktop()
    } else {
        mobile()
    }
}

@Preview
@Composable
fun PreviewResponsive() {
    Responsive(
        mobile = {
            // Your mobile content goes here
        },
        desktop = {
            // Your desktop content goes here
        },
    )
}
