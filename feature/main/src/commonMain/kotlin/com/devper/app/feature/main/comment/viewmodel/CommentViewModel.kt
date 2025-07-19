package presentation.ui.main.comment.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.devper.app.core.domain.constants.CUSTOM_TAG
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState

class CommentViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    val state: MutableState<CommentState> = mutableStateOf(CommentState())


    fun onTriggerEvent(event: CommentEvent) {
        when (event) {

            is CommentEvent.AddComment -> {
                addComment(comment = event.comment, rate = event.rate)
            }

            is CommentEvent.OnUpdateAddCommentDialogState -> {
                onUpdateAddCommentDialogState(event.value)
            }

            is CommentEvent.GetComments -> {
                getComments()
            }

            is CommentEvent.OnUpdateProductId -> {
                onUpdateProductId(event.id)
            }

            is CommentEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is CommentEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is CommentEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is CommentEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }


    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            onTriggerEvent(CommentEvent.OnUpdateProductId(id))
            onTriggerEvent(CommentEvent.GetComments)
        }
    }


    private fun onUpdateProductId(id: Int) {
        state.value = state.value.copy(productId = id)
    }


    private fun addComment(comment: String, rate: Double) {

    }

    private fun getComments() {

    }

    private fun onUpdateAddCommentDialogState(value: UIComponentState) {
        state.value = state.value.copy(addCommentDialogState = value)
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
        onTriggerEvent(CommentEvent.GetComments)
    }


    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }


}