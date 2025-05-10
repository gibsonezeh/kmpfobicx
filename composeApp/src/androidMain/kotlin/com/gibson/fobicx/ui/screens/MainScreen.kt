package com.gibson.fobicx.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.gibson.fobicx.navigation.Screen
import com.gibson.fobicx.ui.components.BottomNavigationBar
import com.gibson.fobicx.ui.screens.pages.*
import com.gibson.fobicx.viewmodel.ThemeViewModel

@Composable
fun MainScreen(
    onLogout: () -> Unit,
    onItemClick: () -> Unit = {},
    themeViewModel: ThemeViewModel
) {
    val bottomNavController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Materials, Screen.Post, Screen.Stock, Screen.Me)

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = bottomNavController, items = items)
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
