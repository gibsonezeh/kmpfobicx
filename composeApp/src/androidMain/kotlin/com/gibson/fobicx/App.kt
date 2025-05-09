package com.gibson.fobicx

import androidx.compose.runtime.*
import com.gibson.fobicx.presentation.screens.MainScreen
import com.gibson.fobicx.ui.theme.FobicxTheme

@Composable
fun App() {
    var isDarkTheme by remember { mutableStateOf(false) }

    FobicxTheme(isDarkTheme = isDarkTheme) {
        MainScreen(
            onLogout = { /* your logout logic */ },
            onToggleTheme = { isDarkTheme = !isDarkTheme }
        )
    }
}
