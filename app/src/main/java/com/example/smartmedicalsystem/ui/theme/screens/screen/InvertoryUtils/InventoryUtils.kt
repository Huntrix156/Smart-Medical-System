package com.example.smartmedicalsystem.ui.theme.screens.screen.InvertoryUtils

//package com.yourapp.pharmacy.utils

import androidx.compose.ui.graphics.Color
import java.time.temporal.ChronoUnit
import java.time.LocalDate

fun stockStatusColor(stock: Int, minStock: Int): Color {
    return when {
        stock == 0 -> Color.Red
        stock <= minStock -> Color(0xFFFFA000)
        else -> Color(0xFF2E7D32)
    }
}

fun expiryStatusColor(expiryDate: LocalDate): Color {
    val daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate)

    return when {
        daysLeft < 0 -> Color.Red
        daysLeft <= 30 -> Color.Red
        daysLeft <= 90 -> Color(0xFFFFA000)
        else -> Color(0xFF2E7D32)
    }
}

fun daysRemaining(expiryDate: LocalDate): Long {
    return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate)
}