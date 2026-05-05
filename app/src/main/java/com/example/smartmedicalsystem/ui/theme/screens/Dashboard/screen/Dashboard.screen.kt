package com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartmedicalsystem.navigation.ROUTE_ADD_MEDICATION
import com.example.smartmedicalsystem.navigation.ROUTE_INVENTORY_SCREEN
import com.example.smartmedicalsystem.navigation.ROUTE_MEDICATION_SCREEN
import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController){
    val selectedItem = remember { mutableIntStateOf(0) }
    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route
//                val authViewModel: AuthViewModel = viewModel()
//                val context = LocalContext.current

//            val scrollState = rememberScrollState()


    // Drawer state
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(

                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.7f) // 👈 70% of screen (adjustable)
                        ) {

                            Text(
                                text = "Smart Medical System Menu",
                                modifier = Modifier.padding(16.dp),
                                fontSize = 20.sp
                            )

                            NavigationDrawerItem(
                                label = { Text("Dashboard") },
                                selected = false,
                                onClick = { scope.launch { drawerState.close() }
                                    navController.navigate(ROUTE_ADD_MEDICATION)}
                            )

                            NavigationDrawerItem(
                                label = { Text("Patients") },
                                selected = false,
                                onClick = { scope.launch { drawerState.close() }
                                    navController.navigate("patients") }
                            )

                            NavigationDrawerItem(
                                label = { Text("Settings") },
                                selected = false,
                                onClick = { scope.launch { drawerState.close() }
                                    navController.navigate("settings")}
                            )

                            NavigationDrawerItem(
                                label = { Text("Logout") },
                                selected = false,
                                onClick = {
//                                    authViewModel.logout(navController, context)
                                }
                            )
                        }
                    }
                ) {






                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text="Smart Medical System")},
                                actions = {
                                    IconButton(
                                        onClick = {
//                                            authViewModel.logout(navController, context)
                                                                                        })
                                    {
                                        Icon(
                                            Icons.Default.ExitToApp,
                                            contentDescription = "Logout",
                                            tint = Color.White
                                        )
                                    }

                                },

                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Blue,
                                    titleContentColor = Color.White
                                ),
                            )
                        },
                        bottomBar = { NavigationBar(containerColor = Color.Black){
                            NavigationBarItem(
                                selected = currentRoute == ROUTE_SETTINGS,
                                onClick = {
                                    navController.navigate(ROUTE_SETTINGS) {
                                        popUpTo(ROUTE_PATIENT_DASHBOARD)
                                        launchSingleTop = true
                                    }
                                },
                                icon = {Icon(Icons.Filled.Settings,
                                    contentDescription = "Settings")},
                                label = {Text(text = "Settings") }

                            )
                            NavigationBarItem(
                                selected = currentRoute == ROUTE_SETTINGS,
                                onClick = {
                                    navController.navigate(ROUTE_SETTINGS) {
                                        popUpTo(ROUTE_PATIENT_DASHBOARD)
                                        launchSingleTop = true
                                    }
                                },
                                icon = {Icon(Icons.Filled.FilePresent,
                                    contentDescription = "Records")},
                                label = {Text(text = "Records") })

                            NavigationBarItem(
                                selected = currentRoute == ROUTE_SETTINGS,
                                onClick = {
                                    navController.navigate(ROUTE_SETTINGS) {
                                        popUpTo(ROUTE_PATIENT_DASHBOARD)
                                        launchSingleTop = true
                                    }
                                },
                                icon = {Icon(Icons.Filled.CalendarToday,
                                    contentDescription = "Appointments")},
                                label = {Text(text = "Appointments") },
                            )
                            NavigationBarItem(
                                selected = currentRoute == ROUTE_SETTINGS,
                                onClick = {
                                    navController.navigate(ROUTE_SETTINGS) {
                                        popUpTo(ROUTE_PATIENT_DASHBOARD)
                                        launchSingleTop = true
                                    }
                                },
                                icon = {Icon(Icons.Filled.AccountBox,
                                    contentDescription = "Profile")},
                                label = {Text(text = "Profile") },
                            )


                        } }

                    )

                    { innerPadding ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
//                            .background(Color(0xFFF3F5F9))
                            .verticalScroll(rememberScrollState()),

                            ) {

                            Text(text = "Welcome to Smart Medical System",
                                fontSize = 25.sp,
                                color = Color.Blue)

                            Row(
                                modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly) {

                                Card(
                                    modifier = Modifier.size(100.dp),
                                    colors = CardDefaults.cardColors(  containerColor = Color.Blue),
                                    elevation = CardDefaults.cardElevation(5.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment= Alignment.CenterHorizontally) {

                                    Text(
                                        text = "120",
                                        color = Color.White,
                                        fontSize = 30.sp)
                                    Text(
                                        text = "Patients",
                                        color = Color.White,
                                        fontSize = 20.sp)
                                }}
                                Card(
                                    modifier = Modifier
                                        .size(100.dp),

                                    colors = CardDefaults.cardColors(  containerColor = Color.Blue),
                                    elevation = CardDefaults.cardElevation(5.dp),
                                    shape = RoundedCornerShape(12.dp)

                                ) {
                                    Column(modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment= Alignment.CenterHorizontally) {
                                    Text(text = "120",
                                        color = Color.White,
                                        fontSize = 30.sp)
                                    Text(text = "view Patients list",
                                        color = Color.White,
                                        fontSize = 20.sp)
                                }}
                                Card(modifier = Modifier.size(100.dp),
                                    colors = CardDefaults.cardColors(  containerColor = Color.Blue),
                                    elevation = CardDefaults.cardElevation(5.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) { Column(modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment= Alignment.CenterHorizontally) {
                                    Text(text = "120",
                                        color = Color.White,
                                        fontSize = 30.sp)
                                    Text(text = "Patients",
                                        color = Color.White,
                                        fontSize = 20.sp)

                                }}
                            }

                            Text(text="Quick Action")
//Add patients card
                            Card(onClick = {
//                                navController.navigate(
//                                ROUTE_ADD_PATIENT
//                            )
                            },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2EFF)),
                                elevation = CardDefaults.cardElevation(8.dp),
                                shape = RoundedCornerShape(16.dp))
                            {
                                Row(modifier = Modifier
                                    .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    //Icon
                                    Icon(Icons.Filled.Person,
                                        contentDescription = "Today`s Medications",
                                        tint = Color.Black,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    //Text content
                                    Column() {
                                        Text(text= "Today`s Medications",
                                            fontSize = 20.sp,
                                            color = Color.Black)
                                        Text("Here Are Today`s Medications", fontSize = 18.sp, color = Color.Black)

                                    }
                                }

                            }

                            Card(onClick = {
//                                navController.navigate(ROUTE_ADD_PATIENT)
                            },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2EFF)),
                                elevation = CardDefaults.cardElevation(8.dp),
                                shape = RoundedCornerShape(16.dp))
                            {
                                Row(modifier = Modifier
                                    .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    //Icon
                                    Icon(Icons.Filled.Person,
                                        contentDescription = "Upcoming Appointment",
                                        tint = Color.Black,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    //Text content
                                    Column() {
                                        Text(text= "Upcoming Appointment",
                                            fontSize = 20.sp,
                                            color = Color.Black)
                                        Text(" View Upcoming Appointment",
                                            fontSize = 14.sp,
                                            color = Color.Black)
                                    }
                                }

                            }
                            Card(onClick = {
                                navController.navigate(ROUTE_ADD_MEDICATION)
                            },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2EFF)),
                                elevation = CardDefaults.cardElevation(8.dp),
                                shape = RoundedCornerShape(16.dp))
                            {
                                Row(modifier = Modifier
                                    .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    //Icon
                                    Icon(
                                        Icons.Filled.Person,
                                        contentDescription = "View Pharmacy",
                                        tint = Color.Black,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    //Text content
                                    Column() {
                                        Text(text= "Pharmacy History Records",
                                            fontSize = 20.sp,
                                            color = Color.Black)
                                        Text("View Pharmacy History Records",
                                            fontSize = 14.sp,
                                            color = Color.Black)
                                    }
                                }

                            }
                            Card(onClick = {
//                                navController.navigate(ROUTE_PATIENT_LIST)
                            },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2EFF)),
                                elevation = CardDefaults.cardElevation(8.dp),
                                shape = RoundedCornerShape(16.dp))
                            {
                                Row(modifier = Modifier
                                    .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    //Icon
                                    Icon(
                                        Icons.Filled.Person,
                                        contentDescription = "View Health Summaries",
                                        tint = Color.Black,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    //Text content
                                    Column() {
                                        Text(text= "Patient`s History Records",
                                            fontSize = 20.sp,
                                            color = Color.Black)
                                        Text("View Patient`s History Records",
                                            fontSize = 14.sp,
                                            color = Color.Black)
                                    }
                                }

                            }

                            Card(onClick = { navController.navigate(ROUTE_INVENTORY_SCREEN)
                            },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2EFF)),
                                elevation = CardDefaults.cardElevation(8.dp),
                                shape = RoundedCornerShape(16.dp))
                            {
                                Row(modifier = Modifier
                                    .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    //Icon
                                    Icon(Icons.Filled.Person,
                                        contentDescription = "Pharmacy Management History",
                                        tint = Color.Black,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    //Text content
                                    Column() {
                                        Text(text= "Pharmacy Management History",
                                            fontSize = 20.sp,
                                            color = Color.Black)
                                        Text("Pharmacy Management History",
                                            fontSize = 14.sp,
                                            color = Color.Black)
                                    }
                                }

                            }
                            Card(onClick = { navController.navigate(ROUTE_MEDICATION_SCREEN)
                            },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2EFF)),
                                elevation = CardDefaults.cardElevation(8.dp),
                                shape = RoundedCornerShape(16.dp))
                            {
                                Row(modifier = Modifier
                                    .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    //Icon
                                    Icon(Icons.Filled.Person,
                                        contentDescription = "Pharmacy Management History",
                                        tint = Color.Black,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    //Text content
                                    Column() {
                                        Text(text= "Pharmacy Management History",
                                            fontSize = 20.sp,
                                            color = Color.Black)
                                        Text("Pharmacy Management History",
                                            fontSize = 14.sp,
                                            color = Color.Black)
                                    }
                                }

                            }

                        }

                    }
                }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview(){
    DashboardScreen(navController = rememberNavController())
}

