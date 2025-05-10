package com.gibson.fobicx.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
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
    themeViewModel: ThemeViewModel
) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                onItemClick = { route ->
                    bottomNavController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(bottomNavController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
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
