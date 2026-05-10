package com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.ExitToApp
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
import com.example.smartmedicalsystem.navigation.ROUTE_EMERGENCY_SOS
import com.example.smartmedicalsystem.navigation.ROUTE_MEDICATION_SCREEN
import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
import com.example.smartmedicalsystem.navigation.ROUTE_PROFILE
import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
import com.example.smartmedicalsystem.navigation.ROUTE_UPCOMING_APPOINTMENT
import com.example.smartmedicalsystem.ui.theme.screens.screens.StatCard
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDashboard(
    navController: NavController,
    username: String,
    viewModel: DashboardViewModel,
    statsViewModel: DashboardStatsViewModel,
    onLogout: () -> Unit
) {
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route

    LaunchedEffect(username) {
        viewModel.setUser(username, "Patient")
    }

    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@LaunchedEffect
        statsViewModel.listenPatientStats(uid)
    }

    val greeting              by viewModel.greeting
    val upcomingAppointments  by statsViewModel.upcomingAppointments
    val appVisits             by statsViewModel.appVisits

    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout") },
            text  = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(onClick = { showLogoutDialog = false; onLogout() }) { Text("Yes") }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancel") }
            }
        )
    }

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
                        text = "Patient Dashboard",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.White)
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF004D40)) {
                NavigationBarItem(
                    selected = currentRoute == ROUTE_SETTINGS,
                    onClick  = {
                        navController.navigate(ROUTE_SETTINGS) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD); launchSingleTop = true
                        }
                    },
                    icon  = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
                NavigationBarItem(
                    selected = currentRoute == ROUTE_EMERGENCY_SOS,
                    onClick  = {
                        navController.navigate(ROUTE_EMERGENCY_SOS) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD); launchSingleTop = true
                        }
                    },
                    icon  = { Icon(Icons.Filled.Emergency, contentDescription = "Emergency") },
                    label = { Text("Emergency") }
                )
                NavigationBarItem(
                    selected = currentRoute == ROUTE_UPCOMING_APPOINTMENT,
                    onClick  = {
                        navController.navigate(ROUTE_UPCOMING_APPOINTMENT) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD); launchSingleTop = true
                        }
                    },
                    icon  = { Icon(Icons.Filled.CalendarToday, contentDescription = "Appointments") },
                    label = { Text("Appts") }
                )
                NavigationBarItem(
                    selected = currentRoute == ROUTE_PROFILE,
                    onClick  = {
                        navController.navigate(ROUTE_PROFILE) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD); launchSingleTop = true
                        }
                    },
                    icon  = { Icon(Icons.Filled.AccountBox, contentDescription = "Profile") },
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
                .padding(4.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text  = "$greeting, $username ⚕️",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Text(
                text  = "Your health overview is ready",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                LiveStatCard(
                    label    = "Upcoming",
                    value    = upcomingAppointments.toString(),
                    modifier = Modifier.weight(1f)
                )
                LiveStatCard(
                    label    = "App Visits",
                    value    = appVisits.toString(),
                    modifier = Modifier.weight(1f)
                )
                StatCard("Prescriptions", "3", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick  = { navController.navigate(ROUTE_UPCOMING_APPOINTMENT) },
                modifier = Modifier.fillMaxWidth(),
                colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFF00604E))
            ) {
                Text("Book Appointment")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Takes patient directly to the My Appointments tab (tab index 1)
            OutlinedButton(
                onClick  = { navController.navigate(ROUTE_UPCOMING_APPOINTMENT) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.CalendarToday, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("View My Appointments")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick  = { navController.navigate(ROUTE_MEDICATION_SCREEN) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Quick Action")
            }
        }
    }
}