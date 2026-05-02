package com.example.smartmedicalsystem.ui.theme.screens

import android.R.attr.background
import android.R.id.background
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartmedicalsystem.data.AuthViewModel
import com.example.smartmedicalsystem.navigation.ROUTE_MAIN_DASHBOARD
import com.example.smartmedicalsystem.navigation.ROUTE_REGISTER


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController,onRoleSelected: (String) -> Unit){
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val roles = listOf("Patient", "Doctor", "Admin")
    var selectedRole by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    //--------Error Name -----------//
    var errorMessage by remember { mutableStateOf("") }
//    val errors = mutableListOf<String>()
//
//    if (email.isEmpty()) errors.add("Email is required")
//    if (password.isEmpty()) errors.add("Password is required")
//    if (selectedRole.isEmpty()) errors.add("Role is required")
//
//    errorMessage = errors.joinToString("\n")
//
//    if (errors.isEmpty()) {
//        onRoleSelected(selectedRole)
//    }



     //======email validation==========//


    Box(modifier = Modifier.padding(26.dp)
        .background(Color(0xFFEDE7F6),
            shape = RoundedCornerShape(10.dp))
        ){

        Column(   modifier = Modifier
//            .padding(innerPadding)
//            .background(Color(0xFFEDE7F6))
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly)
        { Spacer(modifier = Modifier.height(50.dp))
            Text(text = "Welcome Back",fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(text = "Please enter your credentials to access your account")
            // EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email",color = MaterialTheme.colorScheme.onSurface) },
                placeholder = {Text("e.g, example@gmail.com",color = MaterialTheme.colorScheme.onSurface)},
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = email.isNotEmpty() &&
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(),
                supportingText = {
                    if (email.isNotEmpty() &&
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    ) {
                        Text("Invalid email format", color = Color.Red)
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Red,

            ),
                shape = RoundedCornerShape(8.dp)
            )

            // PASSWORD
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password",color = MaterialTheme.colorScheme.onSurface) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)

            )
            // Role dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedRole.ifEmpty { "Select role" },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Role",color = MaterialTheme.colorScheme.onSurface) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)

                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    roles.forEach { role ->
                        DropdownMenuItem(
                            text = { Text(role) },
                            onClick = {
                                selectedRole = role
                                expanded = false
                                errorMessage = ""
                            }
                        )
                    }
                }
            }

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    authViewModel.login(
                        email = email,
                        password = password,
                        gender =gender,
                        navController = navController,
                        context = context
                    )
                    when {
                        email.isEmpty() || password.isEmpty() -> errorMessage = "Please fill in all fields."
                        selectedRole.isEmpty() -> errorMessage = "Please select your role."
                        else -> onRoleSelected(selectedRole)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)

            ) {
                Text("Sign In", fontSize = 16.sp)
            }






            Spacer(modifier = Modifier.height(12.dp))
//            // LOGIN BUTTON
//            Button(
//                onClick = {
//                    authViewModel.login(
//                        email = email,
//                        password = password,
//                          gender =gender,
//                        navController = navController,
//                        context = context
//                    )
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//            ) {
//                Text("Login", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(12.dp))
            Row() {
                Text(text = "I Have No Account?")
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "Sign Up",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate(ROUTE_REGISTER)
                    })}
            Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = "Dashboard",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                        navController.navigate(ROUTE_MAIN_DASHBOARD)
                    })

        }

    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun LoginScreenPreview(){
//    LoginScreen()
//}