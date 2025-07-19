package presentation.ui.main.checkout.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.devper.app.core.domain.constants.CUSTOM_TAG
import business.domain.main.Address
import business.domain.main.ShippingType
import androidx.lifecycle.ViewModel
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState

val shippingType_global = listOf(
    ShippingType("Economy", 25, 7),
    ShippingType("Regular", 35, 6),
    ShippingType("Cargo", 45, 5),
    ShippingType("Express", 55, 4)
)

class CheckoutViewModel(

) : ViewModel() {


    val state: MutableState<CheckoutState> = mutableStateOf(CheckoutState())


    fun onTriggerEvent(event: CheckoutEvent) {
        when (event) {

            is CheckoutEvent.BuyProduct -> {
                buyProduct()
            }

            is CheckoutEvent.OnUpdateSelectedShipping -> {
                onUpdateSelectedShipping(event.value)
            }

            is CheckoutEvent.OnUpdateSelectShippingDialogState -> {
                onUpdateSelectShippingDialogState(event.value)
            }

            is CheckoutEvent.OnUpdateSelectedAddress -> {
                onUpdateSelectedAddress(event.value)
            }

            is CheckoutEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is CheckoutEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is CheckoutEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is CheckoutEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }


    init {
        getAddresses()
        getCart()
    }

    private fun buyProduct() {

    }

    private fun getCart() {

    }

    private fun getAddresses() {

    }

    private fun onUpdateSelectShippingDialogState(value: UIComponentState) {
        state.value = state.value.copy(selectShippingDialogState = value)

        state.value =
            state.value.copy(totalCost = state.value.totalBasket + state.value.selectedShipping.price)
    }

    private fun onUpdateSelectedShipping(value: ShippingType) {
        state.value = state.value.copy(selectedShipping = value)
    }

    private fun onUpdateSelectedAddress(value: Address) {
        state.value = state.value.copy(selectedAddress = value)
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
        getAddresses()
    }

    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }

}