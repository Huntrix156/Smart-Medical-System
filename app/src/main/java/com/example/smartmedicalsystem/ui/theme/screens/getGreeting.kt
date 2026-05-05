package com.example.smartmedicalsystem.ui.theme.screens


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
fun getGreeting(): String {
    val hour = LocalTime.now().hour

    return when (hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Hello"
    }
}