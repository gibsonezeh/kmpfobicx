package com.gibson.fobicx.util

import android.content.Context

object DarkModeManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val DARK_MODE_KEY = "dark_mode"

    fun setDarkMode(context: Context, isEnabled: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(DARK_MODE_KEY, isEnabled).apply()
    }

    fun isDarkMode(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(DARK_MODE_KEY, false)
    }
}
