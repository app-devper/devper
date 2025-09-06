package com.devper.app.feature.main.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.devper.app.feature.main.categories.CategoriesNav
import com.devper.app.feature.main.detail.DetailNav
import com.devper.app.feature.main.home.viewmodel.HomeViewModel
import com.devper.app.feature.main.notifications.NotificationsScreen
import com.devper.app.feature.main.notifications.viewmodel.NotificationsViewModel
import com.devper.app.feature.main.search.SearchNav
import com.devper.app.feature.main.settings.viewmodel.SettingsViewModel
import com.devper.app.feature.navigation.Home
import com.devper.app.feature.navigation.HomeCategories
import com.devper.app.feature.navigation.HomeDetail
import com.devper.app.feature.navigation.HomeSearch
import com.devper.app.feature.navigation.Notification
import com.devper.app.feature.navigation.Settings
import org.koin.compose.koinInject
import presentation.ui.main.settings.SettingsScreen

@Composable
fun HomeNav(logout: () -> Unit) {
    val navigator = rememberNavController()

    NavHost(
        startDestination = Home,
        navController = navigator,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable<Home> {
            val viewModel: HomeViewModel = koinInject()
            HomeScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                navigateToNotifications = {
                    navigator.navigate(Notification)
                },
                navigateToCategories = {
                    navigator.navigate(HomeCategories)
                },
                navigateToSetting = {
                    navigator.navigate(Settings)
                },
                navigateToDetail = {
                    navigator.popBackStack()
                    navigator.navigate(HomeDetail(it))
                },
                navigateToSearch = { categoryId, sort ->
                    navigator.navigate(HomeSearch(categoryId, sort))
                },
            )
        }

        composable<Settings> {
            val viewModel: SettingsViewModel = koinInject()
            SettingsScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                logout = logout,
                popup = {
                    navigator.popBackStack()
                },
            )
        }

        composable<HomeCategories> {
            CategoriesNav {
                navigator.popBackStack()
            }
        }

        composable<HomeSearch> { backStackEntry ->
            val argument: HomeSearch = backStackEntry.toRoute()
            val categoryId = argument.categoryId
            val sort = argument.sort
            SearchNav(categoryId = categoryId, sort = sort) {
                navigator.popBackStack()
            }
        }

        composable<HomeDetail> { backStackEntry ->
            val argument: HomeDetail = backStackEntry.toRoute()
            val id = argument.id
            DetailNav(id.toString()) {
                navigator.popBackStack()
            }
        }

        composable<Notification> {
            val viewModel: NotificationsViewModel = koinInject()
            NotificationsScreen(
                state = viewModel.state.value,
                events = viewModel::onTriggerEvent,
                popup = {
                    navigator.popBackStack()
                },
            )
        }
    }
}
