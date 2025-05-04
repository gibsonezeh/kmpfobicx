package com.gibson.fobicx.ui.screens

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gibson.fobicx.ui.components.BottomNavBar

@Composable
fun MainScreen() {
    var selected by remember { mutableStateOf("Home") }

    Scaffold(
        bottomBar = {
            BottomNavBar { clicked ->
                selected = clicked
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Current: $selected", modifier = Modifier.padding(16.dp))
        }
    }
}
