package presentation.ui.main.address.view_model


import business.domain.main.Address
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState

data class AddressState(
    val addresses: List<Address> = listOf(),
    val addAddressDialogState: UIComponentState = UIComponentState.Hide,

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val networkState: NetworkState = NetworkState.Good,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)
