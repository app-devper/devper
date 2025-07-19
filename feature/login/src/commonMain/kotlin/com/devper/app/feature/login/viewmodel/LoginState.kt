package com.devper.app.feature.login.viewmodel

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent

data class LoginState(
    val nameRegister: String = "",
    val usernameLogin: String = "",
    val passwordLogin: String = "",

    val isTokenValid: Boolean = false,
    val navigateToMain: Boolean = false,

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val networkState: NetworkState = NetworkState.Good,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)
