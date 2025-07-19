package presentation.ui.main.settings.view_model

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent

sealed class SettingsEvent {

    data object Logout : SettingsEvent()

    data object OnRemoveHeadFromQueue : SettingsEvent()

    data class Error(
        val uiComponent: UIComponent
    ) : SettingsEvent()

    data object OnRetryNetwork : SettingsEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState
    ) : SettingsEvent()
}
