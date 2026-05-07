//// ✅ FIXED: Package matches actual file location in this project
//package com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountBox
//import androidx.compose.material.icons.filled.CalendarToday
//import androidx.compose.material.icons.filled.Emergency
//import androidx.compose.material.icons.filled.ExitToApp
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
//import com.example.smartmedicalsystem.navigation.ROUTE_EMERGENCY_SOS
//import com.example.smartmedicalsystem.navigation.ROUTE_INVENTORY_SCREEN
//import com.example.smartmedicalsystem.navigation.ROUTE_MEDICATION_SCREEN
//import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
//import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
//import com.example.smartmedicalsystem.navigation.ROUTE_UPCOMING_APPOINTMENT
//import com.example.smartmedicalsystem.ui.theme.screens.screens.StatCard
//
//// ✅ FIXED: Added username and viewModel parameters to match AppNavHost call
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PatientDashboard(
//    navController: NavController,
//    username: String,
//    viewModel: DashboardViewModel,
//    onLogout: () -> Unit
//) {
//    val currentRoute = navController.currentBackStackEntryAsState()
//        .value?.destination?.route
//
//    // ✅ Populate the viewModel with the passed username
//    LaunchedEffect(username) {
//        viewModel.setUser(username, "Patient")
//    }
//
//    val greeting = viewModel.greeting.value
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Patient Dashboard") },
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
//                    selected = currentRoute == ROUTE_EMERGENCY_SOS,
//                    onClick = {
//                        navController.navigate(ROUTE_EMERGENCY_SOS) {
//                            popUpTo(ROUTE_PATIENT_DASHBOARD)
//                            launchSingleTop = true
//                        }
//                    },
//                    icon = { Icon(Icons.Filled.Emergency, contentDescription = "Emergency") },
//                    label = { Text("Emergency") }
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
//        // ✅ FIXED: ALL content is inside the Scaffold lambda — no dangling code
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .padding(16.dp)
//        ) {
//
//            Text(
//                text = "$greeting, Patient $username ⚕️",
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Text(
//                text = "Your health overview is ready",
//                style = MaterialTheme.typography.bodyMedium
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text("Welcome back, $username", fontSize = 20.sp, fontWeight = FontWeight.Bold)
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                StatCard("Upcoming", "2", Modifier.weight(1f))
//                StatCard("Past Visits", "14", Modifier.weight(1f))
//                StatCard("Prescriptions", "3", Modifier.weight(1f))
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Text("Next Appointment", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Card(modifier = Modifier.fillMaxWidth()) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text("Dr. Amina Osei — Cardiology", fontWeight = FontWeight.Medium)
//                    Text(
//                        "Mon, 5 May 2025 · 10:00 AM",
//                        fontSize = 13.sp,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Button(
//                onClick = { navController.navigate(ROUTE_UPCOMING_APPOINTMENT) },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Book Appointment")
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            OutlinedButton(
//                onClick = {},
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("View My Records")
//            }
//
//            OutlinedButton(
//                onClick = { navController.navigate(ROUTE_MEDICATION_SCREEN) },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Quick Action")
//            }
//        }
//    }
//}

// ✅ FIXED: Package matches actual file location in this project
package com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen

import androidx.compose.animation.animateContentSize
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
import com.example.smartmedicalsystem.navigation.ROUTE_INVENTORY_SCREEN
import com.example.smartmedicalsystem.navigation.ROUTE_MEDICATION_SCREEN
import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
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
    // ✅ NEW: inject the stats ViewModel
    statsViewModel: DashboardStatsViewModel,
    onLogout: () -> Unit
) {
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route

    LaunchedEffect(username) {
        viewModel.setUser(username, "Patient")
    }

    // ✅ Start listening for live patient stats.
    // Uses the Firebase Auth UID of the currently logged-in user so each
    // patient only sees their own appointment and visit counts.
    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@LaunchedEffect
        statsViewModel.listenPatientStats(uid)
    }

    val greeting = viewModel.greeting.value

    // ✅ Observe live counts — update automatically from Firebase
    val upcomingAppointments by statsViewModel.upcomingAppointments
    val appVisits by statsViewModel.appVisits

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
                    selected = currentRoute == ROUTE_EMERGENCY_SOS,
                    onClick = {
                        navController.navigate(ROUTE_EMERGENCY_SOS) {
                            popUpTo(ROUTE_PATIENT_DASHBOARD)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(Icons.Filled.Emergency, contentDescription = "Emergency") },
                    label = { Text("Emergency") }
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

        )
        {

            Text(
                text = "$greeting, Patient $username ⚕️",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier,
                color = Color.Black
            )

            Text(
                text = "Your health overview is ready",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Welcome back, $username", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier,
                color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                // ✅ LIVE COUNT: upcoming appointments booked by this patient
                LiveStatCard(
                    label = "Upcoming",
                    value = upcomingAppointments.toString(),
                    modifier = Modifier.weight(1f)
                )

                // ✅ LIVE COUNT: total number of times this patient visited the app
                LiveStatCard(
                    label = "App Visits",
                    value = appVisits.toString(),
                    modifier = Modifier.weight(1f)
                )

                StatCard("Prescriptions", "3", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Next Appointment", fontWeight = FontWeight.SemiBold, fontSize = 16.sp,
                modifier = Modifier,
                color = Color.Black)

            Spacer(modifier = Modifier.height(8.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Dr. Amina Osei — Cardiology", fontWeight = FontWeight.Medium)
                    Text(
                        "Mon, 5 May 2025 · 10:00 AM",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate(ROUTE_UPCOMING_APPOINTMENT) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Book Appointment")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View My Records")
            }

            OutlinedButton(
                onClick = { navController.navigate(ROUTE_MEDICATION_SCREEN) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Quick Action")
            }
        }
    }
}