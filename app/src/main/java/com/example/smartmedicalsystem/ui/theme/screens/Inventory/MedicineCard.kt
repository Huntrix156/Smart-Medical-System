package com.example.smartmedicalsystem.ui.theme.screens.Inventory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartmedicalsystem.models.medication.Medicine
import com.example.smartmedicalsystem.ui.theme.screens.Inventory.InvertoryUtils.daysRemaining
import com.example.smartmedicalsystem.ui.theme.screens.Inventory.InvertoryUtils.expiryStatusColor
import com.example.smartmedicalsystem.ui.theme.screens.Inventory.InvertoryUtils.stockStatusColor

@Composable
fun MedicineCard(medicine: Medicine) {

    val stockColor = stockStatusColor(medicine.stock, medicine.minStock)
    val expiryColor = expiryStatusColor(medicine.expiryDate)
    val daysLeft = daysRemaining(medicine.expiryDate)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(medicine.name, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Stock Level
            Text("Stock: ${medicine.stock} (Min: ${medicine.minStock})", color = stockColor)

            LinearProgressIndicator(
                progress = (medicine.stock / (medicine.minStock * 2f)).coerceIn(0f, 1f),
                color = stockColor,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Expiry
            Text(
                "Expires in $daysLeft days",
                color = expiryColor
            )
        }
    }
}