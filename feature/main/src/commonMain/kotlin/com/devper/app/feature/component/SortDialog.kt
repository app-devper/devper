package com.devper.app.feature.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devper.app.core.design.component.Spacer16dp
import com.devper.app.core.design.component.noRippleClickable
import com.devper.app.core.design.state.UIComponentState
import com.devper.app.core.domain.constants.Sort.HIGHER_PRICE
import com.devper.app.core.domain.constants.Sort.LOWEST_PRICE
import com.devper.app.core.domain.constants.Sort.MOST_SALE
import com.devper.app.core.domain.constants.Sort.NEWEST
import com.devper.app.core.domain.constants.Sort.OLDEST
import com.devper.app.feature.main.search.viewmodel.SearchEvent
import com.devper.app.feature.main.search.viewmodel.SearchState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDialog(
    state: SearchState,
    events: (SearchEvent) -> Unit,
) {
    val selectedSort = mutableStateOf(state.selectedSort)

    BasicAlertDialog(
        onDismissRequest = {
            events(SearchEvent.OnUpdateSortDialogState(UIComponentState.Hide))
        },
        modifier =
            Modifier
                .fillMaxWidth(0.9f)
                .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            Text(
                "Sort",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer16dp()

            Row(
                modifier =
                    Modifier.fillMaxWidth().noRippleClickable {
                        events(SearchEvent.OnUpdateSelectedSort(NEWEST))
                        events(SearchEvent.OnUpdateSortDialogState(UIComponentState.Hide))
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = selectedSort.value == NEWEST, onCheckedChange = {})
                Text("Newest", style = MaterialTheme.typography.labelLarge)
            }

            Row(
                modifier =
                    Modifier.fillMaxWidth().noRippleClickable {
                        events(SearchEvent.OnUpdateSelectedSort(OLDEST))
                        events(SearchEvent.OnUpdateSortDialogState(UIComponentState.Hide))
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = selectedSort.value == OLDEST, onCheckedChange = {})
                Text("Oldest", style = MaterialTheme.typography.labelLarge)
            }

            Row(
                modifier =
                    Modifier.fillMaxWidth().noRippleClickable {
                        events(SearchEvent.OnUpdateSelectedSort(HIGHER_PRICE))
                        events(SearchEvent.OnUpdateSortDialogState(UIComponentState.Hide))
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = selectedSort.value == HIGHER_PRICE, onCheckedChange = {})
                Text("Highest Price", style = MaterialTheme.typography.labelLarge)
            }

            Row(
                modifier =
                    Modifier.fillMaxWidth().noRippleClickable {
                        events(SearchEvent.OnUpdateSelectedSort(LOWEST_PRICE))
                        events(SearchEvent.OnUpdateSortDialogState(UIComponentState.Hide))
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = selectedSort.value == LOWEST_PRICE, onCheckedChange = {})
                Text("Lowest Price", style = MaterialTheme.typography.labelLarge)
            }

            Row(
                modifier =
                    Modifier.fillMaxWidth().noRippleClickable {
                        events(SearchEvent.OnUpdateSelectedSort(MOST_SALE))
                        events(SearchEvent.OnUpdateSortDialogState(UIComponentState.Hide))
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = selectedSort.value == MOST_SALE, onCheckedChange = {})
                Text("Most Sale", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}
