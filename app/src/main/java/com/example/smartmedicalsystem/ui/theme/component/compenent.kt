package com.example.nexora.ui.theme.screens.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartmedicalsystem.models.FacilityModel

@Composable
fun Label(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        color = Color.Gray,
        modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
    )
}

@Composable
fun FacilityItem(
    facility: FacilityModel,
    onClick: (FacilityModel) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(facility) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = facility.name,
                fontWeight = FontWeight.Bold
            )
            Text(text = facility.type)
            Text(text = facility.specialty)
        }
    }
}