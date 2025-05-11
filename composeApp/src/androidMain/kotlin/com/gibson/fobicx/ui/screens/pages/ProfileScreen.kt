package com.gibson.fobicx.ui.screens.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Theme", fontSize = 16.sp)
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onToggleTheme() }
            )
        }

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

        // User Info (Can be made dynamic)
        Text("Username: Gibson Ezeh", fontSize = 18.sp)
        Text("Account Type: Aluminium Fabrication, Business", fontSize = 14.sp)
        Text("Email: gibson@example.com", fontSize = 14.sp)
        Text("UID: uid001", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Teams & Skits
        Text("Teams", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Skits / Short Videos", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Other Settings
        listOf("Settings", "Account Checkup", "Privacy Settings").forEach {
            Text(text = it, modifier = Modifier
                .fillMaxWidth()
                .clickable { /* Navigate or perform actions */ }
                .padding(vertical = 8.dp),
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Logout
        Button(
            onClick = onLogout,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Logout")
        }
    }
}
