//package com.example.smartmedicalsystem.ui.theme.screens.scheduler
//
////package com.example.smartmedicalsystem.scheduler
//
//import android.content.Context
//import androidx.work.*
//import com.example.smartmedicalsystem.data.MedicationReminder
//import com.example.smartmedicalsystem.ui.theme.screens.screen.MedicationWorker
//import java.util.concurrent.TimeUnit
//
//fun scheduleMedicationReminder(context: Context, reminder: MedicationReminder) {
//    reminder.times.forEach { time ->
//
//        val (hour, minute) = time.split(":").map { it.toInt() }
//
//        val delay = calculateInitialDelay(hour, minute)
//
//        val data = workDataOf(
//            "name" to reminder.name,
//            "dosage" to reminder.dosage
//        )
//
//        val request = PeriodicWorkRequestBuilder<MedicationWorker>(1, TimeUnit.DAYS)
//            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
//            .setInputData(data)
//            .build()
//
//        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//            reminder.id + time,
//            ExistingPeriodicWorkPolicy.REPLACE,
//            request
//        )
//    }
//}
//
//
//
//
//
//fun calculateInitialDelay(hour: Int, minute: Int): Long {
//    val now = java.util.Calendar.getInstance()
//    val target = java.util.Calendar.getInstance().apply {
//        set(java.util.Calendar.HOUR_OF_DAY, hour)
//        set(java.util.Calendar.MINUTE, minute)
//        set(java.util.Calendar.SECOND, 0)
//    }
//
//    if (target.before(now)) {
//        target.add(java.util.Calendar.DAY_OF_YEAR, 1)
//    }
//
//    return target.timeInMillis - now.timeInMillis
//}

//
//package com.example.smartmedicalsystem.ui.theme.screens.scheduler
//
//import android.content.Context
//import androidx.work.*
//import com.example.smartmedicalsystem.data.MedicationReminder
//import com.example.smartmedicalsystem.ui.theme.screens.screen.MedicationWorker
//import java.util.concurrent.TimeUnit
//import java.util.Calendar
//
//fun scheduleMedicationReminder(context: Context, reminder: MedicationReminder) {
//    reminder.times.forEach { time ->
//
//        val parts = time.split(":")
//        if (parts.size < 2) return@forEach // Safety check for malformed time strings
//
//        val hour = parts[0].toInt()
//        val minute = parts[1].toInt()
//
//        val delay = calculateInitialDelay(hour, minute)
//
//        val data = workDataOf(
//            "name" to reminder.name,
//            "dosage" to reminder.dosage
//        )
//
//        // FIX: Explicitly call PeriodicWorkRequest.Builder for better compatibility
//        val request = PeriodicWorkRequest.Builder(
//            MedicationWorker::class.java,
//            1, TimeUnit.DAYS
//        )
//            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
//            .setInputData(data)
//            .build()
//
//        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//            "${reminder.name}_${time}", // Using a clean unique ID
//            ExistingPeriodicWorkPolicy.UPDATE, // UPDATE is usually safer than REPLACE in newer versions
//            request
//        )
//    }
//}
//
//fun calculateInitialDelay(hour: Int, minute: Int): Long {
//    val now = Calendar.getInstance()
//    val target = Calendar.getInstance().apply {
//        set(Calendar.HOUR_OF_DAY, hour)
//        set(Calendar.MINUTE, minute)
//        set(Calendar.SECOND, 0)
//        set(Calendar.MILLISECOND, 0)
//    }
//
//    if (target.before(now)) {
//        target.add(Calendar.DAY_OF_YEAR, 1)
//    }
//
//    return target.timeInMillis - now.timeInMillis
//}


package com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen.scheduler

import android.content.Context
import androidx.work.*
import com.example.smartmedicalsystem.data.MedicationReminder
import com.example.smartmedicalsystem.ui.theme.screens.screens.MedicationWorker
import java.util.concurrent.TimeUnit
import java.util.Calendar

fun scheduleMedicationReminder(context: Context, reminder: MedicationReminder) {
    reminder.times.forEach { time ->

        val parts = time.split(":")
        if (parts.size < 2) return@forEach

        val hour = parts[0].toInt()
        val minute = parts[1].toInt()
        val delay = calculateInitialDelay(hour, minute)

        val data = workDataOf(
            "name" to reminder.name,
            "dosage" to reminder.dosage
        )

        // Using the most explicit constructor to satisfy the compiler candidates
        val request = PeriodicWorkRequest.Builder(
            MedicationWorker::class.java, // Explicit Class reference
            1L,                           // Explicitly a Long
            TimeUnit.DAYS                 // Explicit TimeUnit
        )
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "${reminder.name}_${time}",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }
}

fun calculateInitialDelay(hour: Int, minute: Int): Long {
    val now = Calendar.getInstance()
    val target = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    if (target.before(now)) {
        target.add(Calendar.DAY_OF_YEAR, 1)
    }
    return target.timeInMillis - now.timeInMillis
}