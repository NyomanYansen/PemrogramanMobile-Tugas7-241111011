package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "SiakadSession"
        private const val KEY_IS_LOGIN = "isLogin"
        private const val KEY_USERNAME = "username"
    }

    fun saveLoginSession(username: String) {
        prefs.edit().apply {
            putBoolean(KEY_IS_LOGIN, true)
            putString(KEY_USERNAME, username)
            apply()
        }
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_IS_LOGIN, false)

    fun getUsername(): String = prefs.getString(KEY_USERNAME, "-") ?: "-"

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
