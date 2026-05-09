package com.example.smartmedicalsystem.data

import java.util.UUID

data class MedicationReminder(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val dosage: String,
    val times: List<String>,
    val frequency: String,
    val startDate: Long,
    val endDate: Long?,
    val isActive: Boolean = true
)