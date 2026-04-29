package com.example.smartmedical.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartmedicalsystem.navigation.ROUTE_ADD_MEDICATION
import com.example.smartmedicalsystem.ui.theme.screens.screen.StatCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDashboard(navController: NavController,onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Patient Dashboard") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Welcome, John Doe", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Upcoming", "2", Modifier.weight(1f))
                StatCard("Past Visits", "14", Modifier.weight(1f))
                StatCard("Prescriptions", "3", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Next Appointment", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Dr. Amina Osei — Cardiology", fontWeight = FontWeight.Medium)
                    Text("Mon, 5 May 2025 · 10:00 AM",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Book Appointment")
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("View My Records")
            }
            OutlinedButton(onClick ={ navController.navigate(ROUTE_ADD_MEDICATION)},
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Medicine")
            }
        }
    }
}