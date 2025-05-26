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
fun ProfileScreen(authViewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser
    val uid = currentUser?.uid

    var fullName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var accountType by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }

    var isEditing by remember { mutableStateOf(false) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(uid) {
        uid?.let {
            db.collection("users").document(it).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        fullName = document.getString("fullName") ?: ""
                        userName = document.getString("userName") ?: ""
                        dob = document.getString("dob") ?: ""
                        accountType = document.getString("accountType") ?: ""
                        phoneNumber = document.getString("phoneNumber") ?: ""
                    }
                }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("My Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        if (isEditing) {
            OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = userName, onValueChange = { userName = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = dob, onValueChange = { dob = it }, label = { Text("Date of Birth") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = accountType, onValueChange = { accountType = it }, label = { Text("Account Type") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    uid?.let {
                        val updatedData = mapOf(
                            "fullName" to fullName,
                            "userName" to userName,
                            "dob" to dob,
                            "accountType" to accountType,
                            "phoneNumber" to phoneNumber
                        )
                        db.collection("users").document(it).update(updatedData)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                                isEditing = false
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        } else {
            Text("Full Name: $fullName")
            Text("Username: $userName")
            Text("Email: $email")
            Text("Date of Birth: $dob")
            Text("Account Type: $accountType")
            Text("Phone Number: $phoneNumber")

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { isEditing = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit")
            }
        }
    }
}
