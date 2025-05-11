package com.gibson.fobicx.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.gibson.fobicx.navigation.Screen
import com.gibson.fobicx.ui.components.BottomNavBar
import com.gibson.fobicx.ui.screens.pages.*
import com.gibson.fobicx.viewmodel.ThemeViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    onLogout: () -> Unit,
    themeViewModel: ThemeViewModel
) {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onItemClick = { route ->
                    if (route != currentRoute) {
                        bottomNavController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(bottomNavController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        AnimatedNavHost(
            navController = bottomNavController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { slideInHorizontally { it } + fadeIn() },
            exitTransition = { slideOutHorizontally { -it } + fadeOut() },
            popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
            popExitTransition = { slideOutHorizontally { it } + fadeOut() }
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.Materials.route) { MarketScreen() }
            composable(Screen.Post.route) { PostScreen() }
            composable(Screen.Stock.route) { StockScreen() }
            composable(Screen.Me.route) {
                ProfileScreen(
                    isDarkTheme = themeViewModel.isDarkTheme.value,
                    onToggleTheme = { themeViewModel.toggleTheme() },
                    onLogout = onLogout
                )
            }
        }
    }
}
