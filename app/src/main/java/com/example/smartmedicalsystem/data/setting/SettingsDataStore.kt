package com.example.smartmedicalsystem.data.setting
//
//import android.content.Context
//import androidx.datastore.preferences.core.booleanPreferencesKey
//
//object SettingsDataStore {  // ✅ correct as object because:
//
//    // 1. Just holds constant keys — no constructor needed
//    val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
//    val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")
//
//    // 2. Just utility functions — no mutable state
//    fun getNotifications(context: Context): Flow<Boolean> = ...
//    fun getDarkMode(context: Context): Flow<Boolean> = ...
//
//    // 3. Only one instance ever needed app-wide
//    suspend fun setNotifications(context: Context, enabled: Boolean) { ... }
//    suspend fun setDarkMode(context: Context, enabled: Boolean) { ... }
//}




import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// ✅ This must be outside the object — top level extension property
val Context.dataStore by preferencesDataStore(name = "settings")

object SettingsDataStore {

    val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
    val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")

    // ✅ Fill in the actual implementation (was ... before)
    fun getNotifications(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[NOTIFICATIONS_KEY] ?: true }

    // ✅ Fill in the actual implementation (was ... before)
    fun getDarkMode(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[DARK_MODE_KEY] ?: false }

    // ✅ Fill in the actual implementation (was ... before)
    suspend fun setNotifications(context: Context, enabled: Boolean) {
        context.dataStore.edit { it[NOTIFICATIONS_KEY] = enabled }
    }

    // ✅ Fill in the actual implementation (was ... before)
    suspend fun setDarkMode(context: Context, enabled: Boolean) {
        context.dataStore.edit { it[DARK_MODE_KEY] = enabled }
    }
}