// AuthViewModel.kt
package com.gibson.fobicx.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    class Error(val message: String?) : AuthState()
}

class AuthViewModel : ViewModel() {

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var currentActivity: Activity? = null

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun setError(message: String) {
        _authState.value = AuthState.Error(message)
    }

    fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    fun logout() {
        firebaseAuth.signOut()
        _authState.value = AuthState.Idle
    }

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
                    val uid = firebaseAuth.currentUser?.uid ?: return@addOnCompleteListener
                    val userData = mapOf(
                        "uid" to uid,
                        "email" to email,
                        "fullName" to fullName,
                        "username" to username,
                        "accountType" to accountType,
                        "dob" to dob,
                        "phone" to phone,
                        "createdAt" to FieldValue.serverTimestamp()
                    )
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(uid)
                        .set(userData)
                        .addOnSuccessListener {
                            _authState.value = AuthState.Success
                        }
                        .addOnFailureListener {
                            _authState.value = AuthState.Error(it.message)
                        }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message)
                }
            }
    }
}
