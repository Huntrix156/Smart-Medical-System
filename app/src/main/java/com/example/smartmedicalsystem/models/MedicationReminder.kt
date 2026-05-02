package com.example.smartmedicalsystem.model


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.smartmedicalsystem.data.MedicationReminder
import com.example.smartmedicalsystem.ui.theme.screens.scheduler.scheduleMedicationReminder

@Composable
fun MedicationReminderComponent() {
    val context = LocalContext.current // Get the context here
    val reminder = MedicationReminder(
        name = "Paracetamol",
        dosage = "500mg",
        times = listOf("08:00", "14:00"),
        frequency = "DAILY",
        startDate = System.currentTimeMillis(),
        endDate = System.currentTimeMillis() + 604800000 // Example: 7 days from now
    )

    scheduleMedicationReminder(context, reminder)
}
