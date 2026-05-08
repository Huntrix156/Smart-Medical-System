package com.example.smartmedicalsystem.ui.theme.screens.Profile.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.smartmedicalsystem.data.ProfileViewModel
import com.example.smartmedicalsystem.models.ProfileModel
import com.example.smartmedicalsystem.navigation.ROUTE_ADD_MEDICINE
import com.example.smartmedicalsystem.navigation.ROUTE_PROFILE
import com.example.smartmedicalsystem.navigation.ROUTE_SETTINGS
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(navController: NavController,userId:String) {
    val profileViewModel: ProfileViewModel = viewModel()
    var user by remember { mutableStateOf<ProfileModel?>(null) }
    LaunchedEffect(userId) {
        val ref = FirebaseDatabase.getInstance()
            .getReference("Users").child(userId)
        val snapshot = ref.get().await()
        user = snapshot.getValue(ProfileModel::class.java)?.apply {
            id = userId
        }
    }
    if (user == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    var firstname by remember { mutableStateOf(user!!.firstname ?: "") }
    var lastname by remember { mutableStateOf(user!!.lastname ?: "") }
    var username by remember { mutableStateOf(user!!.username ?: "") }
    var email by remember { mutableStateOf(user!!.email ?: "") }
    var gender by remember { mutableStateOf(user!!.gender ?: "") }

    val selectedItem = remember { mutableStateOf(0) }

    val currentRoute = navController.currentBackStackEntryAsState()
        .value?.destination?.route


    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        it?.let { uri -> imageUri.value = uri }
    }

    val context = LocalContext.current

    var showExitDialog by remember { mutableStateOf(false) }




    Scaffold(

        // ✔ FIX: TopAppBar must be placed inside Scaffold parameter correctly
        topBar = {
            TopAppBar(
                title = { Text(text = "Nexora") },
                actions = {
                    IconButton(onClick = { /* logout logic */ }) {
                        //Top right exit icon//
                        Icon(
                            Icons.Default.ExitToApp,
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

        // ✔ FIX: bottomBar must use NavigationBar, NOT NavController (you used wrong type)
        bottomBar = {
            NavigationBar(containerColor = Color.Black) {

                NavigationBarItem(
//                    selected = selectedItem.value == 0,
                    selected = currentRoute == ROUTE_PROFILE,
                    onClick = {
                        navController.navigate(ROUTE_PROFILE) {
                            popUpTo(ROUTE_SETTINGS)
                            launchSingleTop = true
                        }
                    },

                    icon = {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    },
                    label = { Text("Settings") }
                )
                NavigationBarItem(
//                    selected = selectedItem.value == 1, // ✔ FIX: was incorrectly 0 again
//                    onClick = { selectedItem.value = 1 },


                    selected = currentRoute == ROUTE_ADD_MEDICINE,
                    onClick = {
                        navController.navigate(ROUTE_ADD_MEDICINE) {
                            popUpTo(ROUTE_SETTINGS)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(Icons.Filled.Add, contentDescription = null)
                    },
                    label = { Text("Add") }
                )
                NavigationBarItem(
//                    selected = selectedItem.value == 1, // ✔ FIX: was incorrectly 0 again
//                    onClick = { selectedItem.value = 1 },


                    selected = currentRoute == ROUTE_PROFILE,
                    onClick = {
                        navController.navigate(ROUTE_PROFILE) {
                            popUpTo(ROUTE_SETTINGS)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(Icons.Filled.Person, contentDescription = "Profile")
                    },
                    label = { Text("Profile") }
                )

//                NavigationBarItem(
//                    selected = selectedItem.value == 2, // ✔ FIX: was incorrectly 0 again
//                    onClick = { selectedItem.value = 2
//                        activity?.finish()},//Closes the app
//                    icon = {
//                        Icon(Icons.Filled.ExitToApp, contentDescription = "Exit")
//                    },
//                    label = { Text("Exit") }
//                )
                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = {
                        selectedItem.value = 2
                        showExitDialog = true // 👈 trigger dialog
                    },
                    icon = {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Exit")
                    },
                    label = { Text("Exit") }
                )
            }
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFFFCE4EC), Color(0xFFF8BBD0))
                    )
                )
                .padding(16.dp)
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Update Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF880E4F)
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Card(
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(6.dp),
                        modifier = Modifier
                            .size(140.dp)
                            .clickable { launcher.launch("image/*") }
                            .shadow(8.dp, CircleShape)
                    ) {
                        AnimatedContent(
                            targetState = imageUri.value,
                            label = "Image Picker Animation"
                        ) { targetUri ->
                            AsyncImage(
                                model = imageUri.value ?: user!!.imageUrl,
                                contentDescription = "Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    Text(
                        text = "Tap to change picture",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = 20.dp),
                        color = Color.LightGray,
                        thickness = 1.dp
                    )

                    val fieldModifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)

                    val fieldShape = RoundedCornerShape(14.dp)

                    OutlinedTextField(
                        value = firstname,
                        onValueChange = { firstname = it },
                        label = { Text("First Name") },
                        placeholder = { Text("e.g., John ") },
                        modifier = fieldModifier,
                        shape = fieldShape
                    )


                    OutlinedTextField(
                        value = lastname,
                        onValueChange = { lastname = it },
                        label = { Text("lastname") },
                        placeholder = { Text("e.g., Doe") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = fieldModifier,
                        shape = fieldShape
                    )
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        placeholder = { Text("e.g., John") },
                        modifier = fieldModifier,
                        shape = fieldShape
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("example@gmail.com") },
                        placeholder = { Text("e.g.,Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = fieldModifier,
                        shape = fieldShape
                    )

                    OutlinedTextField(
                        value = gender,
                        onValueChange = { gender = it },
                        label = { Text("Which Gender") },
                        placeholder = { Text("e.g, Male/Female") },
                        modifier = fieldModifier.height(120.dp),
                        shape = fieldShape,
                        maxLines = 5
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { navController.popBackStack() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.LightGray
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.width(140.dp)
                        ) {
                            Text("Go Back", color = Color.DarkGray)
                        }

                        Button(
                            onClick = {
                                profileViewModel.updateUser(
                                    userId,
                                    imageUri.value,
                                    firstname = firstname,
                                    lastname = lastname,
                                    username = username,
                                    email = email,
                                    gender = gender,
                                    context = context,
                                    navController = navController
                                )


                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD81B60)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.width(140.dp)
                        ) {
                            Text("Update", color = Color.White)
                        }

                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
