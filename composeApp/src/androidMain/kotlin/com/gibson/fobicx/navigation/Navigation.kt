package com.gibson.fobicx.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Materials : Screen("market")
    object Post : Screen("post")
    object Stock : Screen("stock")
    object Me : Screen("me")
}
