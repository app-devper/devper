package com.devper.app.feature.main.editprofile.viewmodel

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState

data class EditProfileState(
    val name: String = "",
    val age: String = "",
    val email: String = "",
    val image: String = "",
    val imageOptionDialog: UIComponentState = UIComponentState.Hide,
    val permissionDialog: UIComponentState = UIComponentState.Hide,
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val networkState: NetworkState = NetworkState.Good,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)
