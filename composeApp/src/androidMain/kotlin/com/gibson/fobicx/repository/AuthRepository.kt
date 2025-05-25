package com.gibson.fobicx.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun signup(
        email: String,
        password: String,
        fullName: String,
        username: String,
        accountType: String,
        dateOfBirth: String
    ): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("User ID not found")

            val userMap = mapOf(
                "uid" to uid,
                "fullName" to fullName,
                "username" to username,
                "email" to email,
                "accountType" to accountType,
                "dateOfBirth" to dateOfBirth
            )

            firestore.collection("users").document(uid).set(userMap).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean = auth.currentUser != null

    fun getCurrentUserEmail(): String? = auth.currentUser?.email
}
