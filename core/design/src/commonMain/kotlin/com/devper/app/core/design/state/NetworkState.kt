package com.devper.app.core.design.state

sealed class NetworkState {
    data object Good : NetworkState()

    data object Failed : NetworkState()
}
