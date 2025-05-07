package com.gibson.fobicx.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Light/Dark Theme Toggle (Placeholder)
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text("Dark Mode")
            Switch(checked = false, onCheckedChange = {})
        }

        // Cover photo and profile image
        Box(modifier = Modifier.fillMaxWidth().height(180.dp).background(Color.Gray)) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = 16.dp, y = 50.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
            ) {
                // Placeholder for profile image
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        // User Info Section
        Column(modifier = Modifier.padding(16.dp)) {
            Text("John Doe", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Account Type: Aluminium Fabrication, Business")
            Text("Email: johndoe@example.com")
            Text("UID: uid001")
        }

        // More sections to be added (Teams, Videos, Settings, etc.)
    }
}
