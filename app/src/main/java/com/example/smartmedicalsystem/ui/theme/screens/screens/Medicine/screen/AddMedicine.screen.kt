package com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.smartmedicalsystem.data.MedicineViewModel
import com.example.smartmedicalsystem.ui.theme.SecondaryGreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineScreen(navController: NavController) {

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val scrollState = rememberScrollState()


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("") }

    val medicineViewModel: MedicineViewModel = viewModel()
    val context = LocalContext.current


    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    val selectedItem = remember { mutableIntStateOf(0) }




        Scaffold(

            topBar = {
                TopAppBar(
                    title = {
                        Text("Add Medication",fontWeight = FontWeight.Bold, color = Color.White)
                    },

                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimary
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
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(containerColor = SecondaryGreen),
                )
            },

            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {

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
                        icon = {
                            Icon(
                                Icons.Default.CalendarToday,
                                contentDescription = "Appointments"
                            )
                        },
                        label = { Text("Appointments") }
                    )

                    NavigationBarItem(
                        selected = selectedItem.value == 3,
                        onClick = { selectedItem.value = 3 },
                        icon = { Icon(Icons.Default.AccountBox, contentDescription = "Profile") },
                        label = { Text("Profile") }
                    )
                }
            }

        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .background(Color(0xFFEAF2FF))
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Add Medication",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )

                Text(
                    text = "Fill in medicine details for scheduling reminders",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(16.dp)
                ) {

                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color(0xFFF1F5FF), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (imageUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(imageUri),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp),
                                    tint = Color(0xFF1976D2)
                                )
                            }
                        }

                        Button(
                            onClick = { launcher.launch("image/*") },
                            shape = RoundedCornerShape(12.dp)
                       ) {
                            Text("Upload Medicine Image")
                        }



                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Medicine Name") },
                            placeholder = {Text(text = "e.g. Paracetamol")},
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = dosage,
                            onValueChange = { dosage = it },
                            label = { Text("Dosage (e.g. 1 tablet)") },
                            placeholder = {Text(text = "e.g. 1 tablet")},
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = startDate,
                            onValueChange = { startDate = it },
                            label = { Text("Start Time") },
                            placeholder = {Text(text = "e.g. 08:00")},
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = endDate,
                            onValueChange = { endDate = it },
                            label = { Text("End Time") },
                            placeholder = {Text(text = "e.g. 14:00")},
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = frequency,
                            onValueChange = { frequency = it },
                            label = { Text("Frequency (e.g. 08:00 / 14:00)") },
                            placeholder = {Text(text = "e.g. 08:00 / 14:00")},
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )

                        errorMessage?.let {
                            Text(
                                text = it,
                                color = Color.Red,
                                fontSize = 12.sp
                            )
                        }


                        Button(
                            onClick = {
                                if (name.isBlank() || dosage.isBlank()) {
                                    errorMessage = "Medicine name and dosage required"
                                    return@Button
                                }

                                isLoading = true

                                medicineViewModel.uploadMedicine(
                                    imageUri,
                                    name,
                                    dosage,
                                    startDate,
                                    endDate,
                                    frequency,
                                    context,
                                    navController
                                )

                                isLoading = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White
                                )
                            } else {
                                Text("Save Medication")
                            }
                        }
                    }
                }
            }
        }
}

