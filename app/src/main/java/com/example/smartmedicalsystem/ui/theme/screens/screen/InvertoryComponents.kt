package com.example.smartmedicalsystem.ui.theme.screens.screen

import androidx.compose.ui.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmedicalsystem.models.Medicine
import com.example.smartmedicalsystem.ui.theme.screens.Inventory.InventoryScreen
import com.example.smartmedicalsystem.ui.theme.screens.screen.InvertoryUtils.daysRemaining
import com.example.smartmedicalsystem.ui.theme.screens.screen.InvertoryUtils.expiryStatusColor
import com.example.smartmedicalsystem.ui.theme.screens.screen.InvertoryUtils.stockStatusColor
import java.time.LocalDate

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

            Text(
                "Stock: ${medicine.stock} (Min: ${medicine.minStock})",
                color = stockColor
            )

            LinearProgressIndicator(
                progress = (medicine.stock / (medicine.minStock * 2f)).coerceIn(0f, 1f),
                color = stockColor,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Expires in $daysLeft days",
                color = expiryColor
            )
        }
    }
}


@Composable
fun RefillAlertCard(medicine: Medicine) {

    val refillAmount = (medicine.minStock * 2) - medicine.stock

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text("⚠ Refill Needed", color = Color.Red)

            Text(medicine.name)

            Text("Current: ${medicine.stock} | Min: ${medicine.minStock}")
            Text("Suggested Refill: $refillAmount")

            Row {
                Button(onClick = { /* reorder logic */ }) {
                    Text("Reorder")
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(onClick = { /* ignore */ }) {
                    Text("Ignore")
                }
            }
        }
    }
}


@Composable
fun SummaryCard(title: String, value: String) {
    Card(
        modifier = Modifier.weight(1f),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title)
            Text(value, style = MaterialTheme.typography.titleLarge)
        }
    }
}


@Composable
fun CenterCardText(title: String, subtitle: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, color = Color.White, fontSize = 18.sp)
        Text(subtitle, color = Color.White, fontSize = 12.sp)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewInventory() {
    val sample = listOf(
        Medicine("Paracetamol", 30, 50, LocalDate.now().plusDays(20)),
        Medicine("Amoxicillin", 5, 40, LocalDate.now().plusDays(10)),
        Medicine("Ibuprofen", 100, 50, LocalDate.now().plusDays(120))
    )

    InventoryScreen(sample)
}


@Composable
fun MenuCard(title: String, onClick: () -> Unit) {

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}