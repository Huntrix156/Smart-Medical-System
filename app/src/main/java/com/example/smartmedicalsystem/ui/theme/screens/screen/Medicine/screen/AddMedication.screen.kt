package com.example.smartmedicalsystem.ui.theme.screens.screen.AddMedicine.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AddMedicationScreen(navController: NavController){
    var drugname by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }


    Box(modifier = Modifier.padding(26.dp)){
        Spacer(modifier = Modifier.height(26.dp))
        Column() { Spacer(modifier = Modifier.height(26.dp))
            Text(text = "Medication Details", fontSize = 26.sp,
                fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Please provide accurate information for your prescription tracking.")
            OutlinedTextField(
                value = drugname,
                onValueChange = { drugname= it },
                label = { Text(text = "Enter Drug Name") },
                placeholder = {Text(text = "e.g,brufen")}
            )
            OutlinedTextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = {Text(text = "Enter Dosage")},
                placeholder = {Text(text = "e.g,Once,Twice,Thrice,")}
            )
            OutlinedTextField(
                value = frequency,
                onValueChange = { frequency = it },
                label = {Text(text="Enter Frequency")},
                placeholder = {Text(text = "e.g,Once Daily,Twice Daily,Thrice Daily")}
            )
            OutlinedTextField(
                value = duration,
                onValueChange = { duration =it },
                label = { Text(text = "Enter Duration Of Use")},
                placeholder = {Text(text = "e.g,One Day,One Month,A Year")}
            )

        }
    }

}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddMedicationScreenPreview(){
    AddMedicationScreen(navController = rememberNavController())
}
