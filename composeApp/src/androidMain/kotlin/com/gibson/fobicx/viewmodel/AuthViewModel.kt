package com.gibson.fobicx.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gibson.fobicx.repository.AuthRepository
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

    private val repository = AuthRepository()
    var currentActivity: Activity? = null

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun setError(message: String) {
        _authState.value = AuthState.Error(message)
    }

    fun isLoggedIn(): Boolean = repository.isUserLoggedIn()

    fun logout() {
        repository.signOut()
        _authState.value = AuthState.Idle
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.loginWithEmail(email, password)
            _authState.value = if (result.isSuccess) AuthState.Success else AuthState.Error(result.exceptionOrNull()?.message)
        }
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.signupWithEmail(email, password)
            _authState.value = if (result.isSuccess) AuthState.Success else AuthState.Error(result.exceptionOrNull()?.message)
        }
    }

    fun saveProfile(
        fullName: String,
        username: String,
        accountType: String,
        dob: String,
        phone: String?
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.saveUserProfile(fullName, username, accountType, dob, phone)
            _authState.value = if (result.isSuccess) AuthState.Success else AuthState.Error(result.exceptionOrNull()?.message)
        }
    }
}
