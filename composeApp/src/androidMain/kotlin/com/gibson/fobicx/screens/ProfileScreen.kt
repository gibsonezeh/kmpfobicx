package com.gibson.fobicx.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gibson.fobicx.util.DarkModeManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isDarkMode by remember { mutableStateOf(false) }

    // Load saved dark mode preference
    LaunchedEffect(Unit) {
        isDarkMode = withContext(Dispatchers.IO) {
            DarkModeManager.isDarkMode(context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Dark Mode")

            Switch(
                checked = isDarkMode,
                onCheckedChange = { enabled ->
                    isDarkMode = enabled
                    coroutineScope.launch {
                        DarkModeManager.setDarkMode(context, enabled)
                    }
                }
            )
        }
    }
}
