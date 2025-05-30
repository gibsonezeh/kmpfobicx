package com.gibson.fobicx.ui.screens.pages

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gibson.fobicx.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


            @Composable
fun ProfileScreen(
    userName: String,
    userEmail: String,
    onAccountClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_avatar), // Replace with user avatar
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = userName, color = Color.White, fontSize = 20.sp)
                Text(text = userEmail, color = Color.Gray)
            }
        }

        // Plan Card
        Card(
            backgroundColor = Color.DarkGray,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Free", color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Credits", color = Color.Gray)
                    Text("0 ★", color = Color.White)
                    Text("Daily credits refresh at 01:00", color = Color.Gray, fontSize = 12.sp)
                }
                Button(onClick = { /* Upgrade logic */ }) {
                    Text("Upgrade")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menu Sections
        SettingsSection("Menus") {
            SettingsItem("Share with a friend") { /* Navigate */ }
            SettingsItem("Knowledge") { /* Navigate */ }
            SettingsItem("Language", rightText = "English") { /* Navigate */ }
        }

        SettingsSection("General") {
            SettingsItem("Account") {
                onAccountClick() // navigate to profile details
            }
            SettingsItem("Appearance", rightText = "Follow system") { /* Theme */ }
            SettingsItem("Clear cache", rightText = "2 MB") { /* Clear logic */ }
        }

        SettingsSection("Information") {
            SettingsItem("Rate this app") { /* Open Play Store */ }
            SettingsItem("Contact us") { /* Contact support */ }
        }
    }
}

@Composable
fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = title, color = Color.Gray, fontSize = 12.sp)
        content()
    }
}

@Composable
fun SettingsItem(text: String, rightText: String? = null, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text, color = Color.White)
        rightText?.let {
            Text(it, color = Color.Gray)
        }
    }
}
