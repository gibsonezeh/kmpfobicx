package com.gibson.fobicx.ui.screens.pages

import android.widget.ImageView
import androidx.compose.ui.draw.clip
import com.bumptech.glide.Glide
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.gibson.fobicx.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(
    navController: NavController,
    onAccountClick: () -> Unit,
    viewModel: ProfileViewModel= viewModel()

) {
    var userName by remember { mutableStateOf("Loading...") }
    var userEmail by remember { mutableStateOf("Loading...") }
    var avatarUrl by remember { mutableStateOf("https://via.placeholder.com/64") }
    var isLoading by remember { mutableStateOf(true) }

    // Fetch user info from Firebase
    LaunchedEffect(Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val uid = user.uid
            FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener { doc ->
                    userName = doc.getString("fullName") ?: "No Name"
                    userEmail = doc.getString("email") ?: "No Email"
                    avatarUrl = doc.getString("avatarUrl") ?: avatarUrl
                    isLoading = false
                }
                .addOnFailureListener {
                    userName = "Error"
                    userEmail = "Failed to load"
                    isLoading = false
                }
        } else {
            userName = "Guest"
            userEmail = "Not logged in"
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 100.dp)
            )
        } else {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                GlideImage(
                    url = avatarUrl,
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
}

@Composable
fun GlideImage(url: String, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(context).load(url).into(this)
            }
        },
        modifier = modifier
    )
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
