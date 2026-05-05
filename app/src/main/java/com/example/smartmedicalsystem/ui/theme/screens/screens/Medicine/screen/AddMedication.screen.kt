package com.example.smartmedicalsystem.ui.theme.screens.screens.AddMedicine.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartmedicalsystem.navigation.ROUTE_MEDICATION_SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationScreen(navController: NavController){
    var drugname by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }



    val selectedItem = remember { mutableIntStateOf(0) }



    Scaffold(

        // 🔷 TOP BAR
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add Medication")
                },
                // ✅ Navigation icon added here
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(ROUTE_MEDICATION_SCREEN) // 🔁 change to your route
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go to Dashboard",
                            tint = Color.White
                        )
                    }
                },

                actions = {
                    IconButton(onClick = {
                        // logout
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        },

        // 🔷 BOTTOM BAR (FIXED)
        bottomBar = {
            NavigationBar(containerColor = Color.Black) {

                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = { selectedItem.value = 0 },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )

                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = { selectedItem.value = 1 },
                    icon = { Icon(Icons.Default.FilePresent, contentDescription = "Records") },
                    label = { Text("Records") }
                )

                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = { selectedItem.value = 2 },
                    icon = { Icon(Icons.Default.CalendarToday, contentDescription = "Appointments") },
                    label = { Text("Appointments") }
                )

                NavigationBarItem(
                    selected = selectedItem.value == 3,   // FIXED (was duplicate 2)
                    onClick = { selectedItem.value = 3 },
                    icon = { Icon(Icons.Default.AccountBox, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }

    ) { innerPadding ->

        // 🔷 CONTENT

    Box(
        modifier = Modifier.fillMaxHeight()
        .padding(26.dp)


    ){

    }
        Spacer(modifier = Modifier.height(26.dp))

        Column(modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment= Alignment.CenterHorizontally


        )
        {
            Spacer(modifier = Modifier.height(26.dp))

            Text(text = "Medication Details",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black)
            Text(text = "Please provide accurate information for your prescription tracking.",
            modifier= Modifier.padding(8.dp))

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
