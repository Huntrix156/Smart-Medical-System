//package com.example.nexora.ui.theme.screens
//
//import android.app.TimePickerDialog
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Switch
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.example.nexora.data.Reminder.AlarmScheduler
////import com.example.nexora.data.AlarmScheduler
//import com.example.nexora.models.Reminder
//import java.util.Calendar
//
//@Composable
//fun ReminderScreen(navController: NavController) {
//    val context = LocalContext.current
//
//    var medicineName by remember { mutableStateOf("") }
//    var repeatDaily by remember { mutableStateOf(false) }
//    var showConfirmation by remember { mutableStateOf(false) }
//    var selectedTimeMillis by remember { mutableStateOf<Long?>(null) }
//    var selectedTimeLabel by remember { mutableStateOf("No time selected") }
//
//    // Time Picker Dialog
//    val calendar = Calendar.getInstance()
//    val timePickerDialog = TimePickerDialog(
//        context,
//        { _, hour, minute ->
//            calendar.set(Calendar.HOUR_OF_DAY, hour)
//            calendar.set(Calendar.MINUTE, minute)
//            calendar.set(Calendar.SECOND, 0)
//            calendar.set(Calendar.MILLISECOND, 0)
//
//            // If time has already passed today, schedule for tomorrow
//            if (calendar.timeInMillis <= System.currentTimeMillis()) {
//                calendar.add(Calendar.DAY_OF_YEAR, 1)
//            }
//
//            selectedTimeMillis = calendar.timeInMillis
//            selectedTimeLabel = String.format("%02d:%02d", hour, minute)
//        },
//        calendar.get(Calendar.HOUR_OF_DAY),
//        calendar.get(Calendar.MINUTE),
//        true // 24-hour format
//    )
//
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            modifier = Modifier.padding(24.dp)
//        ) {
//
//            Text("Set Medicine Reminder", style = MaterialTheme.typography.headlineSmall)
//
//            // Medicine name input
//            OutlinedTextField(
//                value = medicineName,
//                onValueChange = { medicineName = it },
//                label = { Text("Medicine Name") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            // Time picker button
//            OutlinedButton(
//                onClick = { timePickerDialog.show() },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("⏰ Pick Time: $selectedTimeLabel")
//            }
//
//            // Repeat daily toggle
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Repeat Daily", modifier = Modifier.weight(1f))
//                Switch(
//                    checked = repeatDaily,
//                    onCheckedChange = { repeatDaily = it }
//                )
//            }
//
//            // Set Reminder button
//            Button(
//                onClick = {
//                    if (medicineName.isNotBlank() && selectedTimeMillis != null) {
//                        val reminder = Reminder(
//                            id = System.currentTimeMillis().toInt(),
//                            medicineName = medicineName,
//                            timeInMillis = selectedTimeMillis!!,
//                            repeatDaily = repeatDaily
//                        )
//                        AlarmScheduler.setAlarm(context, reminder)
//                        showConfirmation = true
//                    }
//                },
//                modifier = Modifier.fillMaxWidth(),
//                enabled = medicineName.isNotBlank() && selectedTimeMillis != null
//            ) {
//                Text("Set Medicine Reminder")
//            }
//
//            // Validation hint
//            if (medicineName.isBlank() || selectedTimeMillis == null) {
//                Text(
//                    text = "⚠️ Please enter medicine name and pick a time",
//                    color = MaterialTheme.colorScheme.error,
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//
//            // Confirmation message
//            if (showConfirmation) {
//                Text(
//                    text = "✅ Reminder set for $medicineName at $selectedTimeLabel!",
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
//        }
//    }
//}


package com.example.nexora.ui.theme.screens.Reminder.screen

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nexora.data.Reminder.AlarmScheduler
import com.example.smartmedicalsystem.models.Reminder
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderScreen(navController: NavController) {
    val context = LocalContext.current

    var medicineName by remember { mutableStateOf("") }
    var repeatDaily by remember { mutableStateOf(false) }
    var showConfirmation by remember { mutableStateOf(false) }
    var selectedTimeMillis by remember { mutableStateOf<Long?>(null) }
    var selectedTimeLabel by remember { mutableStateOf("No time selected") }

    // List to show scheduled reminders in the session
    val scheduledReminders = remember { mutableStateListOf<String>() }

    // Time Picker Dialog
    val calendar = Calendar.getInstance()
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1)
            }

            selectedTimeMillis = calendar.timeInMillis
            selectedTimeLabel = String.format("%02d:%02d", hour, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Medicine Reminders") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // ── Input Card ──────────────────────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("New Reminder", fontWeight = FontWeight.Bold)

                    // Medicine name input
                    OutlinedTextField(
                        value = medicineName,
                        onValueChange = {
                            medicineName = it
                            showConfirmation = false
                        },
                        label = { Text("Medicine Name") },
                        placeholder = { Text("e.g. Paracetamol") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Time picker button
                    OutlinedButton(
                        onClick = { timePickerDialog.show() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("⏰ Pick Time: $selectedTimeLabel")
                    }

                    // Repeat daily toggle
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Repeat Daily")
                            Text(
                                text = if (repeatDaily) "Fires every day at $selectedTimeLabel"
                                else "Fires once",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = repeatDaily,
                            onCheckedChange = { repeatDaily = it }
                        )
                    }
                }
            }

            // ── Validation hint ─────────────────────────────────
            if (medicineName.isBlank() || selectedTimeMillis == null) {
                Text(
                    text = "⚠️ Please enter medicine name and pick a time",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // ── Set Reminder button ─────────────────────────────
            Button(
                onClick = {
                    if (medicineName.isNotBlank() && selectedTimeMillis != null) {
                        val reminder = Reminder(
                            id = System.currentTimeMillis().toInt(),
                            medicineName = medicineName,
                            timeInMillis = selectedTimeMillis!!,
                            repeatDaily = repeatDaily
                        )
                        AlarmScheduler.setAlarm(context, reminder)
                        scheduledReminders.add(
                            "$medicineName at $selectedTimeLabel ${if (repeatDaily) "(Daily)" else "(Once)"}"
                        )
                        showConfirmation = true
                        medicineName = ""
                        selectedTimeMillis = null
                        selectedTimeLabel = "No time selected"
                        repeatDaily = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = medicineName.isNotBlank() && selectedTimeMillis != null
            ) {
                Text("✅ Set Medicine Reminder")
            }

            // ── Confirmation message ────────────────────────────
            if (showConfirmation) {
                Text(
                    text = "✅ Reminder set successfully!",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            // ── Scheduled reminders list ────────────────────────
            if (scheduledReminders.isNotEmpty()) {
                Text(
                    text = "Scheduled Reminders",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                scheduledReminders.forEachIndexed { index, item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "💊 $item",
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}