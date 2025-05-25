package com.gibson.fobicx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gibson.fobicx.repository.AuthRepository
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String?) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun checkUsernameAvailability(username: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val available = repository.isUsernameAvailable(username)
            onResult(available)
        }
    }

    fun signupWithEmail(
        email: String,
        password: String,
        fullName: String,
        username: String,
        accountType: String,
        dob: String,
        phoneNumber: String
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.signupWithEmail(email, password, fullName, username, accountType, dob, phoneNumber)
            _authState.value = if (result.isSuccess) AuthState.Success else AuthState.Error(result.exceptionOrNull()?.message)
        }
    }

    fun verifyPhoneCode(credential: PhoneAuthCredential) {
        _authState.value = AuthState.Loading
        repository.verifyPhoneCode(credential) { result ->
            _authState.value = if (result.isSuccess) AuthState.Success else AuthState.Error(result.exceptionOrNull()?.message)
        }
    }

    fun logout() {
        repository.signOut()
        _authState.value = AuthState.Idle
    }

    fun isUserLoggedIn(): Boolean = repository.isUserLoggedIn()
}
