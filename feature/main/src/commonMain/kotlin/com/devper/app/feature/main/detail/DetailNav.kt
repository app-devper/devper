package com.devper.app.feature.main.detail


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devper.app.feature.navigation.DetailNavigation
import org.koin.compose.koinInject
import com.devper.app.feature.main.comment.CommentScreen
import com.devper.app.feature.main.detail.viewmodel.DetailViewModel
import presentation.ui.main.comment.view_model.CommentViewModel
import presentation.ui.main.detail.view_model.DetailEvent

@Composable
fun DetailNav(id: String, popUp: () -> Unit) {
    val navigator = rememberNavController()
    NavHost(
        startDestination = DetailNavigation.Detail.route,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = DetailNavigation.Detail.route) {
            val viewModel: DetailViewModel = koinInject()
            LaunchedEffect(id) {
                viewModel.onTriggerEvent(DetailEvent.GetProduct(id))
            }
            DetailScreen(state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                popup = {
                    popUp()
                }, navigateToMoreComment = {
                    navigator.navigate(DetailNavigation.Comment.route.plus("/$it"))
                })
        }
        composable(route = DetailNavigation.Comment.route.plus("/{id}")) { backStackEntry ->
            val viewModel: CommentViewModel = koinInject()
            CommentScreen(state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                popup = {
                    navigator.popBackStack()
                })
        }
    }
}
