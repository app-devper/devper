package presentation.ui.main.comment.view_model

import com.devper.app.core.design.state.UIComponentState

import business.domain.main.Comment
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.ProgressBarState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent

data class CommentState(
    val productId: Int = 0,
    val comments: List<Comment> = listOf(),
    val addCommentDialogState: UIComponentState = UIComponentState.Hide,

    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val networkState: NetworkState = NetworkState.Good,
    val errorQueue: Queue<UIComponent> = Queue(mutableListOf()),
)
