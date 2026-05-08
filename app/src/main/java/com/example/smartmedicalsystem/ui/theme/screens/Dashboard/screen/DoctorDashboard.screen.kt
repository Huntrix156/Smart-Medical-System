//
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountBox
//import androidx.compose.material.icons.filled.CalendarToday
//import androidx.compose.material.icons.filled.ExitToApp
//import androidx.compose.material.icons.filled.FilePresent
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import com.example.smartmedicalsystem.data.DashboardViewModel
//import com.example.smartmedicalsystem.navigation.ROUTE_INVENTORY_SCREEN
//import com.example.smartmedicalsystem.navigation.ROUTE_MEDICINE_LIST
//import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
//import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
//import com.example.smartmedicalsystem.navigation.ROUTE_UPCOMING_APPOINTMENT
//
//import com.example.smartmedicalsystem.ui.theme.screens.screens.StatCard
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DoctorDashboard(
//    navController: NavController,
//    username: String,
//    viewModel: DashboardViewModel,
//    onLogout: () -> Unit
//) {
//    val currentRoute = navController.currentBackStackEntryAsState()
//        .value?.destination?.route
//
//    LaunchedEffect(username) {
//        viewModel.setUser(username, "Doctor")
//    }
//
//    val greeting = viewModel.greeting.value
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Doctor Dashboard") },
//                actions = {
//                    IconButton(onClick = onLogout) {
//                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
//                    }
//                }
//            )
//        },
//        bottomBar = {
//            NavigationBar(containerColor = Color.Black) {
//                NavigationBarItem(
//                    selected = currentRoute == ROUTE_SETTINGS,
//                    onClick = {
//                        navController.navigate(ROUTE_SETTINGS) {
//                            popUpTo(ROUTE_PATIENT_DASHBOARD)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
//                    label = { Text("Settings") }
//                )
//                NavigationBarItem(
//                    selected = currentRoute == ROUTE_MEDICINE_LIST,
//                    onClick = {
//                        navController.navigate(ROUTE_MEDICINE_LIST) {
//                            popUpTo(ROUTE_PATIENT_DASHBOARD)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Filled.FilePresent, contentDescription = "Records") },
//                    label = { Text("Records") }
//                )
//                NavigationBarItem(
//                    selected = currentRoute == ROUTE_UPCOMING_APPOINTMENT,
//                    onClick = {
//                        navController.navigate(ROUTE_UPCOMING_APPOINTMENT) {
//                            popUpTo(ROUTE_PATIENT_DASHBOARD)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Filled.CalendarToday, contentDescription = "Appointments") },
//                    label = { Text("Appointments") }
//                )
//                NavigationBarItem(
//                    selected = currentRoute == ROUTE_INVENTORY_SCREEN,
//                    onClick = {
//                        navController.navigate(ROUTE_INVENTORY_SCREEN) {
//                            popUpTo(ROUTE_PATIENT_DASHBOARD)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Profile") },
//                    label = { Text("Profile") }
//                )
//            }
//        }
//    ) { padding ->
//
//        // ✅ FIXED: ALL content lives inside the Scaffold lambda — no dangling code outside
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .padding(16.dp)
//        ) {
//
//            Text(
//                text = "$greeting, Doctor $username ⚕️",
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Text(
//                text = "Manage your patients and medical records",
//                style = MaterialTheme.typography.bodyMedium
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text("Welcome, Dr. $username", fontSize = 20.sp, fontWeight = FontWeight.Bold)
//            Text("Cardiology", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                StatCard("Today", "8", Modifier.weight(1f))
//                StatCard("Pending", "5", Modifier.weight(1f))
//                StatCard("Total", "132", Modifier.weight(1f))
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Text("Today's Appointments", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Card(modifier = Modifier.fillMaxWidth()) {
//                Column(
//                    modifier = Modifier.padding(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    AppointmentRow("John Doe", "10:00 AM · Follow-up", "Confirmed")
//                    HorizontalDivider()
//                    AppointmentRow("Mary Njeri", "11:30 AM · New patient", "Pending")
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Button(
//                onClick = {},
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Write Prescription")
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            OutlinedButton(
//                onClick = {},
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("View Patient Records")
//            }
//        }
//    }
//}
//
//// ✅ AppointmentRow helper — only here, not duplicated elsewhere
//@Composable
//fun AppointmentRow(name: String, detail: String, status: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Column {
//            Text(name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
//            Text(detail, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
//        }
//        SuggestionChip(onClick = {}, label = { Text(status, fontSize = 11.sp) })
//    }
//}

// ✅ FIXED: Package matches actual file location in this project
package com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.smartmedicalsystem.data.DashboardStatsViewModel
import com.example.smartmedicalsystem.data.DashboardViewModel
import com.example.smartmedicalsystem.navigation.ROUTE_INVENTORY_SCREEN
import com.example.smartmedicalsystem.navigation.ROUTE_MEDICINE_LIST
import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
import com.example.smartmedicalsystem.navigation.ROUTE_UPCOMING_APPOINTMENT
import com.example.smartmedicalsystem.navigation.ROUTE_WRITE_PRESCRIPTION
import com.example.smartmedicalsystem.ui.theme.screens.screens.StatCard
import com.google.firebase.auth.FirebaseAuth


class colors {
    // Professional Medical Theme Colors
    val PrimaryGreen = Color(0xFF00604E)
    val SecondaryGreen = Color(0xFF004D40)
    val BackgroundColor = Color(0xFFF5F7F6)
    val IconBgColor = Color(0xFFE0F2F1)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDashboard(
    navController: NavController,
    username: String,
    viewModel: DashboardViewModel,
    // ✅ NEW: inject the stats ViewModel
    statsViewModel: DashboardStatsViewModel,
    onLogout: () -> Unit
) {
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route

    LaunchedEffect(username) {
        viewModel.setUser(username, "Doctor")
    }

    // ✅ Start listening for this doctor's pending appointment count.
    // Uses Firebase Auth UID so each doctor only sees their own bookings.
    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@LaunchedEffect
        statsViewModel.listenDoctorPendingAppointments(uid)
    }

    val greeting = viewModel.greeting.value

    // ✅ Observe the live pending count — updates automatically from Firebase
    val pendingAppointments by statsViewModel.pendingAppointments

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(colors().PrimaryGreen)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Doctor Dashboard",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = onLogout) {

                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                }
            }
        },

        bottomBar = {
            NavigationBar(containerColor = Color(0xFF004D40)) {
                NavigationBarItem(
                    selected = currentRoute == ROUTE_SETTINGS,
                    onClick = {
                        navController.navigate(ROUTE_SETTINGS) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
                NavigationBarItem(
                    selected = currentRoute == ROUTE_MEDICINE_LIST,
                    onClick = {
                        navController.navigate(ROUTE_MEDICINE_LIST) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Filled.FilePresent, contentDescription = "Records") },
                    label = { Text("Records") }
                )
                NavigationBarItem(
                    selected = currentRoute == ROUTE_UPCOMING_APPOINTMENT,
                    onClick = {
                        navController.navigate(ROUTE_UPCOMING_APPOINTMENT) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Filled.CalendarToday, contentDescription = "Appointments") },
                    label = { Text("Appointments") }
                )
                NavigationBarItem(
                    selected = currentRoute == ROUTE_INVENTORY_SCREEN,
                    onClick = {
                        navController.navigate(ROUTE_INVENTORY_SCREEN) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                    .fillMaxSize()
                .background(colors().IconBgColor)
                .padding(padding)
//                .padding(1.dp)
                .verticalScroll(rememberScrollState())

        ) {

            Text(
                text = "$greeting, Doctor $username ⚕️",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier,
                color = Color.Black
            )

            Text(
                text = "Manage your patients and medical records",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Welcome, Dr. $username", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier,
                color = Color.Black)
            Text("Cardiology", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Today", "8", Modifier.weight(1f))

                // ✅ LIVE COUNT: pending appointments assigned to this doctor
                LiveStatCard(
                    label = "Pending",
                    value = pendingAppointments.toString(),
                    modifier = Modifier.weight(1f)
                )

                StatCard("Total", "132", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Today's Appointments", fontWeight = FontWeight.SemiBold, fontSize = 16.sp,
                modifier = Modifier,
                color = Color.Black)

            Spacer(modifier = Modifier.height(8.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AppointmentRow("John Doe", "10:00 AM · Follow-up", "Confirmed")
                    HorizontalDivider()
                    AppointmentRow("Mary Njeri", "11:30 AM · New patient", "Pending")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {navController.navigate(ROUTE_WRITE_PRESCRIPTION)
                          },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Write Prescription")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Patient Records")
            }
        }
    }
}

// ✅ AppointmentRow helper — only here, not duplicated elsewhere
@Composable
fun AppointmentRow(name: String, detail: String, status: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(name, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Text(detail, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        SuggestionChip(onClick = {}, label = { Text(status, fontSize = 11.sp) })
    }
}