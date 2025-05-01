package com.gibson.fobicx.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00695C),
    secondary = Color(0xFF26A69A),
    tertiary = Color(0xFF80CBC4)
)

@Composable
fun FobicxTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography(),
        content = content
    )
}
