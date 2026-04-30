package com.example.smartmedicalsystem.models

import java.time.LocalDate

data class Medicine(
    val name: String,
    val stock: Int,
    val minStock: Int,
    val expiryDate: LocalDate
)
