package com.gibson.fobicx.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.gibson.fobicx.repository.AuthRepository
import kotlinx.coroutines.launch


sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    class Error(val message: String?) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

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
        fullName: String?,
        username: String?,
        accountType: String?,
        dob: String?,
        phone: String?
    ) {
        _authState.value = AuthState.Loading

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid ?: return@addOnCompleteListener
                    val profileData = mutableMapOf<String, Any?>(
                        "uid" to uid,
                        "email" to email,
                        "fullName" to fullName,
                        "username" to username,
                        "accountType" to accountType,
                        "dob" to dob,
                        "phone" to phone
                    )

                    // Only add `createdAt` timestamp if this is the first time
                    profileData["createdAt"] = FieldValue.serverTimestamp()

                    firestore.collection("users")
                        .document(uid)
                        .set(profileData)
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

    fun updateUserProfile(
        fullName: String?,
        username: String?,
        accountType: String?,
        dob: String?,
        phone: String?
    ) {
        val uid = firebaseAuth.currentUser?.uid ?: return
        val updateData = mutableMapOf<String, Any?>()

        fullName?.let { updateData["fullName"] = it }
        username?.let { updateData["username"] = it }
        accountType?.let { updateData["accountType"] = it }
        dob?.let { updateData["dob"] = it }
        phone?.let { updateData["phone"] = it }

        firestore.collection("users")
            .document(uid)
            .update(updateData)
            .addOnSuccessListener {
                _authState.value = AuthState.Success
            }
            .addOnFailureListener {
                _authState.value = AuthState.Error(it.message)
            }
    }

    fun getCurrentUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}
