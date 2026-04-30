//package com.example.smartmedicalsystem.ui.theme.screens.screen.Medicine.screen

package com.example.smartmedicalsystem.ui.theme.screens.screen.Medicine.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartmedicalsystem.navigation.ROUTE_ADD_MEDICATION
import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN
import com.example.smartmedicalsystem.ui.theme.screens.screen.CenterCardText

@Composable
fun MedicationScreen(navController: NavController) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ✅ Add Medication Card
            Card(
                onClick = { navController.navigate(ROUTE_ADD_MEDICATION) },
                modifier = Modifier.size(140.dp),
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
                onClick = { navController.navigate(ROUTE_LOGIN) },
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

            // ✅ Inventory Card
            Card(
                onClick = { navController.navigate(ROUTE_LOGIN) },
                modifier = Modifier.size(140.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Blue),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                CenterCardText(
                    title = "Inventory",
                    subtitle = "View stock"
                )
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MedicationScreenPreview() {
    MedicationScreen(navController = rememberNavController())
}