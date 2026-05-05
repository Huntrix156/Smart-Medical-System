package com.example.smartmedicalsystem.ui.screens.scheduler

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen.scheduler.scheduleMedicationReminder

@Composable
fun AddMedicationRoute() {
    val context = LocalContext.current

    AddMedicationScreen { reminder ->
        scheduleMedicationReminder(context, reminder)
    }
}