package com.gibson.fobicx.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.gibson.fobicx.viewmodel.AuthViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

@Composable fun AccountSetupScreen( navController: NavController, authViewModel: AuthViewModel = viewModel(), onSkip: () -> Unit, onSave: () -> Unit ) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser

var fullName by remember { mutableStateOf("") }
var userName by remember { mutableStateOf("") }
var dob by remember { mutableStateOf("") }
var accountType by remember { mutableStateOf("") }
var customAccountType by remember { mutableStateOf("") }

val accountTypes = listOf("Personal", "Business", "Organization", "Other")

Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
    verticalArrangement = Arrangement.Top
) {
    Text("Set Up Your Account", style = MaterialTheme.typography.headlineMedium)

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = fullName,
        onValueChange = { fullName = it },
        label = { Text("Full Name") },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = userName,
        onValueChange = { userName = it },
        label = { Text("Username") },
        modifier = Modifier.fillMaxWidth()
    )

    OutlinedTextField(
        value = dob,
        onValueChange = { dob = it },
        label = { Text("Date of Birth (e.g., 01/01/2000)") },
        modifier = Modifier.fillMaxWidth()
    )

    DropdownMenuBox(
        selectedOption = accountType,
        options = accountTypes,
        onOptionSelected = { accountType = it }
    )

    if (accountType == "Other") {
        OutlinedTextField(
            value = customAccountType,
            onValueChange = { customAccountType = it },
            label = { Text("Specify Account Type") },
            modifier = Modifier.fillMaxWidth()
        )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        TextButton(onClick = onSkip) {
            Text("Skip")
        }

        Button(onClick = {
            val uid = currentUser?.uid
            if (uid != null) {
                val userDoc = db.collection("users").document(uid)
                userDoc.get().addOnSuccessListener { doc ->
                    val createdAt = doc.getTimestamp("createdAt") ?: Timestamp.now()
                    val info = hashMapOf(
                        "fullName" to fullName,
                        "userName" to userName,
                        "dob" to dob,
                        "accountType" to (if (accountType == "Other") customAccountType else accountType),
                        "email" to currentUser.email,
                        "createdAt" to createdAt
                    )
                    userDoc.set(info).addOnSuccessListener {
                        Toast.makeText(context, "Account setup complete", Toast.LENGTH_SHORT).show()
                        onSave()
                    }
                }
            }
        }) {
            Text("Save")
        }
    }
}

}

@Composable fun DropdownMenuBox( selectedOption: String, options: List<String>, onOptionSelected: (String) -> Unit ) { var expanded by remember { mutableStateOf(false) }

Column {
    OutlinedTextField(
        value = selectedOption,
        onValueChange = {},
        label = { Text("Account Type") },
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }
        }
    )
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        options.forEach { option ->
            DropdownMenuItem(onClick = {
                onOptionSelected(option)
                expanded = false
            }, text = { Text(option) })
        }
    }
}

}

