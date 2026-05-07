package com.example.smartmedicalsystem.ui.theme.screens

import androidx.benchmark.traceprocessor.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartmedicalsystem.models.Appointment

@Composable
fun UpcomingAppointmentsListScreen() {

    // Sample data (later you will replace with Firebase or database)
    val appointments = remember {
        mutableStateListOf(
            Appointment("Dr. Kamau", "12/05/2026", "10:00 AM", "Checkup"),
            Appointment("Nairobi Hospital", "15/05/2026", "2:30 PM", "Lab Results"),
            Appointment("Dr. Amina", "20/05/2026", "9:00 AM", "Follow-up")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Upcoming Appointments",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
//            items(appointments) { appointment ->
//                AppointmentCard(
//                    appointment = appointment,
//                    onDelete = {
//                        appointments.remove(appointment)
//                    }
//                )
            }
        }
    }
//}



/* ---------------- CARD UI ---------------- */

@Composable
fun AppointmentCard(appointment: Appointment) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE3F2FD)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(
                text = "Doctor/Hospital: ${appointment.doctor}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = "Date: ${appointment.date}")
            Text(text = "Time: ${appointment.time}")
            Text(text = "Reason: ${appointment.reason}")

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(onClick = { /* EDIT LOGIC */ }) {
                    Text("Edit")
                }

                Button(
                    onClick = { /* CANCEL LOGIC */ },
                    colors = ButtonDefaults.buttonColors(Color.Red)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}