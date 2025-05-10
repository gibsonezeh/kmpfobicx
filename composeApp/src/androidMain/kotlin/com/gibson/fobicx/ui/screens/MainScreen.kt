package com.gibson.fobicx.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gibson.fobicx.ui.components.BottomNavBar
import com.gibson.fobicx.viewmodel.ThemeViewModel

@Composable
fun MainScreen(
    onLogout: () -> Unit,
    onItemClick: (String) -> Unit,
    themeViewModel: ThemeViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fobicx") },
                actions = {
                    IconButton(onClick = { themeViewModel.toggleTheme() }) {
                        Icon(imageVector = Icons.Default.Brightness6, contentDescription = "Toggle Theme")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavBar(onItemClick = onItemClick)
        }
    ) {
        Column(modifier = Modifier.padding(it).padding(16.dp)) {
            Text("Welcome to Fobicx!")
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = onLogout) {
                Text("Logout")
            }
        }
    }
}
