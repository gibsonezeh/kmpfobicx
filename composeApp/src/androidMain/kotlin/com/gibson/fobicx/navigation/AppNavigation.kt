package com.gibson.fobicx.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gibson.fobicx.ui.screens.MainScreen
import com.gibson.fobicx.ui.screens.auth.LoginScreen
import com.gibson.fobicx.ui.screens.auth.SignupScreen
import com.gibson.fobicx.viewmodel.AuthViewModel

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val HOME = "home"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    val startDestination = if (authViewModel.isLoggedIn()) Routes.HOME else Routes.LOGIN

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.LOGIN) {
            LoginScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Routes.SIGNUP) {
            SignupScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Routes.HOME) {
            MainScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }
    }
}
