package com.gibson.fobicx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gibson.fobicx.navigation.BottomNavBar
import com.gibson.fobicx.navigation.NavigationGraph
import com.gibson.fobicx.ui.theme.FobicxTheme
import com.gibson.fobicx.utils.ThemePreferenceManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()

            // Load saved theme preference on launch
            LaunchedEffect(Unit) {
                isDarkTheme = ThemePreferenceManager.isDarkMode(applicationContext)
            }

            FobicxTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavBar(navController)
                    }
                ) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
