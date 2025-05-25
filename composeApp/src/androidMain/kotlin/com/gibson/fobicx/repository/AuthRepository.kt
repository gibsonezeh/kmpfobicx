package com.gibson.fobicx.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun isUsernameAvailable(username: String): Boolean {
        val result = firestore.collection("users")
            .whereEqualTo("username", username)
            .get().await()
        return result.isEmpty
    }

    suspend fun signupWithEmail(
        email: String,
        password: String,
        fullName: String,
        username: String,
        accountType: String,
        dob: String,
        phoneNumber: String
    ): Result<Unit> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: throw Exception("User ID not found")

            val userData = hashMapOf(
                "uid" to uid,
                "email" to email,
                "fullName" to fullName,
                "username" to username,
                "accountType" to accountType,
                "dob" to dob,
                "phoneNumber" to phoneNumber
            )

            firestore.collection("users").document(uid).set(userData).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun verifyPhoneCode(credential: PhoneAuthCredential, onComplete: (Result<Unit>) -> Unit) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener { onComplete(Result.success(Unit)) }
            .addOnFailureListener { onComplete(Result.failure(it)) }
    }

    fun signOut() = auth.signOut()
    fun isUserLoggedIn() = auth.currentUser != null
    fun getCurrentUserEmail() = auth.currentUser?.email
}
