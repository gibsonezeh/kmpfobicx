package com.gibson.fobicx.ui.screens.auth

import android.app.Activity
import android.util.Log 
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.* 
import androidx.compose.runtime.* 
import androidx.compose.ui.Modifier 
import androidx.compose.ui.platform.LocalContext 
import androidx.compose.ui.unit.dp 
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth 
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions 
import com.google.firebase.auth.PhoneAuthProvider 
import kotlinx.coroutines.tasks.await 
import java.util.concurrent.TimeUnit

@Composable fun PhoneNumberVerificationScreen(
    onVerificationSuccess: () -> Unit,
    onSkip: () -> Unit ) {
  val context = LocalContext.current 
  val activity = context as Activity 
  val auth = FirebaseAuth.getInstance()

var phoneNumber by remember { mutableStateOf("") }
var verificationId by remember { mutableStateOf<String?>(null) }
var code by remember { mutableStateOf("") }
var isVerifying by remember { mutableStateOf(false) }
var message by remember { mutableStateOf("") }

Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(24.dp),
    verticalArrangement = Arrangement.Center
) {
    Text("Verify Phone Number", style = MaterialTheme.typography.headlineMedium)
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        value = phoneNumber,
        onValueChange = { phoneNumber = it },
        label = { Text("Phone Number (+1234567890)") },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (verificationId == null) {
        Button(onClick = {
            isVerifying = true
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        message = "Verification completed."
                        isVerifying = false
                        auth.currentUser?.updatePhoneNumber(credential)?.addOnSuccessListener {
                            onVerificationSuccess()
                        }
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        message = "Verification failed: ${e.localizedMessage}"
                        isVerifying = false
                    }

                    override fun onCodeSent(
                        verifId: String,
                        token: PhoneAuthProvider.ForceResendingToken
                    ) {
                        verificationId = verifId
                        message = "Code sent. Enter the code below."
                        isVerifying = false
                    }
                })
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }) {
            Text("Send Code")
        }
    } else {
        OutlinedTextField(
            value = code,
            onValueChange = { code = it },
            label = { Text("Enter OTP") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
            auth.currentUser?.updatePhoneNumber(credential)?.addOnSuccessListener {
                message = "Phone number verified!"
                onVerificationSuccess()
            }?.addOnFailureListener {
                message = "Invalid code or failed to verify."
            }
        }) {
            Text("Verify")
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    TextButton(onClick = onSkip) {
        Text("Skip")
    }

    if (isVerifying) CircularProgressIndicator() else Text(message)
}

}

