package com.gibson.fobicx.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    class Error(val message: String?) : AuthState()
}

class AuthViewModel : ViewModel() {

    // Firebase Auth instance
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Activity reference if needed later
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
        _authState.value = AuthState.Idle
    }

    // Login with email and password
    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error(task.exception?.message)
                }
            }
    }

    // Simulated username check — replace with real logic
    fun checkUsernameExists(username: String, callback: (Boolean) -> Unit) {
        callback(false)
    }

    // Signup with full details (phone not verified)
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
                    // You can save phone and other info to Firestore here
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error(task.exception?.message)
                }
            }
    }
}
