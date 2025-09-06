package com.devper.app.feature.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.domain.error.ErrorMapper
import com.devper.app.core.domain.model.login.LoginParam
import com.devper.app.core.domain.usecases.KeepAliveUseCase
import com.devper.app.core.domain.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val keepAliveUseCase: KeepAliveUseCase,
) : ViewModel() {
    private var _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> =
        _uiState
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = LoginState(),
            )

    fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                login()
            }

            is LoginEvent.Register -> {
                register()
            }

            is LoginEvent.OnUpdateNameRegister -> {
                onUpdateNameRegister(event.value)
            }

            is LoginEvent.OnUpdatePasswordLogin -> {
                onUpdatePasswordLogin(event.value)
            }

            is LoginEvent.OnUpdateUsernameLogin -> {
                onUpdateUsernameLogin(event.value)
            }

            is LoginEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is LoginEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is LoginEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is LoginEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    init {
        checkToken()
    }

    private fun checkToken() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(progressBarState = ProgressBarState.FullScreenLoading)
            }
            val result = keepAliveUseCase(Unit)
            result.fold(
                onSuccess = { login ->
                    _uiState.update {
                        it.copy(
                            navigateToMain = true,
                            progressBarState = ProgressBarState.Idle,
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(progressBarState = ProgressBarState.Idle)
                    }
                },
            )
        }
    }

    private fun login() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(progressBarState = ProgressBarState.FullScreenLoading)
            }

            // Capture current state values safely within update block
            val currentState = _uiState.value
            val param =
                LoginParam(
                    username = currentState.usernameLogin,
                    password = currentState.passwordLogin,
                    system = "POS",
                )

            val result = loginUseCase(param)
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            navigateToMain = true,
                            progressBarState = ProgressBarState.Idle,
                        )
                    }
                },
                onFailure = { error ->
                    val appError = ErrorMapper.toAppError(error)
                    val errorDialog = UIComponent.Dialog("Error", appError.getDesc())
                    appendToMessageQueue(errorDialog)
                    _uiState.update {
                        it.copy(progressBarState = ProgressBarState.Idle)
                    }
                },
            )
        }
    }

    private fun register() {
    }

    private fun onUpdateNameRegister(value: String) {
        _uiState.update { it.copy(nameRegister = value) }
    }

    private fun onUpdatePasswordLogin(value: String) {
        _uiState.update { it.copy(passwordLogin = value) }
    }

    private fun onUpdateUsernameLogin(value: String) {
        _uiState.update { it.copy(usernameLogin = value) }
    }

    private fun appendToMessageQueue(uiComponent: UIComponent) {
        if (uiComponent is UIComponent.None) {
            return
        }

        _uiState.update { currentState ->
            val newQueueItems =
                currentState.errorQueue.items.toMutableList().apply {
                    add(uiComponent)
                }
            val newQueue = Queue(newQueueItems)
            currentState.copy(
                errorQueue = newQueue,
                progressBarState = ProgressBarState.Idle,
            )
        }
    }

    private fun removeHeadMessage() {
        _uiState.update { currentState ->
            try {
                val currentQueueItems = currentState.errorQueue.items.toMutableList()
                if (currentQueueItems.isNotEmpty()) {
                    currentQueueItems.removeFirst()
                    val newQueue = Queue(currentQueueItems)
                    currentState.copy(errorQueue = newQueue)
                } else {
                    currentState
                }
            } catch (e: Exception) {
                // Queue is empty, return current state unchanged
                currentState
            }
        }
    }

    private fun onRetryNetwork() {
    }

    private fun onUpdateNetworkState(networkState: NetworkState) {
        _uiState.update { it.copy(networkState = networkState) }
    }
}
