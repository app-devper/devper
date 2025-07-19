package presentation.ui.main.categories.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.devper.app.core.domain.constants.CUSTOM_TAG
import androidx.lifecycle.ViewModel
import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.Queue
import com.devper.app.core.design.state.UIComponent

class CategoriesViewModel(

) : ViewModel() {

    val state: MutableState<CategoriesState> = mutableStateOf(CategoriesState())


    fun onTriggerEvent(event: CategoriesEvent) {
        when (event) {

            is CategoriesEvent.OnRemoveHeadFromQueue -> {
                removeHeadMessage()
            }

            is CategoriesEvent.Error -> {
                appendToMessageQueue(event.uiComponent)
            }

            is CategoriesEvent.OnRetryNetwork -> {
                onRetryNetwork()
            }

            is CategoriesEvent.OnUpdateNetworkState -> {
                onUpdateNetworkState(event.networkState)
            }
        }
    }

    init {
        getCategories()
    }


    private fun getCategories() {

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
        getCategories()
    }


    private fun onUpdateNetworkState(networkState: NetworkState) {
        state.value = state.value.copy(networkState = networkState)
    }


}