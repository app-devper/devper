package presentation.ui.main.cart.view_model

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent

sealed class CartEvent {

    data class DeleteFromBasket(val id: Int) : CartEvent()

    data class AddProduct(val id: Int) : CartEvent()


    data object OnRemoveHeadFromQueue : CartEvent()

    data class Error(
        val uiComponent: UIComponent
    ) : CartEvent()

    data object OnRetryNetwork : CartEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState
    ) : CartEvent()
}
