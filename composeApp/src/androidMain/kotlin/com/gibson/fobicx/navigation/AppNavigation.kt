package com.gibson.fobicx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.gibson.fobicx.ui.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    val startDestination = if (authViewModel.isLoggedIn()) "home" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home") { popUpTo("login") { inclusive = true } } },
                onNavigateToSignup = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            SignupScreen(
                onSignupSuccess = { navController.navigate("home") { popUpTo("signup") { inclusive = true } } },
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
        composable("home") {
            MainScreen(
                onLogout = {
                    authViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}
