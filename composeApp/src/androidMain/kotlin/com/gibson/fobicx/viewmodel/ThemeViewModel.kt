package com.gibson.fobicx.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    init {
        loadUserTheme()
    }

    private fun loadUserTheme() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("users").document(uid).get().addOnSuccessListener { doc ->
            val theme = doc.getString("theme") ?: "light"
            _isDarkTheme.value = theme == "dark"
        }
    }

    fun toggleTheme() {
        val uid = auth.currentUser?.uid ?: return
        val newTheme = if (_isDarkTheme.value) "light" else "dark"
        viewModelScope.launch {
            db.collection("users").document(uid).update("theme", newTheme)
                .addOnSuccessListener {
                    _isDarkTheme.value = (newTheme == "dark")
                }
        }
    }
}
