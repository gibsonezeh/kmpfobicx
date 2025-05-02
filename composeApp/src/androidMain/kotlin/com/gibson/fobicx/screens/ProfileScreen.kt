package com.gibson.fobicx.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gibson.fobicx.utils.ThemePreferenceManager
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(context: Context) {
    val scope = rememberCoroutineScope()
    var isDark by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isDark = ThemePreferenceManager.isDarkMode(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Profile", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dark Mode")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isDark,
                onCheckedChange = {
                    isDark = it
                    scope.launch {
                        ThemePreferenceManager.setDarkMode(context, it)
                    }
                }
            )
        }
    }
}
