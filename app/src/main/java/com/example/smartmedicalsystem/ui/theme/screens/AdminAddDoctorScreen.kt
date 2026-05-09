package com.example.smartmedicalsystem.ui.theme.Admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAddDoctorScreen(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF0F4F8))
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Register New Doctor",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color(0xFF1565C0)
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Card(
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(containerColor = Color.White),
//            elevation = CardDefaults.cardElevation(4.dp)
//        ) {
//            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
//
//                OutlinedTextField(
//                    value = firstName,
//                    onValueChange = { firstName = it },
//                    label = { Text("First Name") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                OutlinedTextField(
//                    value = lastName,
//                    onValueChange = { lastName = it },
//                    label = { Text("Last Name") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                OutlinedTextField(
//                    value = email,
//                    onValueChange = { email = it },
//                    label = { Text("Email Address") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                OutlinedTextField(
//                    value = password,
//                    onValueChange = { password = it },
//                    label = { Text("Temporary Password") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                Text("Gender", fontWeight = FontWeight.SemiBold)
//                Row {
//                    RadioButton(selected = gender == "Male", onClick = { gender = "Male" })
//                    Text("Male", modifier = Modifier.align(Alignment.CenterVertically))
//                    Spacer(modifier = Modifier.width(10.dp))
//                    RadioButton(selected = gender == "Female", onClick = { gender = "Female" })
//                    Text("Female", modifier = Modifier.align(Alignment.CenterVertically))
//                }
//
//                Button(
//                    onClick = {
////                        adminViewModel.addDoctor(
////                            firstName = firstName,
////                            lastName = lastName,
////                            email = email,
////                            password = password,
////                            gender = gender,
////                            context = context,
////                            navController = navController
////                        )
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(55.dp),
//                    shape = RoundedCornerShape(12.dp)
//                ) {
//                    Text("Register Doctor")
//                }
//                TextButton(
//                    onClick = { navController.popBackStack() },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Back to Dashboard")
//                }
//            }
//        }
//    }
//}




    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "Register New Doctor",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF004D40)
                )
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F4F8))
                .padding(paddingValues)
                .padding(16.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF004D40),
                            unfocusedBorderColor = Color.Gray
                        )

                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF004D40),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Temporary Password") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Gender",
                        fontWeight = FontWeight.SemiBold
                    )

                    Row {

                        RadioButton(
                            selected = gender == "Male",
                            onClick = { gender = "Male" }
                        )

                        Text(
                            text = "Male",
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        RadioButton(
                            selected = gender == "Female",
                            onClick = { gender = "Female" }
                        )

                        Text(
                            text = "Female",
                            modifier = Modifier.align(Alignment.CenterVertically),

                        )
                    }

                    Button(
                        onClick = {

                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),

                        shape = RoundedCornerShape(12.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF004D40)
                        )
                    ) {

                        Text(
                            text = "Register Doctor",
                            color = Color.White
                        )
                    }

                    TextButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("Back to Dashboard")
                    }
                }
            }
        }
    }
}