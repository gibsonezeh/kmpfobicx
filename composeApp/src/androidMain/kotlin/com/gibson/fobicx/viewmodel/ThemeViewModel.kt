package com.gibson.fobicx.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gibson.fobicx.datastore.ThemePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val preferences = ThemePreferences(application)

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    init {
        preferences.isDarkTheme.onEach {
            _isDarkTheme.value = it
        }.launchIn(viewModelScope)
    }

    fun toggleTheme() {
        val newTheme = !_isDarkTheme.value
        viewModelScope.launch {
            preferences.setDarkTheme(newTheme)
        }
    }
}
