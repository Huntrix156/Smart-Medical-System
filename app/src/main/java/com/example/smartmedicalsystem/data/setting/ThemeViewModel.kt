package com.example.smartmedicalsystem.data.setting

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

// ThemeViewModel.kt
class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val _darkThemeEnabled = mutableStateOf(
        ThemePreferenceManager.isDarkMode(application)
    )
//    val darkThemeEnabled: State<Boolean> = _darkThemeEnabled

    fun toggleDarkMode(enabled: Boolean) {
        _darkThemeEnabled.value = enabled
        ThemePreferenceManager.setDarkMode(getApplication(), enabled)
    }
}