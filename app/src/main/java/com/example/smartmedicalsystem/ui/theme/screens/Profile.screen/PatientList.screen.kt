package com.example.smartmedicalsystem.ui.theme.screens.Profile.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// ----------------------------------------------------
// DATA MODEL
// ----------------------------------------------------

data class Patient(
    val id: String,
    val name: String,
    val age: Int,
    val gender: String,
    val bloodGroup: String,
    val condition: String
)

// ----------------------------------------------------
// MAIN SCREEN
// ----------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(navController: NavController) {

    // ----------------------------------------------------
    // SAMPLE PATIENT DATA
    // ----------------------------------------------------

    val patientList = remember {

        mutableStateListOf(

            Patient(
                id = "PAT-1001",
                name = "John Doe",
                age = 29,
                gender = "Male",
                bloodGroup = "O+",
                condition = "Malaria"
            ),

            Patient(
                id = "PAT-1002",
                name = "Mary Wanjiku",
                age = 34,
                gender = "Female",
                bloodGroup = "A+",
                condition = "Diabetes"
            ),

            Patient(
                id = "PAT-1003",
                name = "Brian Otieno",
                age = 17,
                gender = "Male",
                bloodGroup = "B+",
                condition = "Asthma"
            ),

            Patient(
                id = "PAT-1004",
                name = "Faith Njeri",
                age = 45,
                gender = "Female",
                bloodGroup = "AB+",
                condition = "Hypertension"
            )
        )
    }

    // ----------------------------------------------------
    // SEARCH + FILTER STATES
    // ----------------------------------------------------

    var searchText by remember {
        mutableStateOf("")
    }

    var selectedFilter by remember {
        mutableStateOf("All")
    }

    val filters = listOf(
        "All",
        "Male",
        "Female"
    )

    // ----------------------------------------------------
    // FILTER LOGIC
    // ----------------------------------------------------

    val filteredPatients = patientList.filter { patient ->

        val matchesSearch =
            patient.name.contains(searchText, true) ||
                    patient.id.contains(searchText, true)

        val matchesFilter =
            selectedFilter == "All" ||
                    patient.gender == selectedFilter

        matchesSearch && matchesFilter
    }

    // ----------------------------------------------------
    // UI
    // ----------------------------------------------------

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())

    ) {

        // ----------------------------------------------------
        // TITLE
        // ----------------------------------------------------

        Text(
            text = "Patient List",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ----------------------------------------------------
        // SEARCH FIELD
        // ----------------------------------------------------

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            label = {
                Text("Search Patient")
            },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ----------------------------------------------------
        // FILTER CHIPS
        // ----------------------------------------------------

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            filters.forEach { filter ->

                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = {
                        selectedFilter = filter
                    },
                    label = {
                        Text(filter)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ----------------------------------------------------
        // PATIENT LIST
        // ----------------------------------------------------

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(filteredPatients) { patient ->

                PatientCard(
                    patient = patient,

                    // OPEN PROFILE
                    onViewProfile = {

                        // Example route
                        navController.navigate(
                            "patient_profile/${patient.id}"
                        )
                    },

                    // OPEN PRESCRIPTION SCREEN
                    onWritePrescription = {

                        navController.navigate(
                            "write_prescription/${patient.id}"
                        )
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

// ----------------------------------------------------
// PATIENT CARD
// ----------------------------------------------------

@Composable
fun PatientCard(
    patient: Patient,
    onViewProfile: () -> Unit,
    onWritePrescription: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(22.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(18.dp)
        ) {

            // ----------------------------------------------------
            // TOP ROW
            // ----------------------------------------------------

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(55.dp)
                            .background(
                                MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.1f
                                ),
                                RoundedCornerShape(50.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(
                            text = patient.name,
                            fontSize = 20.sp
                        )

                        Text(
                            text = patient.id,
                            color = Color.Gray
                        )
                    }
                }

                Text(
                    text = patient.gender,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ----------------------------------------------------
            // DETAILS
            // ----------------------------------------------------

            Text("Age: ${patient.age}")
            Text("Blood Group: ${patient.bloodGroup}")
            Text("Condition: ${patient.condition}")

            Spacer(modifier = Modifier.height(18.dp))

            // ----------------------------------------------------
            // ACTION BUTTONS
            // ----------------------------------------------------

            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = onViewProfile,
                    modifier = Modifier.weight(1f)
                ) {

                    Icon(
                        Icons.Default.Person,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Profile")
                }

                Button(
                    onClick = onWritePrescription,
                    modifier = Modifier.weight(1f)
                ) {

                    Icon(
                        Icons.Default.Description,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Prescription")
                }
            }
        }
    }
}