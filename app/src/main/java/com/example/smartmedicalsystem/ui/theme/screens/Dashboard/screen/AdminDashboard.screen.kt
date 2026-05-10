package com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen//package com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen
//
//import androidx.compose.animation.animateContentSize
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountBox
//import androidx.compose.material.icons.filled.CalendarToday
//import androidx.compose.material.icons.filled.ExitToApp
//import androidx.compose.material.icons.filled.FilePresent
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import com.example.smartmedicalsystem.data.DashboardStatsViewModel
//import com.example.smartmedicalsystem.data.DashboardViewModel
//import com.example.smartmedicalsystem.navigation.ROUTE_ADMIN_ADD_DOCTOR
//import com.example.smartmedicalsystem.navigation.ROUTE_GENERATE_REPORT
//import com.example.smartmedicalsystem.navigation.ROUTE_INVENTORY_SCREEN
//import com.example.smartmedicalsystem.navigation.ROUTE_MEDICINE_LIST
//import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
//import com.example.smartmedicalsystem.navigation.ROUTE_PROFILE
//import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
//import com.example.smartmedicalsystem.navigation.ROUTE_UPCOMING_APPOINTMENT
//import com.example.smartmedicalsystem.ui.theme.screens.screens.StatCard
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AdminDashboard(
//    navController: NavController,
//    username: String,
//    viewModel: DashboardViewModel,
//
//    statsViewModel: DashboardStatsViewModel,
//    onLogout: () -> Unit
//) {
//    val currentRoute = navController.currentBackStackEntryAsState()
//        .value?.destination?.route
//
//    LaunchedEffect(username) {
//        viewModel.setUser(username, "Admin")
//    }
//
//    LaunchedEffect(Unit) {
//        statsViewModel.listenDoctorCount()
//    }
//
//    val greeting = viewModel.greeting.value
//
//    val doctorCount by statsViewModel.doctorCount
//
//
//
//
//    var showLogoutDialog by remember {
//        mutableStateOf(false)
//    }
//
//    if (showLogoutDialog) {
//
//        AlertDialog(
//            onDismissRequest = {
//                showLogoutDialog = false
//            },
//
//            title = {
//                Text(
//                    text = "Logout"
//                )
//            },
//
//            text = {
//                Text(
//                    text = "Are you sure you want to logout?"
//                )
//            },
//
//            confirmButton = {
//
//                TextButton(
//                    onClick = {
//
//                        showLogoutDialog = false
//
//                        // Execute logout
//                        onLogout()
//                    }
//                ) {
//
//                    Text(
//                        text = "Yes"
//                    )
//                }
//            },
//
//            dismissButton = {
//
//                TextButton(
//                    onClick = {
//                        showLogoutDialog = false
//                    }
//                ) {
//
//                    Text(
//                        text = "Cancel"
//                    )
//                }
//            }
//        )
//    }
//
//    Scaffold(
//        topBar = {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(90.dp)
//                    .background(colors().PrimaryGreen)
//                    .padding(horizontal = 16.dp, vertical = 12.dp)
//            ) {
//
//                Row(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//
//                    Text(
//                        text = "Admin Dashboard",
//                        color = Color.White,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                    )
//
//                    IconButton(onClick = { showLogoutDialog = true }) {
//
//                        Icon(
//                            imageVector = Icons.Default.ExitToApp,
//                            contentDescription = "Logout",
//                            tint = Color.White
//                        )
//                    }
//                }
//            }
//        },
//
//        bottomBar = {
//            NavigationBar(containerColor = Color(0xFF004D40)) {
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
//                    selected = currentRoute == ROUTE_PROFILE,
//                    onClick = {
//                        navController.navigate(ROUTE_PROFILE) {
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
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(colors().IconBgColor)
//                .padding(padding)
//                .padding(4.dp)
//                .verticalScroll(rememberScrollState())
//
//
//        ) {
//
//            Text(
//                text = "$greeting, Admin $username ⚙️",
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier,
//                color = Color.Black
//            )
//
//            Text(
//                text = "Monitor users, doctors, patients, and system activity",
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = Modifier,
//                color = Color.Black
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text("System Overview", fontSize = 20.sp, fontWeight = FontWeight.Bold,
//                modifier = Modifier,
//                color = Color.Black)
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                StatCard("Total Users", "248", Modifier.weight(1f))
//
//
//                LiveStatCard(
//                    label = "Doctors",
//                    value = doctorCount.toString(),
//                    modifier = Modifier.weight(1f)
//                )
//
//                LiveStatCard(
//                    label = "Patients",
//                    value = "214",
//                    modifier = Modifier.weight(1f)
//                )
//                StatCard("Patients", "214", Modifier.weight(1f))
//            }
//
////            Spacer(modifier = Modifier.height(20.dp))
////
////            Text("Recent Activity", fontWeight = FontWeight.SemiBold, fontSize = 16.sp,
////                modifier = Modifier,
////                color = Color.Black)
////
////            Spacer(modifier = Modifier.height(8.dp))
////
////            Card(modifier = Modifier.fillMaxWidth()) {
////                Column(
////                    modifier = Modifier.padding(16.dp),
////                    verticalArrangement = Arrangement.spacedBy(12.dp)
////                ) {
////                    Text("New doctor registered — Dr. Kamau", fontSize = 14.sp)
////                    HorizontalDivider()
////                    Text("System backup completed", fontSize = 14.sp)
////                }
////            }
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
//                onClick = {navController.navigate(ROUTE_GENERATE_REPORT)},
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Generate Report")
//            }
//        }
//    }
//}
//
//
//@Composable
//fun LiveStatCard(label: String, value: String, modifier: Modifier = Modifier) {
//    Card(
//        modifier = modifier.animateContentSize(),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.secondaryContainer
//        )
//    ) {
//        Column(modifier = Modifier.padding(12.dp)) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(
//                    label,
//                    fontSize = 12.sp,
//                    color = MaterialTheme.colorScheme.onSecondaryContainer
//                )
//                Surface(
//                    color = MaterialTheme.colorScheme.primary,
//                    shape = MaterialTheme.shapes.extraSmall
//                ) {
//                    Text(
//                        "LIVE",
//                        fontSize = 8.sp,
//                        color = MaterialTheme.colorScheme.onPrimary,
//                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                value,
//                fontSize = 22.sp,
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colorScheme.onSecondaryContainer
//            )
//        }
//
//    }
//}

import androidx.compose.animation.animateContentSize
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
import com.example.smartmedicalsystem.navigation.ROUTE_ADMIN_ADD_DOCTOR
import com.example.smartmedicalsystem.navigation.ROUTE_GENERATE_REPORT
import com.example.smartmedicalsystem.navigation.ROUTE_MEDICINE_LIST
import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
import com.example.smartmedicalsystem.navigation.ROUTE_PROFILE
import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
import com.example.smartmedicalsystem.navigation.ROUTE_UPCOMING_APPOINTMENT
import com.example.smartmedicalsystem.ui.theme.screens.screens.StatCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(
    navController: NavController,
    username: String,
    viewModel: DashboardViewModel,

    statsViewModel: DashboardStatsViewModel,
    onLogout: () -> Unit
) {
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route

    LaunchedEffect(username) {
        viewModel.setUser(username, "Admin")
    }

    LaunchedEffect(Unit) {
        statsViewModel.listenDoctorCount()
        statsViewModel.listenPatientCount()
    }

    val greeting = viewModel.greeting.value

    val doctorCount by statsViewModel.doctorCount
    val patientCount by statsViewModel.patientCount




    var showLogoutDialog by remember {
        mutableStateOf(false)
    }

    if (showLogoutDialog) {

        AlertDialog(
            onDismissRequest = {
                showLogoutDialog = false
            },

            title = {
                Text(
                    text = "Logout"
                )
            },

            text = {
                Text(
                    text = "Are you sure you want to logout?"
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        showLogoutDialog = false

                        // Execute logout
                        onLogout()
                    }
                ) {

                    Text(
                        text = "Yes"
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        showLogoutDialog = false
                    }
                ) {

                    Text(
                        text = "Cancel"
                    )
                }
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
                        text = "Admin Dashboard",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    IconButton(onClick = { showLogoutDialog = true }) {

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
                    selected = currentRoute == ROUTE_PROFILE,
                    onClick = {
                        navController.navigate(ROUTE_PROFILE) {
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
                .padding(4.dp)
                .verticalScroll(rememberScrollState())


        ) {

            Text(
                text = "$greeting, Admin $username ⚙️",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier,
                color = Color.Black
            )

            Text(
                text = "Monitor users, doctors, patients, and system activity",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("System Overview", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier,
                color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Total Users", "248", Modifier.weight(1f))

                LiveStatCard(
                    label = "Doctors",
                    value = doctorCount.toString(),
                    modifier = Modifier.weight(1f)
                )

                LiveStatCard(
                    label = "Patients",
                    value = patientCount.toString(),
                    modifier = Modifier.weight(1f)
                )
            }

//            Spacer(modifier = Modifier.height(20.dp))
//
//            Text("Recent Activity", fontWeight = FontWeight.SemiBold, fontSize = 16.sp,
//                modifier = Modifier,
//                color = Color.Black)
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

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate(ROUTE_ADMIN_ADD_DOCTOR) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add New Doctor")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {navController.navigate(ROUTE_GENERATE_REPORT)},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Generate Report")
            }
        }
    }
}


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