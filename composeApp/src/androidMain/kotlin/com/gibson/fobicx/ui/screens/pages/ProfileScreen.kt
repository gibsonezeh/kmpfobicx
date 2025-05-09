package com.gibson.fobicx.ui.screens.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onLogout: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Theme toggle (top right)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Theme", fontSize = 14.sp)
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onToggleTheme() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Cover Photo Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Gray)
                .clickable { /* Trigger cover photo upload */ },
            contentAlignment = Alignment.BottomStart
        ) {
            // Profile Photo Placeholder inside Cover
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable { /* Trigger profile image upload */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User Info
        Text(text = "Username: Gibson Ezeh", fontSize = 18.sp)
        Text(text = "Account Type: Aluminium Fabrication, Business", fontSize = 14.sp)
        Text(text = "Email: gibson@example.com", fontSize = 14.sp)
        Text(text = "UID: uid001", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Teams & Skits
        Text(text = "Teams", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Skits / Short Videos", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Other Settings
        Text(text = "Settings", modifier = Modifier.clickable { }, fontSize = 16.sp)
        Text(text = "Account Checkup", modifier = Modifier.clickable { }, fontSize = 16.sp)
        Text(text = "Privacy Settings", modifier = Modifier.clickable { }, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // Logout
        Button(onClick = onLogout, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Logout")
        }
    }
}
