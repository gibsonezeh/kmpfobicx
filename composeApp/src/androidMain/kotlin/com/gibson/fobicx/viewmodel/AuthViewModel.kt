package com.gibson.fobicx.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gibson.fobicx.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authRepo = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepo.login(email, password)
            _authState.value = if (result.isSuccess) AuthState.Success else AuthState.Error(result.exceptionOrNull()?.message)
        }
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepo.signup(email, password)
            _authState.value = if (result.isSuccess) AuthState.Success else AuthState.Error(result.exceptionOrNull()?.message)
        }
    }

    fun logout() {
        authRepo.logout()
    }

    fun isLoggedIn() = authRepo.isUserLoggedIn()
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String?) : AuthState()
}
