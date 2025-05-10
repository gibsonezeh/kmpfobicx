package com.gibson.fobicx.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.gibson.fobicx.ui.screens.*
import com.gibson.fobicx.ui.screens.auth.*
import com.gibson.fobicx.ui.screens.pages.*
import com.gibson.fobicx.viewmodel.AuthViewModel
import com.gibson.fobicx.viewmodel.ThemeViewModel

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val HOME = "home"
}

@Composable
fun AppNavigation(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    val startDestination = if (authViewModel.isLoggedIn()) Routes.HOME else Routes.LOGIN

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToSignup = { navController.navigate(Routes.SIGNUP) }
            )
        }
        composable(Routes.SIGNUP) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SIGNUP) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(Routes.LOGIN) }
            )
        }
        composable(Routes.HOME) {
            MainScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onItemClick = {}, // Add your navigation logic if needed
                themeViewModel = themeViewModel
            )
        }

        // Extra tabbed pages (replace MainScreen's onItemClick or bottom nav destinations with these routes)
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Materials.route) { MarketScreen() }
        composable(Screen.Post.route) { PostScreen() }
        composable(Screen.Stock.route) { StockScreen() }
        composable(Screen.Me.route) {
            ProfileScreen(
                isDarkTheme = themeViewModel.isDarkTheme,
                onToggleTheme = { themeViewModel.toggleTheme() },
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
