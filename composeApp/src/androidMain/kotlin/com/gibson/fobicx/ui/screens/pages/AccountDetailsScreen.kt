package com.gibson.fobicx.ui.screens.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.GlideImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AccountDetailsScreen(
    navController: NavController
) {
    var fullName by remember { mutableStateOf("Loading...") }
    var email by remember { mutableStateOf("Loading...") }
    var avatarUrl by remember { mutableStateOf("https://via.placeholder.com/150") }

    // Load data from Firebase
    LaunchedEffect(Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uid = it.uid
            FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener { doc ->
                    fullName = doc.getString("fullName") ?: "No Name"
                    email = doc.getString("email") ?: "No Email"
                    avatarUrl = doc.getString("avatarUrl") ?: avatarUrl
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        Text(
            text = "Account Details",
            color = Color.White,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            GlideImage(
                model = avatarUrl,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = fullName, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = email, color = Color.Gray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Full Name", color = Color.Gray)
                Text(fullName, color = Color.White, fontSize = 16.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Text("Email", color = Color.Gray)
                Text(email, color = Color.White, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Add edit logic or show snackbar */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Edit")
        }
    }
}
