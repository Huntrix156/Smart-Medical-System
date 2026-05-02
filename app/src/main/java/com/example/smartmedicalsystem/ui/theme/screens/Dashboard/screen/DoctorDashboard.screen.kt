package com.example.smartmedical.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDashboard(onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Doctor Dashboard") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Welcome, Dr. Amina Osei", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Cardiology", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Today", "8", Modifier.weight(1f))
                StatCard("Pending", "5", Modifier.weight(1f))
                StatCard("Total", "132", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Today's Appointments", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    AppointmentRow("John Doe", "10:00 AM · Follow-up", "Confirmed")
                    HorizontalDivider()
                    AppointmentRow("Mary Njeri", "11:30 AM · New patient", "Pending")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Write Prescription")
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("View Patient Records")
            }
        }
    }
}




@Composable
fun StatCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    // Set a default color so you don't always have to pass one
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(), // Center content inside the card
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = label, fontSize = 12.sp)
        }
    }
}



@Composable
fun AppointmentRow(name: String, detail: String, status: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Text(detail, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        SuggestionChip(onClick = {}, label = { Text(status, fontSize = 11.sp) })
    }
}