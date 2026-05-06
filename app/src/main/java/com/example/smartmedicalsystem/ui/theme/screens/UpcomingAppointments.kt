package com.example.smartmedicalsystem.ui.theme.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartmedicalsystem.models.Appointment
import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingAppointmentsScreen(navController: NavController) {
    val selectedItem = remember { mutableIntStateOf(0) }
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route
    var appointmentDate by remember { mutableStateOf("") }
    var appointmentTime by remember { mutableStateOf("") }
    var doctorName by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }

    var reminder by remember { mutableStateOf("1 Hour Before") }
    val reminderOptions = listOf("30 Min Before", "1 Hour Before", "1 Day Before")
    var reminderExpanded by remember { mutableStateOf(false) }

    var nextdosetime by remember { mutableStateOf("") }
    var drugname by remember { mutableStateOf("") }

    // 1. Define the possible states
    val options = listOf("Taken", "Missed", "Pending")

    // 2. State variables for expansion and selection
    var expanded by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf(options[2]) } // Defaults to "Pending"


    val appointments = remember { mutableStateListOf<Appointment>() }


    Scaffold(

        // 🔷 TOP BAR
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Booking Appointment")
                },
                actions = {
                    IconButton(onClick = {
                        // logout
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        },

        // 🔷 BOTTOM BAR (FIXED)
        bottomBar = {
            NavigationBar(containerColor = Color.Black) {

                NavigationBarItem(
                    selected = currentRoute == ROUTE_SETTINGS,
                    onClick = {
                        navController.navigate(ROUTE_SETTINGS) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )

                NavigationBarItem(
                    selected = currentRoute == ROUTE_SETTINGS,
                    onClick = {
                        navController.navigate(ROUTE_SETTINGS) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Default.FilePresent, contentDescription = "Records") },
                    label = { Text("Records") }
                )

                NavigationBarItem(
                    selected = currentRoute == ROUTE_SETTINGS,
                    onClick = {
                        navController.navigate(ROUTE_SETTINGS) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Appointments") },
                    label = { Text("Appointments") }
                )

                NavigationBarItem(
                    selected = currentRoute == ROUTE_SETTINGS,
                    onClick = {
                        navController.navigate(ROUTE_SETTINGS) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Default.AccountBox, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }

    ) { innerPadding ->

        // 🔷 CONTENT
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(innerPadding)
                .padding(26.dp),
        ) {

            Column {
                OutlinedTextField(
                    value = appointmentDate,
                    onValueChange = { appointmentDate = it },
                    label = { Text("Appointment Date (e.g. 12/05/2026)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = appointmentTime,
                    onValueChange = { appointmentTime = it },
                    label = { Text("Appointment Time (e.g. 10:30 AM)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = doctorName,
                    onValueChange = { doctorName = it },
                    label = { Text("Doctor Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = reason,
                    onValueChange = { reason = it },
                    label = { Text("Reason for Appointment") },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenuBox(
                    expanded = reminderExpanded,
                    onExpandedChange = { reminderExpanded = !reminderExpanded }
                ) {

                    OutlinedTextField(
                        value = reminder,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Reminder") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = reminderExpanded)
                        },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = reminderExpanded,
                        onDismissRequest = { reminderExpanded = false }
                    ) {
                        reminderOptions.forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    reminder = it
                                    reminderExpanded = false
                                }
                            )
                        }
                    }
                }
                Button(onClick = {
                    appointments.add(
                        Appointment(
                            doctor = doctorName,
                            date = appointmentDate,
                            time = appointmentTime,
                            reason = reason
                        )
                    )
                }) {
                    Text("Book Appointment")
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpcomingAppointmentsScreenPreview(){
    UpcomingAppointmentsScreen(navController = rememberNavController())
}