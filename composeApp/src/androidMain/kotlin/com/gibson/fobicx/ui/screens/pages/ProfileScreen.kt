package com.gibson.fobicx.ui.screens.pages

import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(
    navController: NavController
) {
    var userName by remember { mutableStateOf("Loading...") }
    var userEmail by remember { mutableStateOf("Loading...") }
    var avatarUrl by remember { mutableStateOf("https://via.placeholder.com/64") }

    // Fetch user info from Firebase
    LaunchedEffect(Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uid = it.uid
            FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener { doc ->
                    userName = doc.getString("fullName") ?: "No Name"
                    userEmail = doc.getString("email") ?: "No Email"
                    avatarUrl = doc.getString("avatarUrl") ?: avatarUrl
                }
        }
    }

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
                painter = rememberImagePainter(avatarUrl),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = userName, color = Color.White, fontSize = MaterialTheme.typography.h6.fontSize)
                Text(text = userEmail, color = Color.Gray)
            }
        }

        // Plan Card
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Free", color = Color.White, style = MaterialTheme.typography.body1)
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
            SettingsItem("Share with a friend") {}
            SettingsItem("Knowledge") {}
            SettingsItem("Language", rightText = "English") {}
        }

        SettingsSection("General") {
            SettingsItem("Account") {
                navController.navigate("account_details")
            }
            SettingsItem("Appearance", rightText = "Follow system") {}
            SettingsItem("Clear cache", rightText = "2 MB") {}
        }

        SettingsSection("Information") {
            SettingsItem("Rate this app") {}
            SettingsItem("Contact us") {}
        }
    }
}

// Custom Composables
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
