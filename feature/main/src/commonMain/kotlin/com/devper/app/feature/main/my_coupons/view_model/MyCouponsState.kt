package presentation.ui.main.my_coupons.view_model

import business.domain.main.Coupons
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent

data class MyCouponsState(
    val couponsList:List<Coupons> = listOf(),

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val networkState: NetworkState = NetworkState.Good,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)
