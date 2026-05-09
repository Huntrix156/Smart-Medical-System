package com.example.smartmedicalsystem.data.setting




import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val _darkThemeEnabled = mutableStateOf(
        ThemePreferenceManager.isDarkMode(application)
    )

    // ✅ FIXED: was commented out — now exposed so UI can observe theme changes
    val darkThemeEnabled: State<Boolean> = _darkThemeEnabled

    fun toggleDarkMode(enabled: Boolean) {
        _darkThemeEnabled.value = enabled
        ThemePreferenceManager.setDarkMode(getApplication(), enabled)
    }
}

