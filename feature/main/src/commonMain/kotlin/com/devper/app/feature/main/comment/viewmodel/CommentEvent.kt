package com.devper.app.feature.main.comment.viewmodel

import com.devper.app.core.design.state.NetworkState
import com.devper.app.core.design.state.UIComponent
import com.devper.app.core.design.state.UIComponentState

sealed class CommentEvent {
    data class OnUpdateAddCommentDialogState(
        val value: UIComponentState,
    ) : CommentEvent()

    data object GetComments : CommentEvent()

    data class OnUpdateProductId(
        val id: Int,
    ) : CommentEvent()

    data class AddComment(
        val rate: Double,
        val comment: String,
    ) : CommentEvent()

    data object OnRemoveHeadFromQueue : CommentEvent()

    data class Error(
        val uiComponent: UIComponent,
    ) : CommentEvent()

    data object OnRetryNetwork : CommentEvent()

    data class OnUpdateNetworkState(
        val networkState: NetworkState,
    ) : CommentEvent()
}
