package com.example.smartmedicalsystem.models
//
//data class Reminder(
//    val id: Int,
//    val medicineName: String,
//    val timeInMillis: Long,
//    val repeatDaily: Boolean
//)





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




