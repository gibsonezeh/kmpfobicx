package com.gibson.fobicx.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme

private val LightColors = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF0077CC),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFF0F0F0)
)

private val DarkColors = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF66B2FF),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFF222222)
)

@Composable
fun FobicxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
