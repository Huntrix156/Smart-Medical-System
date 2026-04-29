package com.example.smartmedicalsystem.ui.theme.screens.screen.Medicine.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartmedicalsystem.navigation.ROUTE_ADD_MEDICATION
import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN

@Composable
fun MedicationScreen(navController: NavController){
    Box(){
        Column(){
                Card(
                    onClick = {  navController.navigate(ROUTE_ADD_MEDICATION)},
                    modifier = Modifier.size(100.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Blue),
                    elevation = CardDefaults.cardElevation(5.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Add Medication", color = Color.White, fontSize = 30.sp)
                        Text("Add Medication", color = Color.White, fontSize = 16.sp)
                    }
                    Card(
                        onClick = {  navController.navigate(ROUTE_LOGIN)},
                        modifier = Modifier.size(100.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Blue),
                        elevation = CardDefaults.cardElevation(5.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(" Medication Reminder", color = Color.White, fontSize = 30.sp)
                            Text(" Medication Reminder", color = Color.White, fontSize = 16.sp)
                        }
                    }
                }
                        Card(
                            onClick = {  navController.navigate(ROUTE_LOGIN)},
                            modifier = Modifier.size(100.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Blue),
                            elevation = CardDefaults.cardElevation(5.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(" Inventory", color = Color.White, fontSize = 30.sp)
                                Text(" Inventory", color = Color.White, fontSize = 16.sp)
                            }
                }

        }
    }

}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MedicationScreenPreview(){
    MedicationScreen(navController = rememberNavController())
}