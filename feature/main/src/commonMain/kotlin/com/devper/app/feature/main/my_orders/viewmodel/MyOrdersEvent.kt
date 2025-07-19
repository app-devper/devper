package presentation.ui.main.my_orders.view_model

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent

sealed class MyOrdersEvent {

    data object OnRemoveHeadFromQueue : MyOrdersEvent()

    data class Error(
        val uiComponent: UIComponent
    ) : MyOrdersEvent()

    data object OnRetryNetwork : MyOrdersEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState
    ) : MyOrdersEvent()
}
