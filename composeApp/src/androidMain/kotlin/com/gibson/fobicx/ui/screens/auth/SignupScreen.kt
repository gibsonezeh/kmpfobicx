package com.gibson.fobicx.ui.screens.auth

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gibson.fobicx.viewmodel.AuthState
import com.gibson.fobicx.viewmodel.AuthViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    authViewModel: AuthViewModel = viewModel(),
    onSignupSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val accountTypes = listOf("Fabricator", "Retailer", "Freelancer", "Customer")

    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var dob by remember { mutableStateOf("") }
    var accountType by remember { mutableStateOf(accountTypes.first()) }
    val context = LocalContext.current

    val authState by authViewModel.authState.collectAsState()

    // Date picker
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            dob = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign Up", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(icon, contentDescription = null)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = dob,
            onValueChange = {},
            label = { Text("Date of Birth") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { datePickerDialog.show() },
            readOnly = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = accountType,
                onValueChange = {},
                readOnly = true,
                label = { Text("Account Type") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
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

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                authViewModel.signupWithDetails(
                    email = email,
                    password = password,
                    fullName = fullName,
                    username = username,
                    accountType = accountType,
                    dob = dob,
                    phone = ""
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Log In")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (authState) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> LaunchedEffect(Unit) { onSignupSuccess() }
            is AuthState.Error -> Text(
                text = (authState as AuthState.Error).message ?: "Signup failed",
                color = MaterialTheme.colorScheme.error
            )
            else -> {}
        }
    }
}
