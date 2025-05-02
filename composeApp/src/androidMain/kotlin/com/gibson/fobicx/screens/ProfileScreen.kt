package com.gibson.fobicx.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.gibson.fobicx.ui.ThemePreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isDarkMode by remember { mutableStateOf(false) }

    // Load saved preference
    LaunchedEffect(Unit) {
        isDarkMode = withContext(Dispatchers.IO) {
            ThemePreferenceManager.isDarkMode(context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Profile", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Dark Mode")
            Switch(
                checked = isDarkMode,
                onCheckedChange = {
                    isDarkMode = it
                    scope.launch {
                        ThemePreferenceManager.setDarkMode(context, it)
                    }
                }
            )
        }
    }
}
