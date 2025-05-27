package com.gibson.fobicx.repository

import com.google.firebase.Timestamp
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

    suspend fun loginWithEmail(email: String, password: String): Result<Unit>{
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    // Sign up with email and password only
    suspend fun signupWithEmail(email: String, password: String): Result<Unit> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Save or update user profile data
    suspend fun saveUserProfile(
        fullName: String,
        username: String,
        accountType: String,
        dob: String,
        phoneNumber: String?
    ): Result<Unit> {
        return try {
            val uid = auth.currentUser?.uid ?: throw Exception("User not logged in")
            val docRef = firestore.collection("users").document(uid)

            val userSnapshot = docRef.get().await()
            val isFirstTime = !userSnapshot.exists()

            val profileData = mutableMapOf<String, Any?>(
                "uid" to uid,
                "email" to auth.currentUser?.email,
                "fullName" to fullName,
                "username" to username,
                "accountType" to accountType,
                "dob" to dob
            )

            phoneNumber?.let {
                profileData["phoneNumber"] = it
            }

            if (isFirstTime) {
                profileData["createdAt"] = Timestamp.now()
            }

            docRef.set(profileData, com.google.firebase.firestore.SetOptions.merge()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Phone verification
    fun verifyPhoneCode(credential: PhoneAuthCredential, onComplete: (Result<Unit>) -> Unit) {
        auth.signInWithCredential(credential)
            .addOnSuccessListener { onComplete(Result.success(Unit)) }
            .addOnFailureListener { onComplete(Result.failure(it)) }
    }

    fun signOut() = auth.signOut()
    fun isUserLoggedIn() = auth.currentUser != null
    fun getCurrentUserEmail() = auth.currentUser?.email
    fun getCurrentUserId() = auth.currentUser?.uid
}
