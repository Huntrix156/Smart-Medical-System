package com.example.smartmedicalsystem.ui.theme.screens

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartmedicalsystem.data.AuthViewModel
import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN

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

    // ✅ FIX 1: Added per-field error states for inline validation feedback
    var firstNameError by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var genderError by remember { mutableStateOf("") }

    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current

    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF2FF))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(40.dp))

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

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // ── FIRST + LAST NAME ─────────────────────────
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = firstName,
                                onValueChange = {
                                    firstName = it
                                    firstNameError = ""
                                },
                                label = { Text("First Name") },
                                leadingIcon = { Icon(Icons.Default.Person, null) },
                                // ✅ FIX 2: isError wired to validation state
                                isError = firstNameError.isNotEmpty(),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF1976D2),
                                    unfocusedBorderColor = Color.Gray
                                )
                            )
                            if (firstNameError.isNotEmpty()) {
                                Text(firstNameError, color = Color.Red, fontSize = 11.sp)
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = lastName,
                                onValueChange = {
                                    lastName = it
                                    lastNameError = ""
                                },
                                label = { Text("Last Name") },
                                leadingIcon = { Icon(Icons.Default.Person, null) },
                                isError = lastNameError.isNotEmpty(),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF1976D2),
                                    unfocusedBorderColor = Color.Gray
                                )
                            )
                            if (lastNameError.isNotEmpty()) {
                                Text(lastNameError, color = Color.Red, fontSize = 11.sp)
                            }
                        }
                    }

                    // ── EMAIL ─────────────────────────────────────
                    // ✅ FIX 3: Inline email format validation
                    val emailValid = email.isEmpty() ||
                            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = ""
                        },
                        label = { Text("Email") },
                        leadingIcon = { Icon(Icons.Default.Email, null) },
                        isError = emailError.isNotEmpty() || !emailValid,
                        supportingText = {
                            when {
                                emailError.isNotEmpty() ->
                                    Text(emailError, color = Color.Red, fontSize = 11.sp)
                                !emailValid ->
                                    Text("Invalid email format", color = Color.Red, fontSize = 11.sp)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1976D2),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    // ── PASSWORD ──────────────────────────────────
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = ""
                            confirmPasswordError = ""
                        },
                        label = { Text("Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, null) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    contentDescription = if (passwordVisible) "Hide" else "Show"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        isError = passwordError.isNotEmpty(),
                        // ✅ FIX 4: Password length hint shown as supportingText
                        supportingText = {
                            when {
                                passwordError.isNotEmpty() ->
                                    Text(passwordError, color = Color.Red, fontSize = 11.sp)
                                password.isNotEmpty() && password.length < 6 ->
                                    Text("Password must be at least 6 characters", color = Color.Red, fontSize = 11.sp)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1976D2),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    // ── CONFIRM PASSWORD ──────────────────────────
                    // ✅ FIX 5: Live mismatch warning shown as the user types
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            confirmPasswordError = ""
                        },
                        label = { Text("Confirm Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, null) },
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    if (confirmPasswordVisible) Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    contentDescription = if (confirmPasswordVisible) "Hide" else "Show"
                                )
                            }
                        },
                        visualTransformation = if (confirmPasswordVisible)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        isError = confirmPasswordError.isNotEmpty() ||
                                (confirmPassword.isNotEmpty() && confirmPassword != password),
                        supportingText = {
                            when {
                                confirmPasswordError.isNotEmpty() ->
                                    Text(confirmPasswordError, color = Color.Red, fontSize = 11.sp)
                                confirmPassword.isNotEmpty() && confirmPassword != password ->
                                    Text("Passwords do not match", color = Color.Red, fontSize = 11.sp)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1976D2),
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    // ── GENDER ────────────────────────────────────
                    Text(text = "Gender", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = gender == "Male",
                            onClick = {
                                gender = "Male"
                                genderError = ""
                            }
                        )
                        Text(
                            "Male",
                            modifier = Modifier.clickable {
                                gender = "Male"
                                genderError = ""
                            }
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        RadioButton(
                            selected = gender == "Female",
                            onClick = {
                                gender = "Female"
                                genderError = ""
                            }
                        )
                        Text(
                            "Female",
                            modifier = Modifier.clickable {
                                gender = "Female"
                                genderError = ""
                            }
                        )
                    }
                    // ✅ FIX 6: Gender error shown below radio buttons
                    if (genderError.isNotEmpty()) {
                        Text(genderError, color = Color.Red, fontSize = 11.sp)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // ── REGISTER BUTTON ───────────────────────────
                    // ✅ FIX 7: Client-side validation runs BEFORE calling Firebase
                    //           Prevents unnecessary network calls on empty fields
//                    Button(
//                        onClick = {
//                            var valid = true
//
//                            if (firstName.isBlank()) {
//                                firstNameError = "First name is required"
//                                valid = false
//                            }
//                            if (lastName.isBlank()) {
//                                lastNameError = "Last name is required"
//                                valid = false
//                            }
//                            if (email.isBlank()) {
//                                emailError = "Email is required"
//                                valid = false
//                            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//                                emailError = "Invalid email format"
//                                valid = false
//                            }
//                            if (password.isBlank()) {
//                                passwordError = "Password is required"
//                                valid = false
//                            } else if (password.length < 6) {
//                                passwordError = "Password must be at least 6 characters"
//                                valid = false
//                            }
//                            if (confirmPassword != password) {
//                                confirmPasswordError = "Passwords do not match"
//                                valid = false
//                            }
//                            if (gender.isBlank()) {
//                                genderError = "Please select your gender"
//                                valid = false
//                            }
//
//                            if (valid) {
//                                authViewModel.signup(
//                                    firstname = firstName,
//                                    lastname = lastName,
//                                    email = email,
//                                    password = password,
//                                    confirmpassword = confirmPassword,
//                                    gender = gender,
//                                    navController = navController,
//                                    context = context
//                                )
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(52.dp),
//                        shape = RoundedCornerShape(14.dp)
//                    ) {
//                        Text("Create Account", fontSize = 16.sp)
//                    }
                    // ── REGISTER BUTTON ───────────────────────────
                    Button(
                        onClick = {
                            // 1. Reset and Run Validation
                            var isValid = true

                            if (firstName.isBlank()) { firstNameError = "First name is required"; isValid = false }
                            if (lastName.isBlank()) { lastNameError = "Last name is required"; isValid = false }
                            if (email.isBlank()) { emailError = "Email is required"; isValid = false }
                            if (password.length < 6) { passwordError = "Password too short"; isValid = false }
                            if (confirmPassword != password) { confirmPasswordError = "Passwords do not match"; isValid = false }
                            if (gender.isBlank()) { genderError = "Select gender"; isValid = false }

                            // 2. Only proceed if valid and not already loading
                            if (isValid && !isLoading) {
                                isLoading = true
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
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        enabled = !isLoading // Disable button while processing
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.height(24.dp).width(24.dp)
                            )
                        } else {
                            Text("Create Account", fontSize = 16.sp)
                        }
                    }
                    // ── FOOTER ────────────────────────────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Already have an account?", color = Color.DarkGray)
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

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController())
}