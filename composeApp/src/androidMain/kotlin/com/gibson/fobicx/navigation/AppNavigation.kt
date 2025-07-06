// AppNavigation.kt 
package com.gibson.fobicx.navigation

import androidx.compose.runtime.Composable 
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController 
import androidx.navigation.compose.NavHost 
import androidx.navigation.compose.composable 
import androidx.navigation.compose.rememberNavController 
import com.gibson.fobicx.ui.screens.* 
import com.gibson.fobicx.ui.screens.auth.*
import com.gibson.fobicx.ui.screens.pages.*
import com.gibson.fobicx.viewmodel.AuthViewModel

object Routes {
    const val LOGIN = "login" 
    const val SIGNUP = "signup"
    const val ACCOUNT_SETUP = "account_setup"
    const val PHONE_VERIFICATION = "phone_verification" 
    const val HOME = "home" 
}

@Composable fun AppNavigation() { 
    val navController = rememberNavController() 
    val authViewModel: AuthViewModel = viewModel()

val startDestination = if (authViewModel.isLoggedIn()) Routes.HOME else Routes.LOGIN

NavHost(navController = navController, startDestination = startDestination) {
    composable(Routes.LOGIN) {
        LoginScreen(
            onLoginSuccess = { navController.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } } },
            onNavigateToSignup = { navController.navigate(Routes.SIGNUP) }
        )
    }

    composable(Routes.SIGNUP) {
        SignupScreen(
            onSignupSuccess = { navController.navigate(Routes.ACCOUNT_SETUP) },
            onNavigateToLogin = { navController.navigate(Routes.LOGIN) }
        )
    }

    composable(Routes.ACCOUNT_SETUP) {
        AccountSetupScreen(
            navController = navController,
            onSave = { navController.navigate(Routes.PHONE_VERIFICATION) },
            onSkip = { navController.navigate(Routes.HOME)
            }
        )
    }

    composable(Routes.PHONE_VERIFICATION) {
        PhoneNumberVerificationScreen(
            onVerificationSuccess = { navController.navigate(Routes.HOME) },
            onSkip = { navController.navigate(Routes.HOME) }
        )
    }

    composable(Routes.HOME) {
        MainScreen(
            onLogout = {}
        )
    }
}

}

// Other screens (SignupScreen, LoginScreen, AccountSetupScreen, etc.) will be added next one by one.

