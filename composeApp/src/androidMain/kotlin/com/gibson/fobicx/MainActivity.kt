package com.gibson.fobicx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import com.gibson.fobicx.navigation.AppNavigation
import com.gibson.fobicx.ui.theme.FobicxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FobicxTheme (isDarkTheme = isSystemInDarkTheme()){
                AppNavigation()
            }
        }
    }
}
