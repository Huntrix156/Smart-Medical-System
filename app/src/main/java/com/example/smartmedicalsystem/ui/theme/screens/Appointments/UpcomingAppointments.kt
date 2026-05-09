//package com.example.smartmedicalsystem.ui.theme.screens
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountBox
//import androidx.compose.material.icons.filled.CalendarToday
//import androidx.compose.material.icons.filled.ExitToApp
//import androidx.compose.material.icons.filled.FilePresent
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.material3.Button
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ExposedDropdownMenuBox
//import androidx.compose.material3.ExposedDropdownMenuDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.example.smartmedicalsystem.models.Appointment
//import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
//import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun UpcomingAppointmentsScreen(navController: NavController) {
//    val selectedItem = remember { mutableIntStateOf(0) }
//    val currentRoute = navController.currentBackStackEntryAsState()
//        .value?.destination?.route
//    var appointmentDate by remember { mutableStateOf("") }
//    var appointmentTime by remember { mutableStateOf("") }
//    var doctorName by remember { mutableStateOf("") }
//    var reason by remember { mutableStateOf("") }
//
//    var reminder by remember { mutableStateOf("1 Hour Before") }
//    val reminderOptions = listOf("30 Min Before", "1 Hour Before", "1 Day Before")
//    var reminderExpanded by remember { mutableStateOf(false) }
//
//    var nextdosetime by remember { mutableStateOf("") }
//    var drugname by remember { mutableStateOf("") }
//
//    // 1. Define the possible states
//    val options = listOf("Taken", "Missed", "Pending")
//
//    // 2. State variables for expansion and selection
//    var expanded by remember { mutableStateOf(false) }
//    var status by remember { mutableStateOf(options[2]) } // Defaults to "Pending"
//
//
//    val appointments = remember { mutableStateListOf<Appointment>() }
//
//
//    Scaffold(
//
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(text = "Booking Appointment")
//                },
//                actions = {
//                    IconButton(onClick = {
//                        // logout
//                    }) {
//                        Icon(
//                            imageVector = Icons.Default.ExitToApp,
//                            contentDescription = "Logout",
//                            tint = Color.White
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.Blue,
//                    titleContentColor = Color.White
//                )
//            )
//        },
//
//        bottomBar = {
//            NavigationBar(containerColor = Color.Black) {
//
//                NavigationBarItem(
//                    selected = currentRoute == ROUTE_SETTINGS,
//                    onClick = {
//                        navController.navigate(ROUTE_SETTINGS) {
//                            popUpTo(ROUTE_PATIENT_DASHBOARD)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
//                    label = { Text("Settings") }
//                )
//
//                NavigationBarItem(
//                    selected = currentRoute == ROUTE_SETTINGS,
//                    onClick = {
//                        navController.navigate(ROUTE_SETTINGS) {
//                            popUpTo(ROUTE_PATIENT_DASHBOARD)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Default.FilePresent, contentDescription = "Records") },
//                    label = { Text("Records") }
//                )
//
//                NavigationBarItem(
//                    selected = currentRoute == ROUTE_SETTINGS,
//                    onClick = {
//                        navController.navigate(ROUTE_SETTINGS) {
//                            popUpTo(ROUTE_PATIENT_DASHBOARD)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Appointments") },
//                    label = { Text("Appointments") }
//                )
//
//                NavigationBarItem(
//                    selected = currentRoute == ROUTE_SETTINGS,
//                    onClick = {
//                        navController.navigate(ROUTE_SETTINGS) {
//                            popUpTo(ROUTE_PATIENT_DASHBOARD)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Default.AccountBox, contentDescription = "Profile") },
//                    label = { Text("Profile") }
//                )
//            }
//        }
//
//    ) { innerPadding ->
//
//        // 🔷 CONTENT
//        Box(
//            modifier = Modifier.fillMaxWidth()
//                .padding(innerPadding)
//                .padding(26.dp),
//        ) {
//
//            Column {
//                OutlinedTextField(
//                    value = appointmentDate,
//                    onValueChange = { appointmentDate = it },
//                    label = { Text("Appointment Date (e.g. 12/05/2026)") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                OutlinedTextField(
//                    value = appointmentTime,
//                    onValueChange = { appointmentTime = it },
//                    label = { Text("Appointment Time (e.g. 10:30 AM)") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                OutlinedTextField(
//                    value = doctorName,
//                    onValueChange = { doctorName = it },
//                    label = { Text("Doctor Name") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                OutlinedTextField(
//                    value = reason,
//                    onValueChange = { reason = it },
//                    label = { Text("Reason for Appointment") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//                ExposedDropdownMenuBox(
//                    expanded = reminderExpanded,
//                    onExpandedChange = { reminderExpanded = !reminderExpanded }
//                ) {
//
//                    OutlinedTextField(
//                        value = reminder,
//                        onValueChange = {},
//                        readOnly = true,
//                        label = { Text("Reminder") },
//                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = reminderExpanded)
//                        },
//                        modifier = Modifier.menuAnchor()
//                    )
//
//                    ExposedDropdownMenu(
//                        expanded = reminderExpanded,
//                        onDismissRequest = { reminderExpanded = false }
//                    ) {
//                        reminderOptions.forEach {
//                            DropdownMenuItem(
//                                text = { Text(it) },
//                                onClick = {
//                                    reminder = it
//                                    reminderExpanded = false
//                                }
//                            )
//                        }
//                    }
//                }
//                Button(onClick = {
//                    appointments.add(
//                        Appointment(
//                            doctorName = doctorName,
//                            date = appointmentDate,
//                            time = appointmentTime,
//                            reason = reason
//                        )
//                    )
//                }) {
//                    Text("Book Appointment")
//                }
//            }
//        }
//    }
//}
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun UpcomingAppointmentsScreenPreview(){
//    UpcomingAppointmentsScreen(navController = rememberNavController())
//}


package com.example.smartmedicalsystem.ui.theme.screens.Appointments

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartmedicalsystem.data.AppointmentViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingAppointmentsScreen(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel = viewModel()
) {
    val context = LocalContext.current

    // Form fields
    var appointmentDate by remember { mutableStateOf("") }
    var appointmentTime by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    var specialization by remember { mutableStateOf("") }

    // Specialization dropdown
    val specializationOptions = listOf(
        "General Practice",
        "Cardiology",
        "Dermatology",
        "Neurology",
        "Orthopedics",
        "Pediatrics",
        "Psychiatry",
        "Gynecology",
        "Ophthalmology",
        "ENT"
    )
    var specExpanded by remember { mutableStateOf(false) }

    // ViewModel state
    val isLoading by appointmentViewModel.isLoading
    val successMessage by appointmentViewModel.successMessage
    val errorMessage by appointmentViewModel.errorMessage

    // Handle feedback toasts
    LaunchedEffect(successMessage) {
        successMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            appointmentViewModel.clearMessages()
            navController.popBackStack()
        }
    }
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            appointmentViewModel.clearMessages()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Appointment") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00604E),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // ── Header ────────────────────────────────────────────────────
            Text(
                text = "New Appointment",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00604E)
            )
            Text(
                text = "Fill in the details below. The admin will review and assign you a doctor.",
                fontSize = 13.sp,
                color = Color.Gray
            )

            HorizontalDivider()

            // ── Date ──────────────────────────────────────────────────────
            OutlinedTextField(
                value = appointmentDate,
                onValueChange = { appointmentDate = it },
                label = { Text("Preferred Date (e.g. 15/05/2026)") },
                leadingIcon = {
                    Icon(Icons.Default.CalendarToday, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Time ──────────────────────────────────────────────────────
            OutlinedTextField(
                value = appointmentTime,
                onValueChange = { appointmentTime = it },
                label = { Text("Preferred Time (e.g. 10:30 AM)") },
                leadingIcon = {
                    Icon(Icons.Default.Schedule, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // ── Specialization Dropdown ───────────────────────────────────
            ExposedDropdownMenuBox(
                expanded = specExpanded,
                onExpandedChange = { specExpanded = !specExpanded }
            ) {
                OutlinedTextField(
                    value = specialization,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Specialization Required") },
                    leadingIcon = {
                        Icon(Icons.Default.MedicalServices, contentDescription = null)
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = specExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = specExpanded,
                    onDismissRequest = { specExpanded = false }
                ) {
                    specializationOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                specialization = option
                                specExpanded = false
                            }
                        )
                    }
                }
            }

            // ── Reason ────────────────────────────────────────────────────
            OutlinedTextField(
                value = reason,
                onValueChange = { reason = it },
                label = { Text("Reason for Appointment") },
                placeholder = { Text("Describe your symptoms or concern…") },
                leadingIcon = {
                    Icon(Icons.Default.Notes, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                minLines = 4,
                maxLines = 6
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Info card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "ℹ️ How it works",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = Color(0xFF00604E)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "1. You submit this request.\n" +
                                "2. Admin reviews and assigns a doctor based on specialization.\n" +
                                "3. The doctor confirms or requests a referral.\n" +
                                "4. You receive a notification about your appointment.",
                        fontSize = 12.sp,
                        color = Color(0xFF004D40),
                        lineHeight = 18.sp
                    )
                }
            }

            // ── Submit Button ─────────────────────────────────────────────
            Button(
                onClick = {
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user == null) {
                        Toast.makeText(context, "You must be logged in.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    when {
                        appointmentDate.isBlank() ->
                            Toast.makeText(context, "Please enter a preferred date.", Toast.LENGTH_SHORT).show()
                        appointmentTime.isBlank() ->
                            Toast.makeText(context, "Please enter a preferred time.", Toast.LENGTH_SHORT).show()
                        specialization.isBlank() ->
                            Toast.makeText(context, "Please select a specialization.", Toast.LENGTH_SHORT).show()
                        reason.isBlank() ->
                            Toast.makeText(context, "Please describe the reason for this appointment.", Toast.LENGTH_SHORT).show()
                        else -> {
                            val patientName = user.displayName
                                ?: user.email?.substringBefore("@")
                                ?: "Patient"

                            appointmentViewModel.bookAppointment(
                                patientId = user.uid,
                                patientName = patientName,
                                reason = reason.trim(),
                                date = appointmentDate.trim(),
                                time = appointmentTime.trim(),
                                specialization = specialization,
                                onSuccess = { /* handled by LaunchedEffect */ },
                                onFailure = { /* handled by LaunchedEffect */ }
                            )
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00604E))
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(22.dp)
                    )
                } else {
                    Text("Book Appointment", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpcomingAppointmentsScreenPreview() {
    UpcomingAppointmentsScreen(navController = rememberNavController())
}