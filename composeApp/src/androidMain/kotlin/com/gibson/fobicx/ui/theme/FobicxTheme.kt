package com.gibson.fobicx.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF121212),
    onPrimary = Color.White,
    background = Color.Black,
    onBackground = Color.White
)

val LocalDarkMode = staticCompositionLocalOf { false }

@Composable
fun FobicxTheme(isDarkTheme: Boolean, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalDarkMode provides isDarkTheme) {
        MaterialTheme(
            colorScheme = if (isDarkTheme) DarkColors else LightColors,
            content = content
        )
    }
}
