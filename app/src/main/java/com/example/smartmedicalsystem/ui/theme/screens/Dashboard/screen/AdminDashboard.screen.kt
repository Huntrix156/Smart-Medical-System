//// ✅ FIXED: Package matches actual file location in this project
//package com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen
//
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
//import com.example.smartmedicalsystem.navigation.ROUTE_ADMIN_ADD_DOCTOR
//import com.example.smartmedicalsystem.navigation.ROUTE_INVENTORY_SCREEN
//import com.example.smartmedicalsystem.navigation.ROUTE_MEDICINE_LIST
//import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
//import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
//import com.example.smartmedicalsystem.navigation.ROUTE_UPCOMING_APPOINTMENT
//
//// ✅ FIXED: Import StatCard from its own file
//import com.example.smartmedicalsystem.ui.theme.screens.screens.StatCard
//
//// ✅ FIXED: Added username parameter — AppNavHost passes it in
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AdminDashboard(
//    navController: NavController,
//    username: String,
//    viewModel: DashboardViewModel,
//    onLogout: () -> Unit
//) {
//    val currentRoute = navController.currentBackStackEntryAsState()
//        .value?.destination?.route
//
//    // ✅ Populate viewModel with the passed username
//    LaunchedEffect(username) {
//        viewModel.setUser(username, "Admin")
//    }
//
//    val greeting = viewModel.greeting.value
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Admin Dashboard") },
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
//        // ✅ FIXED: All content is properly inside the Scaffold lambda
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .padding(16.dp)
//        ) {
//
//            Text(
//                text = "$greeting, Admin $username ⚙️",
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Text(
//                text = "Monitor users, doctors, patients, and system activity",
//                style = MaterialTheme.typography.bodyMedium
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text("System Overview", fontSize = 20.sp, fontWeight = FontWeight.Bold)
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                StatCard("Total Users", "248", Modifier.weight(1f))
//                StatCard("Doctors", "32", Modifier.weight(1f))
//                StatCard("Patients", "214", Modifier.weight(1f))
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Text("Recent Activity", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Card(modifier = Modifier.fillMaxWidth()) {
//                Column(
//                    modifier = Modifier.padding(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    Text("New doctor registered — Dr. Kamau", fontSize = 14.sp)
//                    HorizontalDivider()
//                    Text("System backup completed", fontSize = 14.sp)
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Button(
//                onClick = { navController.navigate(ROUTE_ADMIN_ADD_DOCTOR) },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Add New Doctor")
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            OutlinedButton(
//                onClick = {},
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Generate Report")
//            }
//        }
//    }
//}

// ✅ FIXED: Package matches actual file location in this project
package com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen

import androidx.compose.animation.animateContentSize
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
import com.example.smartmedicalsystem.navigation.ROUTE_ADMIN_ADD_DOCTOR
import com.example.smartmedicalsystem.navigation.ROUTE_INVENTORY_SCREEN
import com.example.smartmedicalsystem.navigation.ROUTE_MEDICINE_LIST
import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
import com.example.smartmedicalsystem.navigation.ROUTE_UPCOMING_APPOINTMENT
import com.example.smartmedicalsystem.ui.theme.screens.screens.StatCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(
    navController: NavController,
    username: String,
    viewModel: DashboardViewModel,
    // ✅ NEW: inject the stats ViewModel — provide it from AppNavHost the same
    //        way you provide DashboardViewModel (viewModel() or hiltViewModel())
    statsViewModel: DashboardStatsViewModel,
    onLogout: () -> Unit
) {
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route

    // Populate greeting ViewModel
    LaunchedEffect(username) {
        viewModel.setUser(username, "Admin")
    }

    // ✅ Start listening for live doctor count as soon as the screen opens.
    // LaunchedEffect with Unit key means it runs once on first composition.
    LaunchedEffect(Unit) {
        statsViewModel.listenDoctorCount()
    }

    val greeting = viewModel.greeting.value

    // ✅ Observe the live doctor count — updates automatically from Firebase
    val doctorCount by statsViewModel.doctorCount

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
        },
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
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())

        ) {

            Text(
                text = "$greeting, Admin $username ⚙️",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Monitor users, doctors, patients, and system activity",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("System Overview", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Total Users", "248", Modifier.weight(1f))

                // ✅ LIVE COUNT: shows real-time doctor count from Firebase.
                // The number animates whenever a new doctor is added / removed.
                LiveStatCard(
                    label = "Doctors",
                    value = doctorCount.toString(),
                    modifier = Modifier.weight(1f)
                )

                StatCard("Patients", "214", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Recent Activity", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("New doctor registered — Dr. Kamau", fontSize = 14.sp)
                    HorizontalDivider()
                    Text("System backup completed", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate(ROUTE_ADMIN_ADD_DOCTOR) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add New Doctor")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generate Report")
            }
        }
    }
}

/**
 * LiveStatCard — identical visual to StatCard but adds a small pulsing
 * "LIVE" indicator badge so admins know the number updates in real-time.
 */
@Composable
fun LiveStatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    label,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                // ✅ Live badge
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    Text(
                        "LIVE",
                        fontSize = 8.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
