package com.gibson.fobicx.ui.screens.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun AccountDetailsScreen() {
    val context = LocalContext.current

    var fullName by remember { mutableStateOf("Loading...") }
    var email by remember { mutableStateOf("Loading...") }
    var phone by remember { mutableStateOf("Loading...") }
    var username by remember { mutableStateOf("Loading...") }
    var avatarUrl by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            try {
                val doc = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(user.uid)
                    .get()
                    .await()

                fullName = doc.getString("fullName") ?: "No name"
                email = doc.getString("email") ?: "No email"
                phone = doc.getString("phone") ?: "No phone"
                username = doc.getString("username") ?: "No username"
                avatarUrl = doc.getString("avatarUrl") ?: ""

                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Error loading account info"
                isLoading = false
            }
        } else {
            errorMessage = "User not logged in"
            isLoading = false
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage ?: "Unknown error")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Avatar
                if (avatarUrl.isNotEmpty()) {
                    GlideImage(
                        model = avatarUrl,
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp),
                        shape = CircleShape
                    )
                }

                // Full Name
                AccountDetailItem(label = "Full Name", value = fullName)

                // Username
                AccountDetailItem(label = "Username", value = username)

                // Email
                AccountDetailItem(label = "Email", value = email)

                // Phone
                AccountDetailItem(label = "Phone", value = phone)
            }
        }
    }
}

@Composable
fun AccountDetailItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
    }
}
