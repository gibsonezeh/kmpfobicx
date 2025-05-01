package com.gibson.fobicx.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gibson.fobicx.navigation.BottomNavBar
import com.gibson.fobicx.screens.*

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = androidx.compose.ui.Modifier.padding(padding)
        ) {
            composable("home") { HomeScreen() }
            composable("market") { MarketplaceScreen() }
            composable("post") { PostScreen() }
            composable("chat") { ChatScreen() }
            composable("profile") { ProfileScreen() }
        }
    }
}
