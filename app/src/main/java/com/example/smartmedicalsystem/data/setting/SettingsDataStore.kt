package com.example.smartmedicalsystem.data.setting


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

object SettingsDataStore {

    val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
    val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")

    fun getNotifications(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[NOTIFICATIONS_KEY] ?: true }

    fun getDarkMode(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[DARK_MODE_KEY] ?: false }

    suspend fun setNotifications(context: Context, enabled: Boolean) {
        context.dataStore.edit { it[NOTIFICATIONS_KEY] = enabled }
    }

    suspend fun setDarkMode(context: Context, enabled: Boolean) {
        context.dataStore.edit { it[DARK_MODE_KEY] = enabled }
    }
}