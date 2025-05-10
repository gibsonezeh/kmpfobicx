package com.gibson.fobicx.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.gibson.fobicx.ui.screens.*
import com.gibson.fobicx.ui.screens.auth.*
import com.gibson.fobicx.viewmodel.AuthViewModel
import com.gibson.fobicx.viewmodel.ThemeViewModel

@Composable
fun AppNavigation(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    val startDestination = if (authViewModel.isLoggedIn()) Screen.Home.route else Screen.Login.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = { navController.navigate(Screen.Signup.route) }
            )
        }
        composable(Screen.Signup.route) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(Screen.Home.route) {
            MainScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onItemClick = { route ->
                    if (route != Screen.Home.route) {
                        navController.navigate(route)
                    }
                },
                themeViewModel = themeViewModel
            )
        }

        // Other navigation destinations
        composable(Screen.Materials.route) {
            TextScreen("Materials Screen")
        }
        composable(Screen.Post.route) {
            TextScreen("Create Post Screen")
        }
        composable(Screen.Stock.route) {
            TextScreen("Stock Screen")
        }
        composable(Screen.Me.route) {
            TextScreen("Profile Screen")
        }
    }
}

@Composable
fun TextScreen(text: String) {
    androidx.compose.material3.Text(text = text)
}
