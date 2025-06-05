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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import cafe.adriel.voyager.core.screen.Screen

object AccountDetailsScreen : Screen {
    @Composable
    override fun Content() {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()

        var isLoading by remember { mutableStateOf(true) }
        var error by remember { mutableStateOf<String?>(null) }

        var fullName by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        var accountType by remember { mutableStateOf("") }
        var dateOfBirth by remember { mutableStateOf("") }
        var avatarUrl by remember {
            mutableStateOf("https://via.placeholder.com/150")
        }

        LaunchedEffect(Unit) {
            val uid = auth.currentUser?.uid
            if (uid == null) {
                error = "User not logged in"
                isLoading = false
                return@LaunchedEffect
            }

            db.collection("users").document(uid).get()
                .addOnSuccessListener { doc ->
                    fullName = doc.getString("fullName") ?: ""
                    username = doc.getString("username") ?: ""
                    email = doc.getString("email") ?: ""
                    phoneNumber = doc.getString("phoneNumber") ?: ""
                    accountType = doc.getString("accountType") ?: ""
                    dateOfBirth = doc.getString("dateOfBirth") ?: ""
                    avatarUrl = doc.getString("avatarUrl")
                        ?: "https://via.placeholder.com/150"
                    isLoading = false
                }
                .addOnFailureListener {
                    error = it.message
                    isLoading = false
                }
        }

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = error ?: "Error", color = Color.Red)
            }
        } else {
            AccountDetailContent(
                fullName = fullName,
                username = username,
                email = email,
                phoneNumber = phoneNumber,
                accountType = accountType,
                dateOfBirth = dateOfBirth,
                avatarUrl = avatarUrl
            )
        }
    }
}

@Composable
fun AccountDetailContent(
    fullName: String,
    username: String,
    email: String,
    phoneNumber: String,
    accountType: String,
    dateOfBirth: String,
    avatarUrl: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        Text("Account Details", color = Color.White, fontSize = 22.sp)

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(avatarUrl),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text(fullName, color = Color.White, fontSize = 20.sp)
                Text(email, color = Color.Gray, fontSize = 14.sp)
            }
        }

        Spacer(Modifier.height(24.dp))

        Divider(color = Color.Gray)

        InfoRow("Username", username)
        InfoRow("Phone Number", phoneNumber)
        InfoRow("Account Type", accountType)
        InfoRow("Date of Birth", dateOfBirth)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(value, color = Color.White, fontSize = 16.sp)
    }
}
