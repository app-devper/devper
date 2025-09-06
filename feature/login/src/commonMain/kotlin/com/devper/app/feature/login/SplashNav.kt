package com.devper.app.feature.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devper.app.feature.login.viewmodel.LoginViewModel
import com.devper.app.feature.navigation.SplashNavigation
import org.koin.compose.koinInject

@Composable
fun SplashNav(
    viewModel: LoginViewModel = koinInject(),
    navigateToMain: () -> Unit,
) {
    val navigator = rememberNavController()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle(
        lifecycleOwner = LocalLifecycleOwner.current,
    )

    NavHost(
        startDestination = SplashNavigation.Splash.route,
        navController = navigator,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(route = SplashNavigation.Splash.route) {
            SplashScreen(
                state = uiState,
                events = viewModel::onTriggerEvent,
                navigateToMain = navigateToMain,
                navigateToLogin = {
                    navigator.popBackStack()
                    navigator.navigate(SplashNavigation.Login.route)
                },
            )
        }
        composable(route = SplashNavigation.Login.route) {
            LoginScreen(
                state = uiState,
                events = viewModel::onTriggerEvent,
                navigateToMain = navigateToMain,
                navigateToRegister = {
                    navigator.navigate(SplashNavigation.Register.route)
                },
            )
        }
        composable(route = SplashNavigation.Register.route) {
            RegisterScreen(
                state = uiState,
                events = viewModel::onTriggerEvent,
                navigateToMain = navigateToMain,
                popUp = {
                    navigator.popBackStack()
                },
            )
        }
    }
}
