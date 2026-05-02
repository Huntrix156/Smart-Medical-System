package com.example.nexora.ui.theme.screens.medicine.screen

import android.R.attr.padding
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.example.smartmedicalsystem.navigation.ROUTE_MEDICATION_SCREEN

//
//import com.example.nexora.data.MedicineViewModel
//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.Button
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import coil.compose.rememberAsyncImagePainter
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddMedicineScreen(navController: NavController){
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ){uri: Uri? ->
//        imageUri = uri
//    }
//    var name by remember { mutableStateOf("") }
//    var dosage by remember { mutableStateOf("") }
//    var startDate by remember { mutableStateOf("") }
//    var endDate by remember { mutableStateOf("") }
//    var frequency by remember { mutableStateOf("") }
//
//
//    val medicineViewModel: MedicineViewModel =viewModel()  //this brings the medicine viewmodel to the screen from the PatientViewModel
//    val context = LocalContext.current
//
//    Scaffold(topBar = {
//        TopAppBar(title = { Text(text="Add Patient") },
//            colors = TopAppBarDefaults.topAppBarColors(
//                containerColor = Color.Blue,
//                titleContentColor = Color.White))
//    })
//    {padding ->
//        Column(
//            modifier= Modifier.padding(padding)
//                .fillMaxSize()
//                .padding(16.dp)
//        )
//        {
//            Box(modifier = Modifier.size(120.dp)
//                .align(Alignment.CenterHorizontally),
//                contentAlignment = Alignment.Center)
//            {
//                //image section//
//                if(imageUri !=null){
//                    Image(painter = rememberAsyncImagePainter(imageUri),
//                        contentDescription = null,
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.Crop)
//                }else{
//                    Icon(Icons.Default.Person,
//                        contentDescription = null,
//                        modifier = Modifier.size(80.dp)
//                    )
//                }
//            }
//            Button(onClick = {launcher.launch("image/*")},
//                modifier = Modifier.align(Alignment.CenterHorizontally))
//            {
//                Text(text = "Select Image")
//            }
//            OutlinedTextField(
//                value = name,
//                onValueChange = { name = it },
//                label = { Text(text = "Medicine Name") },
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//
//            OutlinedTextField(
//                value = dosage,
//                onValueChange = { dosage = it },
//                label = { Text(text = "Dosage") },
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = startDate,
//                onValueChange = { startDate = it },
//                label = { Text(text = "Starting Time") },
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//
//            OutlinedTextField(
//                value = endDate,
//                onValueChange = { endDate = it },
//                label = { Text(text = "End date Time") },
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = frequency,
//                onValueChange = { frequency = it },
//                label = { Text(text = "State Frequency") },
//                modifier = Modifier
//                    .fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(onClick = {
//                medicineViewModel.uploadMedicine(
//                    imageUri = imageUri,
//                    name = name,
//                    dosage = dosage,
//                    startDate = startDate,
//                    endDate = endDate,
//                    frequency =  frequency,
//                    context=context,
//                    navController = navController
//                )
//
//            }, modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(10.dp))
//            {
//                Text(text = "Save Medication")
//            }
//        }
//
//    }
//
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AddPatientScreenPreview(){
//    AddMedicineScreen(rememberNavController())
//}






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

    // =========================
    // ✔ NEW: FORM VALIDATION STATE
    // =========================
    var isLoading by remember { mutableStateOf(false) } // ✔ NEW
    var errorMessage by remember { mutableStateOf<String?>(null) } // ✔ NEW


    val selectedItem = remember { mutableIntStateOf(0) }




        Scaffold(

            // 🔷 TOP BAR
            topBar = {
                TopAppBar(
                    title = {
                        Text("Booking Appointment")
                    },

                    // ✅ Navigation (BACK)
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

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },

            // 🔷 BOTTOM BAR
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

            // ✅ Wrap everything properly
            Column(
                modifier = Modifier
                    .background(Color(0xFFF3F5F9))
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // =========================
                // IMAGE PICKER
                // =========================
                Box(
                    modifier = Modifier.size(80.dp),
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
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }

                Button(onClick = { launcher.launch("image/*") }) {
                    Text("Select Image")
                }

                // =========================
                // FORM
                // =========================

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Medicine Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = dosage,
                    onValueChange = { dosage = it },
                    label = { Text("Dosage") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text("Start Date/Time") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text("End Date/Time") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = frequency,
                    onValueChange = { frequency = it },
                    label = { Text("Frequency (e.g. 08:00,14:00)") },
                    modifier = Modifier.fillMaxWidth()
                )

                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // =========================
                // SAVE BUTTON
                // =========================
                Button(
                    onClick = {
                        if (name.isBlank() || dosage.isBlank()) {
                            errorMessage = "Name and dosage are required"
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
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Save Medication")
                    }
                }
            }
//        }
    }
}