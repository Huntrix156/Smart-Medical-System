
package com.example.smartmedicalsystem.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartmedicalsystem.data.AuthViewModel
import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN

//
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.RadioButton
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.smartmedicalsystem.data.AuthViewModel
//import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN
//
//@Composable
//fun RegisterScreen(navController: NavController) {
//    var firstName by remember { mutableStateOf("") }
//    var lastName by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var gender by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var confirmPassword by remember { mutableStateOf("") }
//    var passwordVisible by remember { mutableStateOf(false) }
//    var confirmPasswordVisible by remember { mutableStateOf(false) }
//
//    val scrollState = rememberScrollState()
//    val authViewModel: AuthViewModel = viewModel()
//    val context = LocalContext.current
//
//    Card(
//        modifier = Modifier
//            .padding(20.dp)
//            .fillMaxWidth()
//        .verticalScroll(scrollState),
//        shape = MaterialTheme.shapes.large,
//        elevation = CardDefaults.cardElevation(10.dp),
//
//    ) {
//        Column(
//            modifier = Modifier
//           .background(Color(0xFFF3F5F9))
//                .padding(20.dp)
//                .fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {Spacer(modifier = Modifier.height(26.dp))
//
//            Text(text = "Create Account", fontSize = 20.sp, color = Color.Black)
//            Text(text = "Please fill in your clinical profile details", color = Color.Black)
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // FIRST NAME + LAST NAME
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                OutlinedTextField(
//                    value = firstName,
//                    onValueChange = { firstName = it },
//                    label = { Text("First Name") },
//                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
//                    singleLine = true,
//                    modifier = Modifier.weight(1f),
//                    shape = RoundedCornerShape(8.dp)
//                )
//                OutlinedTextField(
//                    value = lastName,
//                    onValueChange = { lastName = it },
//                    label = { Text("Last Name") },
//                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
//                    singleLine = true,
//                    modifier = Modifier.weight(1f),
//                    shape = RoundedCornerShape(8.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // EMAIL
//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Email") },
//                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//                isError = email.isNotEmpty() &&
//                        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches(),
//                supportingText = {
//                    if (email.isNotEmpty() &&
//                        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
//                    ) {
//                        Text("Invalid email format", color = Color.Red)
//                    }
//                },
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp)
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // PASSWORD
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Password") },
//                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
//                trailingIcon = {
//                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                        Icon(
//                            imageVector = if (passwordVisible)
//                                Icons.Default.VisibilityOff
//                            else Icons.Default.Visibility,
//                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
//                        )
//                    }
//                },
//                visualTransformation = if (passwordVisible)
//                    VisualTransformation.None
//                else PasswordVisualTransformation(),
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp)
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // CONFIRM PASSWORD
//            OutlinedTextField(
//                value = confirmPassword,
//                onValueChange = { confirmPassword = it },
//                label = { Text("Confirm Password") },
//                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
//                trailingIcon = {
//                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
//                        Icon(
//                            imageVector = if (confirmPasswordVisible)
//                                Icons.Default.VisibilityOff
//                            else Icons.Default.Visibility,
//                            contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
//                        )
//                    }
//                },
//                visualTransformation = if (confirmPasswordVisible)
//                    VisualTransformation.None
//                else PasswordVisualTransformation(),
//                isError = confirmPassword.isNotEmpty() && confirmPassword != password,
//                supportingText = {
//                    if (confirmPassword.isNotEmpty() && confirmPassword != password) {
//                        Text("Passwords do not match", color = Color.Red)
//                    }
//                },
//                singleLine = true,
//                modifier = Modifier.fillMaxWidth(),
//                shape = RoundedCornerShape(8.dp)   ////for the rounded shape////
//            )
////       OutlinedTextField(
////           value = gender,
////           onValueChange = {gender = it},
////           label = {Text(text= "Male/Female")},
////           placeholder = {Text(text = "Male/Female")})
//                   // GENDER SELECTION (RADIO BUTTONS)
//                   Column(modifier = Modifier.fillMaxWidth()) {
//               Text(text = "Gender", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
//               Row(verticalAlignment = Alignment.CenterVertically) {
//                   RadioButton(
//                       selected = gender == "Male",
//                       onClick = { gender = "Male" }
//                   )
//                   Text("Male", modifier = Modifier.clickable { gender = "Male" })
//
//                   Spacer(modifier = Modifier.width(20.dp))
//
//                   RadioButton(
//                       selected = gender == "Female",
//                       onClick = { gender = "Female" }
//                   )
//                   Text("Female", modifier = Modifier.clickable { gender = "Female" })
//               }
//           }
//
//                   Spacer(modifier = Modifier.height(12.dp))
//
//            Spacer(modifier = Modifier.height(12.dp))
//        }
//        // REGISTER BUTTON
//        Button(
//            onClick = {
//                authViewModel.signup(
//                    firstname = firstName,
//                    lastname = lastName,
//                    email = email,
//                    password = password,
//                    confirmpassword = confirmPassword,
//                      gender = gender,
//                    navController = navController,
//                    context = context
//                )
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            shape = RoundedCornerShape(8.dp)
//        ) {
//            Text("Create Account")
//            Spacer(modifier = Modifier.width(20.dp))
//        }
//        Row() {
//            Text(text="Already Have an Account?")
//            Spacer(modifier = Modifier.width(20.dp))
//
//            Text(
//                text = "Sign In",
//                color = MaterialTheme.colorScheme.primary,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.clickable {
//                    navController.navigate(ROUTE_LOGIN)
//                })
//        }
//    }
//}
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun RegisterScreenPreview() {
//    RegisterScreen(rememberNavController())
//}


@Composable
fun RegisterScreen(navController: NavController) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEAF2FF)) // soft medical background
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Create Account",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
            )

            Text(
                text = "Build your clinical profile",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(20.dp))

            // MAIN CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    // FIRST + LAST NAME
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            label = { Text("First Name") },
                            leadingIcon = { Icon(Icons.Default.Person, null) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )

                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            label = { Text("Last Name") },
                            leadingIcon = { Icon(Icons.Default.Person, null) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // EMAIL
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        leadingIcon = { Icon(Icons.Default.Email, null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // PASSWORD
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, null) },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisible = !passwordVisible
                            }) {
                                Icon(
                                    if (passwordVisible)
                                        Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    null
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // CONFIRM PASSWORD
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, null) },
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisible = !confirmPasswordVisible
                            }) {
                                Icon(
                                    if (confirmPasswordVisible)
                                        Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    null
                                )
                            }
                        },
                        visualTransformation = if (confirmPasswordVisible)
                            VisualTransformation.None
                        else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // GENDER
                    Text(
                        text = "Gender",
                        fontWeight = FontWeight.SemiBold
                    )

                    Row {
                        RadioButton(
                            selected = gender == "Male",
                            onClick = { gender = "Male" }
                        )
                        Text("Male", modifier = Modifier.clickable { gender = "Male" })

                        Spacer(modifier = Modifier.width(16.dp))

                        RadioButton(
                            selected = gender == "Female",
                            onClick = { gender = "Female" }
                        )
                        Text("Female", modifier = Modifier.clickable { gender = "Female" })
                    }

                    // REGISTER BUTTON (premium style)
                    Button(
                        onClick = {
                            authViewModel.signup(
                                firstname = firstName,
                                lastname = lastName,
                                email = email,
                                password = password,
                                confirmpassword = confirmPassword,
                                gender = gender,
                                navController = navController,
                                context = context
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Create Account", fontSize = 16.sp)
                    }

                    // FOOTER
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Already have an account?")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Sign In",
                            color = Color(0xFF1976D2),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                navController.navigate(ROUTE_LOGIN)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

