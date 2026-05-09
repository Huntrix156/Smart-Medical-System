package com.example.smartmedicalsystem.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")   // ✅ add this
data class Reminder(
    @PrimaryKey(autoGenerate = true) // ✅ add this
    val id: Int = 0,
    val medicineName: String,
    val timeInMillis: Long,
    val repeatDaily: Boolean
)




