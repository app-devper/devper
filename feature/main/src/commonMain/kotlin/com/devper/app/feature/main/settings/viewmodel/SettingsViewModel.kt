package com.devper.app.feature.main.settings.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.domain.constants.CUSTOM_TAG
import com.devper.app.core.domain.usecases.LogoutUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    val logoutUseCase: LogoutUseCase,
) : ViewModel() {
    val state: MutableState<SettingsState> = mutableStateOf(SettingsState())

    fun onTriggerEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.Logout -> {
                logout()
            }

            is SettingsEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is SettingsEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is SettingsEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is SettingsEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            state.value = state.value.copy(progressBarState = ProgressBarState.ScreenLoading)

            val result = logoutUseCase(Unit)
            result.fold(
                onSuccess = {
                    state.value = state.value.copy(progressBarState = ProgressBarState.Idle)
                    state.value = state.value.copy(logout = true)
                },
                onFailure = { error ->
                    state.value = state.value.copy(progressBarState = ProgressBarState.Idle)
                    appendToMessageQueue(
                        UIComponent.Dialog(
                            title = "Logout Failed",
                            description = error.message ?: "An error occurred while logging out.",
                        ),
                    )
                },
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
    }

    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }
}
