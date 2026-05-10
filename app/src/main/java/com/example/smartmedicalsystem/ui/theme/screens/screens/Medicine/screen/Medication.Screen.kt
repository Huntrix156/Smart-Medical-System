package com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartmedicalsystem.navigation.*

private val HospitalTeal = Color(0xFF00604E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationScreen(navController: NavController,  onLogout: () -> Unit) {




    Scaffold(
        topBar = {
            TopAppBar(

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },

                title = {
                    Text(
                        "Medication Management",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HospitalTeal
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Health Utilities",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MedicationMenuCard("Add Medication", "Create new entry", Modifier.weight(1f)) {
                    navController.navigate(ROUTE_ADD_MEDICINE)
                }
                MedicationMenuCard("Reminders", "Set alerts", Modifier.weight(1f)) {
                    navController.navigate(ROUTE_REMINDER)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MedicationMenuCard("Medicine List", "View stock", Modifier.weight(1f)) {
                    navController.navigate(ROUTE_MEDICINE_LIST)
                }
                MedicationMenuCard("Update Medication", "Search meds", Modifier.weight(1f)) {
                    navController.navigate(ROUTE_UPDATE_MEDICATION)
                }
            }
        }
    }
}

@Composable
fun MedicationMenuCard(title: String, subtitle: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier.height(140.dp),
        colors = CardDefaults.cardColors(containerColor = HospitalTeal),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        }
    }
}


