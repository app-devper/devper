package presentation.ui.main.checkout.view_model

import business.domain.main.Address
import business.domain.main.ShippingType
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState

sealed class CheckoutEvent {

    data class OnUpdateSelectedShipping(val value: ShippingType) : CheckoutEvent()

    data class OnUpdateSelectShippingDialogState(val value: UIComponentState) : CheckoutEvent()

    data class OnUpdateSelectedAddress(val value: Address) : CheckoutEvent()

    data object BuyProduct : CheckoutEvent()

    data object OnRemoveHeadFromQueue : CheckoutEvent()

    data class Error(
        val uiComponent: UIComponent
    ) : CheckoutEvent()

    data object OnRetryNetwork : CheckoutEvent()
    data class OnUpdateNetworkState(
        val networkState: NetworkState
    ) : CheckoutEvent()
}
