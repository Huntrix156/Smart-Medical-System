package com.example.smartmedicalsystem.ui.theme.screens.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmergencyScreen(
    onSOSClick: () -> Unit,
    onCallContact: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        // 🔴 SOS BUTTON
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onSOSClick,
                modifier = Modifier
                    .size(180.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                shape = CircleShape
            ) {
                Text(
                    text = "SOS",
                    color = Color.White,
                    fontSize = 32.sp
                )
            }
        }

        // 📞 EMERGENCY CONTACTS
        Column {
            Text("Emergency Contacts", color = Color.White)

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { onCallContact("0712345678") }) {
                    Text("Mom")
                }

                Button(onClick = { onCallContact("0798765432") }) {
                    Text("Doctor")
                }

                Button(onClick = { onCallContact("112") }) {
                    Text("Ambulance")
                }
            }
        }

        // 🏥 MEDICAL INFO
        Card(
            colors = CardDefaults.cardColors(Color.DarkGray)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Blood Group: O+", color = Color.White)
                Text("Allergies: Penicillin", color = Color.White)
                Text("Condition: Asthma", color = Color.White)
                Text("Medication: Inhaler", color = Color.White)
            }
        }
    }
}