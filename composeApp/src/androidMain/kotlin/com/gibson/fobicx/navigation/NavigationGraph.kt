package com.gibson.fobicx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gibson.fobicx.screens.ChatScreen
import com.gibson.fobicx.screens.HomeScreen
import com.gibson.fobicx.screens.MarketplaceScreen
import com.gibson.fobicx.screens.ProfileScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(route = BottomNavItem.Home.route) {
            HomeScreen(navController)
        }
        composable(route = BottomNavItem.Marketplace.route) {
            MarketplaceScreen(navController)
        }
        composable(route = BottomNavItem.Chat.route) {
            ChatScreen(navController)
        }
        composable(route = BottomNavItem.Profile.route) {
            ProfileScreen(navController)
        }
    }
}
