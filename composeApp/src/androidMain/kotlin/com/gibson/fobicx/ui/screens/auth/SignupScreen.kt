// SignupScreen.kt 
package com.gibson.fobicx.screens.auth

import androidx.compose.foundation.layout.* 
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController 
import com.gibson.fobicx.navigation.Screen
import com.gibson.fobicx.viewmodel.AuthState 
import com.gibson.fobicx.viewmodel.AuthViewModel

 @OptIn(ExperimentalMaterial3Api::class)

@Composable fun SignupScreen(navController: NavController, authViewModel: AuthViewModel) { var fullName by remember { mutableStateOf("") } var username by remember { mutableStateOf("") } var email by remember { mutableStateOf("") } var password by remember { mutableStateOf("") } var accountType by remember { mutableStateOf("") } var dob by remember { mutableStateOf("") } var phone by remember { mutableStateOf("") }

val authState by authViewModel.authState.collectAsState()

Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text("Sign Up", style = MaterialTheme.typography.headlineMedium)

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") })
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation()
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(value = accountType, onValueChange = { accountType = it }, label = { Text("Account Type") })
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(value = dob, onValueChange = { dob = it }, label = { Text("Date of Birth") })
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number") })

    Spacer(modifier = Modifier.height(16.dp))
    Button(onClick = {
        authViewModel.signupWithDetails(
            email = email,
            password = password,
            fullName = fullName,
            username = username,
            accountType = accountType,
            dob = dob,
            phone = phone
        )
    }) {
        Text("Sign Up")
    }

    Spacer(modifier = Modifier.height(8.dp))
    TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
        Text("Already have an account? Log in")
    }

    when (authState) {
        is AuthState.Loading -> CircularProgressIndicator()
        is AuthState.Error -> Text((authState as AuthState.Error).message ?: "Signup failed")
        is AuthState.Success -> {
            Text("Signup successful")
            // Navigate to Home or other screen
        }
        else -> {}
    }
}

}

