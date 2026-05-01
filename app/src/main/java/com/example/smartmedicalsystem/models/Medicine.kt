package com.example.smartmedicalsystem.models

import java.time.LocalDate

data class Medicine(
    val name: String,
    val stock: Int = 0,
    val minStock: Int = 0,
    val expiryDate: LocalDate,
    val quantity: Int,
)
