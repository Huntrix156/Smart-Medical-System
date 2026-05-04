//package com.example.nexora.data
package com.example.nexora.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmedicalsystem.data.setting.SettingsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    val notificationsEnabled = SettingsDataStore.getNotifications(context)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), true)

    val darkModeEnabled = SettingsDataStore.getDarkMode(context)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun setNotifications(enabled: Boolean) {
        viewModelScope.launch {
            SettingsDataStore.setNotifications(context, enabled)
        }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            SettingsDataStore.setDarkMode(context, enabled)
        }
    }
}