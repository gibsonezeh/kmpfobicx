package com.gibson.fobicx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.gibson.fobicx.navigation.BottomNavBar
import com.gibson.fobicx.navigation.NavigationGraph
import com.gibson.fobicx.ui.ThemePreferenceManager
import com.gibson.fobicx.ui.theme.FobicxTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            // Read dark mode setting when app starts
            LaunchedEffect(Unit) {
                isDarkMode = withContext(Dispatchers.IO) {
                    ThemePreferenceManager.isDarkMode(applicationContext)
                }
            }

            FobicxTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavBar(navController) }
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
