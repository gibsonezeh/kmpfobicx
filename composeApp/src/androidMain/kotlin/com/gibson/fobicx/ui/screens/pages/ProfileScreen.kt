package com.gibson.fobicx.ui.screens.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gibson.fobicx.R

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit = {},
    isDarkTheme: Boolean = false,
    onThemeToggle: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Toggle Theme Button (Top Right)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Switch(checked = isDarkTheme, onCheckedChange = { onThemeToggle() })
        }

        // Cover Photo and Profile Picture
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(MaterialTheme.colorScheme.primary)
        ) {
            Image(
                painter = painterResource(id = R.drawable.cover_placeholder), // replace with your cover image
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            Image(
                painter = painterResource(id = R.drawable.profile_placeholder), // replace with your profile image
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = -50.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        // User Info
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("Gibson Ezeh", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Account Types: Aluminium Fabrication, Business")
            Text("Email: gibson@example.com")
            Text("UID: uid001k1")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section Links
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Teams", modifier = Modifier.padding(vertical = 4.dp))
            Text("Skits / Short Videos", modifier = Modifier.padding(vertical = 4.dp))
            Text("Settings", modifier = Modifier.padding(vertical = 4.dp))
            Text("Account Checkup", modifier = Modifier.padding(vertical = 4.dp))
            Text("Privacy Settings", modifier = Modifier.padding(vertical = 4.dp))
            Text("More", modifier = Modifier.padding(vertical = 4.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        // Logout Button
        Button(
            onClick = onLogoutClick,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text("Logout")
        }
    }
}
