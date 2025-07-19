package com.devper.app.feature.main.edit_profile.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState

sealed class EditProfileEvent {

    data class UpdateProfile(val imageBitmap: ImageBitmap?) : EditProfileEvent()

    data class OnUpdateImageOptionDialog(val value: UIComponentState) : EditProfileEvent()

    data class OnUpdatePermissionDialog(val value: UIComponentState) : EditProfileEvent()

    data class OnUpdateName(val value: String) : EditProfileEvent()

    data class OnUpdateAge(val value: String) : EditProfileEvent()

    data class Error(
        val uiComponent: UIComponent
    ) : EditProfileEvent()

    data object OnRetryNetwork : EditProfileEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState
    ) : EditProfileEvent()

    data object OnRemoveHeadFromQueue : EditProfileEvent()

}
