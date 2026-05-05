package com.example.smartmedicalsystem.ui.theme.screens.Inventory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartmedicalsystem.models.medication.Medicine
import com.example.smartmedicalsystem.ui.theme.screens.Inventory.InvertoryUtils.daysRemaining
import com.example.smartmedicalsystem.ui.theme.screens.screens.RefillAlertCard
import com.example.smartmedicalsystem.ui.theme.screens.screens.SummaryCard
import kotlin.collections.filter

//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.Card
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//
//@Composable
//fun PharmacyManagementScreen(navController: NavController){
//    var stocklevels by remember { mutableStateOf("") }
//    var expirydates by remember { mutableStateOf("") }
//    var refillalerts by remember { mutableStateOf("") }
//
//   Column(modifier = Modifier.padding(26.dp)) {
//       Row() {
//           Card() {
//               Text(text = "All")
//           }
//           Spacer(modifier = Modifier.width(26.dp))
//           Card(modifier = Modifier.background(color = Color.Blue)) {
//               Text(text = "Low Stock",
//                   color = Color.Red)
//           }
//           Spacer(modifier = Modifier.width(26.dp))
//
//           Card() {
//               Text(text = "Expiring Soon")
//           }
//
//       }
//       Row(modifier = Modifier.padding(26.dp)) {
//           Text(text = "Current Stock",
//               modifier = Modifier.weight(1f),
//               fontWeight= FontWeight.Bold, fontSize = (20.sp))
//           Card(modifier = Modifier.weight(1f)) {
//               Text(text = "Low Stock")
//           }
//       }
//       Spacer(modifier = Modifier.height(26.dp))
//       Box(modifier = Modifier.fillMaxSize().padding(26.dp)) {
//           Spacer(modifier = Modifier.height(26.dp))
//           Spacer(modifier = Modifier.height(30.dp))
//           Column() {
//               OutlinedTextField(
//                   value = stocklevels,
//                   onValueChange = { stocklevels = it },
//                   label = { Text(text = "Stock levels") },
//                   placeholder = { Text(text = "Stock levels") }
//               )
//               Spacer(modifier = Modifier.height(30.dp))
//
//
//               OutlinedTextField(
//                   value = expirydates,
//                   onValueChange = { expirydates = it },
//                   label = { Text(text = "Expiry Dates") },
//                   placeholder = { Text(text = "e.g,12/02/2026") }
//               )
//               Spacer(modifier = Modifier.height(30.dp))
//
//               Row() { }
//
//
//               OutlinedTextField(
//                   value = refillalerts,
//                   onValueChange = { refillalerts = it },
//                   label = { Text(text = "Day To Notified For Refill") },
//                   placeholder = { Text(text = "e.g,12/02/2026") }
//               )
//           }
//       }
//
//
//   }
//
//}
//
//
//
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun PharmacyManagementScreenPreview(){
//    PharmacyManagementScreen(navController = rememberNavController())
//}


@Composable
fun InventoryScreen(navController: NavController,medicines: List<Medicine>) {

    val lowStockItems = medicines.filter { it.stock <= it.minStock }
    val expiringSoon = medicines.filter { daysRemaining(it.expiryDate) <= 30 }

    Column(modifier = Modifier.padding(16.dp)) {

        Text("Pharmacy Inventory", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Summary Cards
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SummaryCard("Total", medicines.size.toString())
            SummaryCard("Low Stock", lowStockItems.size.toString())
            SummaryCard("Expiring", expiringSoon.size.toString())
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Refill Alerts Section
        if (lowStockItems.isNotEmpty()) {
            Text("Refill Alerts", style = MaterialTheme.typography.titleMedium)

            lowStockItems.forEach {
                RefillAlertCard(it)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Inventory List
        LazyColumn {
            items(medicines) { med ->
                MedicineCard(med)
            }
        }
    }
}