package com.example.smartmedicalsystem.data

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import java.time.LocalTime

class DashboardViewModel : ViewModel() {

    private val _username = mutableStateOf("")
    val username: State<String> = _username

    private val _role = mutableStateOf("")
    val role: State<String> = _role

    private val _greeting = mutableStateOf("")
    val greeting: State<String> = _greeting

    fun setUser(name: String, userRole: String) {
        _username.value = name
        _role.value = userRole
        _greeting.value = generateGreeting()
    }

    private fun generateGreeting(): String {
        val hour = LocalTime.now().hour

        return when (hour) {
            in 5..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            in 17..20 -> "Good Evening"
            else -> "Hello"
        }
    }
}