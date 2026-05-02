//package com.example.smartmedicalsystem.ui.theme.screens.screen.Medicine.screen

package com.example.smartmedicalsystem.ui.theme.screens.screen.Medicine.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartmedicalsystem.navigation.ROUTE_ADD_MEDICATION
import com.example.smartmedicalsystem.navigation.ROUTE_ADD_MEDICINE
import com.example.smartmedicalsystem.navigation.ROUTE_INVENTORY_SCREEN
import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN
import com.example.smartmedicalsystem.navigation.ROUTE_MEDICATION_REMINDER_COMPONENT
import com.example.smartmedicalsystem.navigation.ROUTE_MEDICINE_LIST
import com.example.smartmedicalsystem.ui.theme.screens.screen.CenterCardText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationScreen(navController: NavController) {
    val selectedItem = remember { mutableIntStateOf(0) }


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
                    selected = selectedItem.value == 0,
                    onClick = { selectedItem.value = 0 },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )

                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = { selectedItem.value = 1 },
                    icon = { Icon(Icons.Default.FilePresent, contentDescription = "Records") },
                    label = { Text("Records") }
                )

                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = { selectedItem.value = 2 },
                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Appointments") },
                    label = { Text("Appointments") }
                )

                NavigationBarItem(
                    selected = selectedItem.value == 3,   // FIXED (was duplicate 2)
                    onClick = { selectedItem.value = 3 },
                    icon = { Icon(Icons.Default.AccountBox, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }

    ) { innerPadding ->

        // 🔷 CONTENT
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    // ✅ Add Medication Card
                    Card(
                        onClick = { navController.navigate(ROUTE_ADD_MEDICINE) },
                        modifier = Modifier.weight(1f).size(140.dp),

                        colors = CardDefaults.cardColors(containerColor = Color.Blue),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(5.dp)
                    ) {
                        CenterCardText(
                            title = "Add Medication",
                            subtitle = "Create new entry"
                        )
                    }

                    // ✅ Medication Reminder Card
                    Card(
                        onClick = { navController.navigate(ROUTE_MEDICATION_REMINDER_COMPONENT) },
                        modifier = Modifier.size(140.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Blue),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(5.dp)
                    ) {
                        CenterCardText(
                            title = "Medication Reminder",
                            subtitle = "Set alerts"
                        )
                    }
                }
                Row() {
                    // ✅ Inventory Card
                    Card(
                        onClick = { navController.navigate(ROUTE_INVENTORY_SCREEN) },
                        modifier = Modifier.weight(1f).size(140.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Blue),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(5.dp)
                    ) {
                        CenterCardText(
                            title = "Inventory",
                            subtitle = "View stock"
                        )
                    }
                    Card(
                        onClick = { navController.navigate(ROUTE_MEDICINE_LIST) },
                        modifier = Modifier.weight(1f).size(140.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Blue),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(5.dp)
                    ) {
                        CenterCardText(
                            title = "Medicine List",
                            subtitle = "View medicine"
                        )
                    }
                }
            }
        }
    }

}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MedicationScreenPreview() {
    MedicationScreen(navController = rememberNavController())
}