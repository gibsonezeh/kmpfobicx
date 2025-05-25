package com.gibson.fobicx.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    class Error(val message: String?) : AuthState()
}

class AuthViewModel : ViewModel() {

    // Firebase Auth instance
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Activity reference for phone auth
    var currentActivity: Activity? = null

    // Auth state flow
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    // Set error state
    fun setError(message: String) {
        _authState.value = AuthState.Error(message)
    }

    fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun logout(){
        firebaseAuth.signOut()
    }

    // Simulated username check — replace with real logic
    fun checkUsernameExists(username: String, callback: (Boolean) -> Unit) {
        // Simulate username not existing
        callback(false)
    }

    // Phone credential sign-in
    fun signInWithPhoneCredential(credential: PhoneAuthCredential, callback: (Boolean) -> Unit) {
        _authState.value = AuthState.Loading
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success
                    callback(true)
                } else {
                    _authState.value = AuthState.Error(task.exception?.message)
                    callback(false)
                }
            }
    }

    // Sign up with full user details
    fun signupWithDetails(
        email: String,
        password: String,
        fullName: String,
        username: String,
        accountType: String,
        dob: String,
        phone: String
    ) {
        _authState.value = AuthState.Loading
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Simulate success — add Firestore saving if needed
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error(task.exception?.message)
                }
            }
    }
}
