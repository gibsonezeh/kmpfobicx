package com.gibson.fobicx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.gibson.fobicx.navigation.AppNavigation
import com.gibson.fobicx.ui.theme.FobicxTheme
import com.gibson.fobicx.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            FobicxTheme(useDarkTheme = isDarkTheme) {
                AppNavigation(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { themeViewModel.toggleTheme() }
                )
            }
        }
    }
}
