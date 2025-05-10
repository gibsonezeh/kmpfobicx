package com.gibson.fobicx.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.navigation.compose.composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.gibson.fobicx.ui.components.BottomNavBar
import com.gibson.fobicx.ui.screens.pages.ProfileScreen

@Composable
fun MainScreen(onLogout: () -> Unit = {}) {
    val navController = rememberNavController()
    var isDarkTheme by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.widthIn(max = 500.dp)) {
                    BottomNavBar { clicked ->
                        navController.navigate(clicked) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavBar.Home.route) { Text("Home") }
            composable(BottomNavBar.Materials.route) { Text("Market") }
            composable(Screen.Post.route) { Text("Post") }
            composable(Screen.Stock.route) { Text("Stock") }
            composable(Screen.Me.route) { ProfileScreen(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme },
                    onLogout = onLogout
                )
            }
        }
    }
}
