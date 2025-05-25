package com.gibson.fobicx.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions 
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown 
import androidx.compose.material3.* 
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier 
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation 
import androidx.compose.ui.unit.dp 
import androidx.lifecycle.viewmodel.compose.viewModel 
import com.gibson.fobicx.viewmodel.AuthState 
import com.gibson.fobicx.viewmodel.AuthViewModel 
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)

@Composable fun SignupScreen( authViewModel: AuthViewModel = viewModel(), onSignupSuccess: () -> Unit, onNavigateToLogin: () -> Unit ) { var step by remember { mutableStateOf(1) }

// Step 1
var fullName by remember { mutableStateOf("") }
var username by remember { mutableStateOf("") }
var email by remember { mutableStateOf("") }

// Step 2
var accountType by remember { mutableStateOf("") }
var customAccountType by remember { mutableStateOf("") }
var expanded by remember { mutableStateOf(false) }
var dateOfBirth by remember { mutableStateOf("") }

// Step 3
var password by remember { mutableStateOf("") }
var confirmPassword by remember { mutableStateOf("") }

val accountTypes = listOf(
    "Aluminium Fabrication", "Retail", "Freelancing", "Education",
    "Healthcare", "Construction", "Tech", "Real Estate", "Entertainment",
    "Logistics", "Other"
)

val authState by authViewModel.authState.collectAsState()

Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
    verticalArrangement = Arrangement.Center
) {
    Text("Sign Up - Step $step", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.height(24.dp))

    when (step) {
        1 -> {
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        2 -> {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = accountType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Account Type") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    accountTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                accountType = type
                                expanded = false
                            }
                        )
                    }
                }
            }

            if (accountType == "Other") {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = customAccountType,
                    onValueChange = { customAccountType = it },
                    label = { Text("Enter your account type") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                label = { Text("Date of Birth") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        3 -> {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
        onClick = {
            when (step) {
                1 -> if (fullName.isNotBlank() && username.isNotBlank() && email.isNotBlank()) step++
                2 -> if (accountType.isNotBlank() && dateOfBirth.isNotBlank() && (accountType != "Other" || customAccountType.isNotBlank())) step++
                3 -> if (password == confirmPassword && password.length >= 6) {
                    val finalAccountType = if (accountType == "Other") customAccountType else accountType
                    authViewModel.signup(email, password) // Extend this to pass other user info to Firestore
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(if (step < 3) "Next" else "Sign Up")
    }

    Spacer(modifier = Modifier.height(8.dp))

    TextButton(onClick = onNavigateToLogin) {
        Text("Already have an account? Login")
    }

    Spacer(modifier = Modifier.height(16.dp))

    when (authState) {
        is AuthState.Loading -> CircularProgressIndicator()
        is AuthState.Success -> {
            LaunchedEffect(Unit) {
                delay(500)
                onSignupSuccess()
            }
        }
        is AuthState.Error -> {
            Text(
                text = (authState as AuthState.Error).message ?: "Signup failed",
                color = MaterialTheme.colorScheme.error
            )
        }
        else -> {}
    }
}

}

