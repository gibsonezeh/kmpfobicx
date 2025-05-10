package com.gibson.fobicx.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onLogout: () -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onItemClick: (String) -> Unit = {} // Default to avoid error if not passed
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fobicx") },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(Icons.Default.Brightness6, contentDescription = "Toggle Theme")
                    }
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Welcome to Fobicx!",
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Example clickable items
            listOf("Dashboard", "Profile", "Settings").forEach { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(item) }
                        .padding(12.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}
