package com.devper.app.core.design.state

sealed class ProgressBarState {

    data object ButtonLoading : ProgressBarState()

    data object ScreenLoading : ProgressBarState()

    data object FullScreenLoading : ProgressBarState()

    data object LoadingWithLogo : ProgressBarState()

    data object Idle : ProgressBarState()

}

