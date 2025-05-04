package com.gibson.fobicx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gibson.fobicx.navigation.BottomNavBar
import com.gibson.fobicx.navigation.NavigationGraph
import com.gibson.fobicx.ui.theme.FobicxTheme
import com.gibson.fobicx.util.DarkModeManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isDark = DarkModeManager.isDarkMode(applicationContext)

        setContent {
            var isDarkMode by remember { mutableStateOf(isDark) }

            FobicxTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavBar(navController) }
                ) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                        onToggleTheme = { enabled ->
                            isDarkMode = enabled
                            DarkModeManager.setDarkMode(applicationContext, enabled)
                        }
                    )
                }
            }
        }
    }
}
