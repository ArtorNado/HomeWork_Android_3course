package com.example.homeworks.preference

import android.content.Context

class ThemeSharedPreference(
    context: Context
) {

    companion object {
        private const val SHARED_PREFERENCE_NAME_APP_THEME = "App_theme"
        private const val KEY_THEME = "theme"
        private const val DEFAULT_THEME_ID = 1
    }

    private val sharedPreference =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME_APP_THEME, Context.MODE_PRIVATE)

    var appTheme: Int
        get() {
            return sharedPreference.getInt(
                KEY_THEME,
                DEFAULT_THEME_ID
            )
        }
        set(value) {
            sharedPreference.edit().putInt(KEY_THEME, value).apply()
        }
}