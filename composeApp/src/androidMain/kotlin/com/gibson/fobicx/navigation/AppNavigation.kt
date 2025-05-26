package com.gibson.fobicx.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.gibson.fobicx.ui.screens.HomeScreen
import com.gibson.fobicx.ui.screens.ProfileScreen
import com.gibson.fobicx.ui.screens.auth.AccountSetupScreen
import com.gibson.fobicx.ui.screens.auth.PhoneVerificationScreen
import com.gibson.fobicx.ui.screens.auth.SignupScreen
import com.gibson.fobicx.viewmodel.AuthViewModel
import com.gibson.fobicx.viewmodel.ProfileViewModel

object Routes {
    const val SIGNUP = "signup"
    const val ACCOUNT_SETUP = "account_setup"
    const val PHONE_VERIFICATION = "phone_verification"
    const val HOME = "home"
    const val PROFILE = "profile"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.SIGNUP) {
        composable(Routes.SIGNUP) {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Routes.ACCOUNT_SETUP)
                }
            )
        }
        composable(Routes.ACCOUNT_SETUP) {
            AccountSetupScreen(
                onSkip = {
                    navController.navigate(Routes.PHONE_VERIFICATION)
                },
                onContinue = {
                    navController.navigate(Routes.PHONE_VERIFICATION)
                }
            )
        }
        composable(Routes.PHONE_VERIFICATION) {
            PhoneVerificationScreen(
                onSkip = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SIGNUP) { inclusive = true }
                    }
                },
                onVerified = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SIGNUP) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) {
            HomeScreen(
                onProfileClick = {
                    navController.navigate(Routes.PROFILE)
                }
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen()
        }
    }
}
