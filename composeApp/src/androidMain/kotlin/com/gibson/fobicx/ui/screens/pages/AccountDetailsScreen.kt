package com.gibson.fobicx.ui.screens.pages

import android.widget.ImageView
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AccountDetailsScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var avatarUrl by remember { mutableStateOf("https://via.placeholder.com/150") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uid = it.uid
            FirebaseFirestore.getInstance().collection("users").document(uid)
                .get()
                .addOnSuccessListener { doc ->
                    fullName = doc.getString("fullName") ?: ""
                    email = doc.getString("email") ?: ""
                    username = doc.getString("username") ?: ""
                    phoneNumber = doc.getString("phoneNumber") ?: ""
                    avatarUrl = doc.getString("avatarUrl") ?: avatarUrl
                    isLoading = false
                }
                .addOnFailureListener {
                    isLoading = false
                }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    } else {
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
                    url = avatarUrl,
                    modifier = Modifier
                        .size(72.dp)
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
                    AccountDetailItem(label = "Full Name", value = fullName)
                    AccountDetailItem(label = "Email", value = email)
                    AccountDetailItem(label = "Username", value = username)
                    AccountDetailItem(label = "Phone Number", value = phoneNumber)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Navigate to edit profile screen */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Edit")
            }
        }
    }
}

@Composable
fun AccountDetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Text(text = value, color = Color.White, fontSize = 16.sp)
    }
}

@Composable
fun GlideImage(url: String, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { imageView ->
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        },
        modifier = modifier
    )
}
