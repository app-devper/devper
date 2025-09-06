package com.devper.app.feature.main.comment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.devper.app.core.design.component.AddCommentDialog
import com.devper.app.core.design.component.DefaultScreenUI
import com.devper.app.core.design.state.UIComponentState
import com.devper.app.core.design.theme.BorderColor
import com.devper.app.feature.main.comment.viewmodel.CommentEvent
import com.devper.app.feature.main.comment.viewmodel.CommentState
import com.devper.app.feature.main.detail.CommentBox

@Composable
fun CommentScreen(
    state: CommentState,
    events: (CommentEvent) -> Unit,
    popup: () -> Unit,
) {
    if (state.addCommentDialogState == UIComponentState.Show) {
        AddCommentDialog(
            onDismissRequest = {
                events(CommentEvent.OnUpdateAddCommentDialogState(UIComponentState.Hide))
            },
            onExecute = { rate, comment ->
                events(CommentEvent.AddComment(rate = rate, comment = comment))
            },
        )
    }

    DefaultScreenUI(
        queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(CommentEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState,
        networkState = state.networkState,
        onTryAgain = { events(CommentEvent.OnRetryNetwork) },
        titleToolbar = "Comments",
        startIconToolbar = Icons.AutoMirrored.Filled.ArrowBack,
        onClickStartIconToolbar = popup,
        endIconToolbar = Icons.Filled.AddComment,
        onClickEndIconToolbar = { events(CommentEvent.OnUpdateAddCommentDialogState(UIComponentState.Show)) },
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (state.comments.isEmpty()) {
                Text(
                    "No Comments!",
                    style = MaterialTheme.typography.titleLarge,
                    color = BorderColor,
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center,
                )
            }

            LazyColumn {
                items(state.comments, key = { it.id }) {
                    CommentBox(comment = it, modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}
