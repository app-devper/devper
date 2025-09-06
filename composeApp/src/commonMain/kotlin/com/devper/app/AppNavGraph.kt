package com.devper.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devper.app.feature.login.SplashNav
import com.devper.app.navigation.AppNavigation
import com.devper.app.feature.main.MainNav

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = Modifier.then(modifier),
        startDestination = AppNavigation.Splash.route,
        navController = navController,
    ) {
        composable(route = AppNavigation.Splash.route) {
            SplashNav(navigateToMain = {
                navController.popBackStack()
                navController.navigate(AppNavigation.Main.route)
            })
        }

        composable(route = AppNavigation.Main.route) {
            MainNav(logout = {
                navController.popBackStack()
                navController.navigate(AppNavigation.Splash.route)
            })
        }
    }
}