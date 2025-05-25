//@OptIn(ExperimentalMaterial3Api::class)

package com.gibson.fobicx.ui.screens.auth

import android.app.DatePickerDialog
import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gibson.fobicx.viewmodel.AuthState
import com.gibson.fobicx.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun MultiStepSignupScreen(
    authViewModel: AuthViewModel = viewModel(),
    onSignupSuccess: () -> Unit
) {
    var currentStep by remember { mutableStateOf(1) }
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var accountType by remember { mutableStateOf("") }
    var customAccountType by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        when (currentStep) {
            1 -> {
                Text("Step 1: Basic Info", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    if (fullName.isNotBlank() && username.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        authViewModel.checkUsernameExists(username) { exists ->
                            if (!exists) currentStep++ else authViewModel.setError("Username already exists")
                        }
                    } else {
                        authViewModel.setError("Please enter valid info")
                    }
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Next")
                }
            }

            2 -> {
                Text("Step 2: Account Info", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                val accountTypes = listOf("Engineer", "Doctor", "Developer", "Fabricator", "Other")
                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                    OutlinedTextField(
                        value = accountType,
                        onValueChange = { accountType = it },
                        label = { Text("Account Type") },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        readOnly = true
                    )
                    ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
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
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = customAccountType,
                        onValueChange = { customAccountType = it },
                        label = { Text("Specify your Account Type") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                val calendar = Calendar.getInstance()
                val datePicker = DatePickerDialog(
                    context,
                    { _, y, m, d -> dob = String.format("%04d-%02d-%02d", y, m + 1, d) },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                Button(onClick = { datePicker.show() }) {
                    Text(if (dob.isBlank()) "Select Date of Birth" else dob)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    if (dob.isNotBlank()) currentStep++ else authViewModel.setError("DOB required")
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Next")
                }
            }

            3 -> {
                Text("Step 3: Password", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    if (password == confirmPassword && password.length >= 6) {
                        coroutineScope.launch {
                            authViewModel.signupWithDetails(
                                email = email,
                                password = password,
                                fullName = fullName,
                                username = username,
                                accountType = accountType.ifBlank { customAccountType },
                                dob = dob,
                                phone = "" // phone removed
                            )
                        }
                    } else {
                        authViewModel.setError("Password does not match or too short")
                    }
                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Sign Up")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (authState) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> {
                LaunchedEffect(Unit) { onSignupSuccess() }
            }
            is AuthState.Error -> {
                Text(
                    text = (authState as AuthState.Error).message ?: "Error",
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {}
        }
    }
}
