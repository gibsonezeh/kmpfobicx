import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

// ...

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

                // User data to store
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

                // Save to Firestore
                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(uid)
                    .set(userData)
                    .addOnSuccessListener {
                        _authState.value = AuthState.Success
                    }
                    .addOnFailureListener { e ->
                        _authState.value = AuthState.Error(e.message)
                    }
            } else {
                _authState.value = AuthState.Error(task.exception?.message)
            }
        }
}
