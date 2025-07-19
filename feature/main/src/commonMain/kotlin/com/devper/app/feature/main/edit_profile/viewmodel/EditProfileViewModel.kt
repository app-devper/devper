package com.devper.app.feature.main.edit_profile.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import com.devper.app.core.domain.constants.CUSTOM_TAG

import androidx.lifecycle.ViewModel
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState

class EditProfileViewModel(

) : ViewModel() {


    val state: MutableState<EditProfileState> = mutableStateOf(EditProfileState())


    fun onTriggerEvent(event: EditProfileEvent) {
        when (event) {

            is EditProfileEvent.OnUpdateImageOptionDialog -> {
                onUpdateImageOptionDialog(event.value)
            }

            is EditProfileEvent.UpdateProfile -> {
                updateProfile(event.imageBitmap)
            }

            is EditProfileEvent.OnUpdatePermissionDialog -> {
                onUpdatePermissionDialog(event.value)
            }

            is EditProfileEvent.OnUpdateName -> {
                onUpdateName(event.value)
            }

            is EditProfileEvent.OnUpdateAge -> {
                onUpdateAge(event.value)
            }

            is EditProfileEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is EditProfileEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is EditProfileEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is EditProfileEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }


    init {
        getProfile()
        getEmail()
    }

    private fun updateProfile(imageBitmap: ImageBitmap?) {

    }

    private fun onUpdateImageOptionDialog(value: UIComponentState) {
        state.value = state.value.copy(imageOptionDialog = value)
    }

    private fun onUpdatePermissionDialog(value: UIComponentState) {
        state.value = state.value.copy(permissionDialog = value)
    }

    private fun onUpdateName(value: String) {
        state.value = state.value.copy(name = value)
    }

    private fun onUpdateAge(value: String) {
        state.value = state.value.copy(age = value)
    }

    private fun getProfile() {

    }


    private fun getEmail() {

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