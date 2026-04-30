package com.example.smartmedicalsystem.ui.theme.screens.screen.Medicine.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartmedicalsystem.models.Medicine
import com.example.smartmedicalsystem.ui.theme.screens.screen.InvertoryUtils.daysRemaining
import com.example.smartmedicalsystem.ui.theme.screens.screen.InvertoryUtils.expiryStatusColor
import com.example.smartmedicalsystem.ui.theme.screens.screen.InvertoryUtils.stockStatusColor

@Composable
fun MedicineCard(medicine: Medicine) {

    val stockColor = stockStatusColor(medicine.stock, medicine.minStock)
    val expiryColor = expiryStatusColor(medicine.expiryDate)
    val days = daysRemaining(medicine.expiryDate)

    Card {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(medicine.name)

            // ✅ THIS IS WHERE YOUR LINES GO
            Text("Stock: ${medicine.stock}", color = stockColor)

            Text("Expires in $days days", color = expiryColor)
        }
    }
}