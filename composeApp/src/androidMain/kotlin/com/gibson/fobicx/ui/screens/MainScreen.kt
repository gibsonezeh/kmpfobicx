package com.gibson.fobicx.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.gibson.fobicx.navigation.Screen
import com.gibson.fobicx.ui.components.BottomNavBar
import com.gibson.fobicx.ui.screens.pages.*
import com.gibson.fobicx.viewmodel.ThemeViewModel

@Composable
fun MainScreen(
    onLogout: () -> Unit,
    onItemClick: () -> Unit = {},
    themeViewModel: ThemeViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                onItemClick = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
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
