package com.devper.app.feature.main.categories

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.devper.app.feature.main.categories.viewmodel.CategoriesViewModel
import com.devper.app.feature.main.search.SearchNav
import com.devper.app.feature.navigation.Categories
import com.devper.app.feature.navigation.CategoriesSearch
import org.koin.compose.koinInject

@Composable
fun CategoriesNav(popup: () -> Unit) {
    val navigator = rememberNavController()
    NavHost(
        startDestination = Categories,
        navController = navigator,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable<Categories> {
            val viewModel: CategoriesViewModel = koinInject()
            CategoriesScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                popup = popup,
            ) { categoryId ->
                navigator.navigate(CategoriesSearch(categoryId))
            }
        }

        composable<CategoriesSearch> { backStackEntry ->
            val search: CategoriesSearch = backStackEntry.toRoute()
            val categoryId = search.categoryId
            SearchNav(categoryId = categoryId, sort = null) {
                navigator.popBackStack()
            }
        }
    }
}
