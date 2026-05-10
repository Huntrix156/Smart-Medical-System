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