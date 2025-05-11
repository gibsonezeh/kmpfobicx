package com.gibson.fobicx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gibson.fobicx.navigation.AppNavigation
import com.gibson.fobicx.ui.theme.FobicxTheme
import com.gibson.fobicx.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            FobicxTheme(themeViewModel.isDarkTheme.value) {
                AppNavigation(themeViewModel)
            }
        }
    }
}
