//package com.example.smartmedicalsystem.ui.theme.screens.scheduler
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.smartmedicalsystem.data.MedicationReminder
//
//@Composable
//fun AddMedicationScreen(onSave: (MedicationReminder) -> Unit) {
//
//    var name by remember { mutableStateOf("") }
//    var dosage by remember { mutableStateOf("") }
//    var time by remember { mutableStateOf("08:00") }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Medication Name") }
//        )
//
//        OutlinedTextField(
//            value = dosage,
//            onValueChange = { dosage = it },
//            label = { Text("Dosage") }
//        )
//
//        OutlinedTextField(
//            value = time,
//            onValueChange = { time = it },
//            label = { Text("Time (HH:mm)") }
//        )
//
//        Button(onClick = {
//            val reminder = MedicationReminder(
//                name = name,
//                dosage = dosage,
//                times = listOf(time),
//                frequency = "DAILY",
//                startDate = System.currentTimeMillis()
//            )
//            onSave(reminder)
//        }) {
//            Text("Save Reminder")
//        }
//    }
//}

package com.example.smartmedicalsystem.ui.screens.scheduler
//package com.example.smartmedicalsystem.ui.screens.scheduler

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartmedicalsystem.data.MedicationReminder
//import com.example.smartmedicalsystem.data.model.MedicationReminder

@Composable
fun AddMedicationScreen(onSave: (MedicationReminder) -> Unit) {

    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("08:00") }

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Medication Name") }
        )

        OutlinedTextField(
            value = dosage,
            onValueChange = { dosage = it },
            label = { Text("Dosage") }
        )

        OutlinedTextField(
            value = time,
            onValueChange = { time = it },
            label = { Text("Time (HH:mm)") }
        )

        Button(
            onClick = {
                val reminder = MedicationReminder(
                    name = name,
                    dosage = dosage,
                    times = listOf(time),
                    frequency = "DAILY",
                    startDate = System.currentTimeMillis(),
                    endDate = System.currentTimeMillis() + 604800000 // Example: 7 days from now
                )
                onSave(reminder)
            },
            enabled = name.isNotBlank() && dosage.isNotBlank()
        ) {
            Text("Save Reminder")
        }
    }
}

