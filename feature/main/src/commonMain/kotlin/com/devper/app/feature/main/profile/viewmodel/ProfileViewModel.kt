package com.devper.app.feature.main.profile.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.devper.app.core.domain.constants.CUSTOM_TAG
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.domain.usecases.GetUserInfoUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    val state: MutableState<ProfileState> = mutableStateOf(ProfileState())

    fun onTriggerEvent(event: ProfileEvent) {
        when (event) {

            is ProfileEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is ProfileEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is ProfileEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is ProfileEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            state.value = state.value.copy(progressBarState = ProgressBarState.ScreenLoading)
            val result = getUserInfoUseCase(Unit)
            result.fold(
                onSuccess = { userInfo ->
                    state.value = state.value.copy(progressBarState = ProgressBarState.Idle)
                    state.value = state.value.copy(
                        profile = ProfileView(
                            name = userInfo.firstName,
                            profileUrl = "https://www.w3schools.com/w3images/avatar2.png"
                        )
                    )
                },
                onFailure = { error ->
                    state.value = state.value.copy(progressBarState = ProgressBarState.Idle)
                }
            )
        }
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        if (uiComponent is UIComponent.None) {
            println("${CUSTOM_TAG}: onTriggerEvent:  ${uiComponent.message}")
            return
        }

        val queue = state.value.errorQueue
        queue.add(uiComponent)
        state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
        state.value = state.value.copy(errorQueue = queue)
    }

    private fun removeHeadMessage() {
        try {
            val queue = state.value.errorQueue
            queue.remove() // can throw exception if empty
            state.value = state.value.copy(errorQueue = Queue(mutableListOf())) // force recompose
            state.value = state.value.copy(errorQueue = queue)
        } catch (e: Exception) {
            println("${CUSTOM_TAG}: removeHeadMessage: Nothing to remove from DialogQueue")
        }
    }

    private fun onRetryNetwork() {
        getProfile()
    }

    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }

}