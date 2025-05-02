package com.gibson.fobicx.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gibson.fobicx.utils.ThemePreferenceManager
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isDarkMode by remember { mutableStateOf(false) }

    // Load saved preference
    LaunchedEffect(Unit) {
        isDarkMode = ThemePreferenceManager.isDarkMode(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Profile Settings", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dark Mode")
            Switch(
                checked = isDarkMode,
                onCheckedChange = { enabled ->
                    isDarkMode = enabled
                    scope.launch {
                        ThemePreferenceManager.setDarkMode(context, enabled)
                    }
                }
            )
        }
    }
}
