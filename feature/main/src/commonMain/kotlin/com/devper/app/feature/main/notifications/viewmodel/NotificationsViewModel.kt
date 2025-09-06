package com.devper.app.feature.main.notifications.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import business.domain.main.Notification
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.domain.constants.CUSTOM_TAG
import com.devper.app.core.domain.constants.LOREM

class NotificationsViewModel : ViewModel() {
    val state: MutableState<NotificationsState> = mutableStateOf(NotificationsState())

    fun onTriggerEvent(event: NotificationsEvent) {
        when (event) {
            is NotificationsEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is NotificationsEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is NotificationsEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is NotificationsEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    init {
        state.value =
            state.value.copy(
                notifications =
                    listOf(
                        Notification(title = "Order Shipped", desc = LOREM, createAt = "1h", isRead = false),
                        Notification(title = "Flash Sale Alert", desc = LOREM, createAt = "3h", isRead = true),
                        Notification(title = "Product Review Request", desc = LOREM, createAt = "5h", isRead = true),
                        Notification(title = "New Paypal Added", desc = LOREM, createAt = "5h", isRead = true),
                    ),
            )
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
