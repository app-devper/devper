package com.devper.app.feature.main.search


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import business.domain.main.Category
import org.koin.compose.koinInject
import com.devper.app.feature.main.detail.DetailNav
import com.devper.app.feature.main.search.viewmodel.SearchEvent
import com.devper.app.feature.main.search.viewmodel.SearchViewModel
import com.devper.app.feature.navigation.Search
import com.devper.app.feature.navigation.SearchDetail

@Composable
fun SearchNav(categoryId: Int?, sort: Int?, popUp: () -> Unit) {
    val navigator = rememberNavController()
    NavHost(
        startDestination = Search,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Search> {
            val viewModel: SearchViewModel = koinInject()
            LaunchedEffect(sort, categoryId) {
                val categories = if (categoryId != null) listOf(Category(id = categoryId)) else null
                sort?.let {
                    viewModel.onTriggerEvent(SearchEvent.OnUpdateSelectedSort(sort))
                }
                if (categoryId != null || sort != null) {
                    viewModel.onTriggerEvent(SearchEvent.Search(categories = categories))
                }
            }
            SearchScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                navigateToDetailScreen = {
                    navigator.popBackStack()
                    navigator.navigate(SearchDetail(it))
                },
                popUp = { popUp() }
            )
        }
        composable<SearchDetail> { backStackEntry ->
            val detail: SearchDetail = backStackEntry.toRoute()
            DetailNav(detail.id.toString()) {
                navigator.popBackStack()
            }
        }
    }
}
