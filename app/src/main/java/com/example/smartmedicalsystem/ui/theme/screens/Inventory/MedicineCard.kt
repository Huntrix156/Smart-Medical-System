package com.example.smartmedicalsystem.ui.theme.screens.Inventory

import androidx.compose.runtime.Composable

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