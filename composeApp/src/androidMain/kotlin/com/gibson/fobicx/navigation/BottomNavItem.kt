package com.gibson.fobicx.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Marketplace : BottomNavItem("marketplace", Icons.Default.ShoppingCart, "Marketplace")
    object Chat : BottomNavItem("chat", Icons.Default.Email, "Chat")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}
