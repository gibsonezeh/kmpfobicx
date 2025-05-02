package com.gibson.fobicx.ui

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

// Extension property for DataStore
val Context.dataStore by preferencesDataStore(name = "settings")

object ThemePreferenceManager {
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

    // Save dark mode preference
    suspend fun setDarkMode(context: Context, isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isEnabled
        }
    }

    // Read dark mode preference
    suspend fun isDarkMode(context: Context): Boolean {
        return context.dataStore.data
            .map { preferences -> preferences[DARK_MODE_KEY] ?: false }
            .first()
    }
}
