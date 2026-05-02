package com.example.smartmedical.screens

//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmedicalsystem.ui.theme.screens.screen.StatCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Dashboard") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("System Overview", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Total Users", "248", Modifier.weight(1f))
                StatCard("Doctors", "32", Modifier.weight(1f))
                StatCard("Patients", "214", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Recent Activity", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("New doctor registered — Dr. Kamau", fontSize = 14.sp)
                    HorizontalDivider()
                    Text("System backup completed", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Add New User")
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Generate Report")
            }
        }
    }
}



//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AdminDashboard(
//    userRole: String, // Added this parameter
//    onLogout: () -> Unit
//) {
//    Scaffold(
//        topBar = {
//            aTopAppBar(title = { Text("System Administrator") })
//        }
//    ) { padding ->
//        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
//            Text("Admin Portal", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//
//            // SECURITY CHECK: Only show "Add Doctor" if userRole is exactly "Admin"
//            if (userRole == "Admin") {
//                Spacer(modifier = Modifier.height(20.dp))
//
//                Card(
//                    colors = CardDefaults.cardColors(
//                        containerColor = MaterialTheme.colorScheme.primaryContainer
//                    )
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text("Management", fontWeight = FontWeight.Bold)
//                        Text("Add new clinical staff to the system.")
//                        Spacer(modifier = Modifier.height(10.dp))
//
//                        Button(
//                            onClick = { /* Navigate to your Add Doctor Screen */ },
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Text("Add New Doctor")
//                        }
//                    }
//                }
//            } else {
//                // If somehow a non-admin gets here, they see this:
//                Text("Access Denied. You do not have permission to manage staff.", color = Color.Red)
//            }
//        }
//    }
//}