package presentation.ui.main.notifications.view_model

import business.domain.main.Notification
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent

data class NotificationsState(
    val notifications: List<Notification> = listOf(),

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val networkState: NetworkState = NetworkState.Good,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)
