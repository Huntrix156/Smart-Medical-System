package com.example.smartmedicalsystem.ui.theme.screens.Authentication.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

// ── Role metadata ─────────────────────────────────────────────────────────────
private data class RoleOption(
    val label: String,
    val description: String,
    val icon: ImageVector,
    val accentColor: Color
)

private val ROLES = listOf(
    RoleOption(
        label       = "Patient",
        description = "Book appointments, track medications",
        icon        = Icons.Default.Person,
        accentColor = Color(0xFF1976D2)
    ),
    RoleOption(
        label       = "Doctor",
        description = "Manage patients & write prescriptions",
        icon        = Icons.Default.Person,
        accentColor = Color(0xFF388E3C)
    ),
    RoleOption(
        label       = "Admin",
        description = "System administration (max 3 accounts)",
        icon        = Icons.Default.Shield,
        accentColor = Color(0xFF7B1FA2)
    )
)
private val SPECIALIZATIONS = listOf(
    "General Practice",
    "Cardiology",
    "Dermatology",
    "Emergency Medicine",
    "Endocrinology",
    "Family Medicine",
    "Gastroenterology",
    "General Surgery",
    "Gynaecology & Obstetrics",
    "Haematology",
    "Internal Medicine",
    "Nephrology",
    "Neurology",
    "Oncology",
    "Ophthalmology",
    "Orthopaedics",
    "Paediatrics",
    "Psychiatry",
    "Pulmonology",
    "Radiology",
    "Urology"
)

@Composable
fun RegisterScreen(navController: NavController) {

    var firstName           by remember { mutableStateOf("") }
    var lastName            by remember { mutableStateOf("") }
    var email               by remember { mutableStateOf("") }
    var gender              by remember { mutableStateOf("") }
    var password            by remember { mutableStateOf("") }
    var confirmPassword     by remember { mutableStateOf("") }
    var passwordVisible     by remember { mutableStateOf(false) }
    var confirmPwVisible    by remember { mutableStateOf(false) }

    var selectedRole        by remember { mutableStateOf("") }
    var roleDropdownOpen    by remember { mutableStateOf(false) }
    var roleError           by remember { mutableStateOf("") }

    var specialization      by remember { mutableStateOf("") }
    var specializationError by remember { mutableStateOf("") }
    var specializationDropdownOpen by remember { mutableStateOf(false) }



    var firstNameError      by remember { mutableStateOf("") }
    var lastNameError       by remember { mutableStateOf("") }
    var emailError          by remember { mutableStateOf("") }
    var passwordError       by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var genderError         by remember { mutableStateOf("") }


    val authViewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    val accentColor = ROLES.find { it.label == selectedRole }?.accentColor
        ?: Color(0xFF004D40)

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
                color = Color(0xFF004D40)
            )
            Text(
                text = "Build your clinical profile",
                fontSize = 14.sp,
                color = Color(0xFF004D40)
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

                    Text(
                        text = "Select Your Role",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        color = Color(0xFF004D40)
                    )

                    Box {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .border(
                                    width = if (roleError.isNotEmpty()) 2.dp else 1.dp,
                                    color = when {
                                        roleError.isNotEmpty() -> Color.Red
                                        selectedRole.isNotEmpty() -> accentColor
                                        else -> Color.Gray
                                    },
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    roleDropdownOpen = true
                                    roleError = ""
                                }
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (selectedRole.isNotEmpty()) {
                                val opt = ROLES.find { it.label == selectedRole }
                                if (opt != null) {
                                    Icon(
                                        imageVector = opt.icon,
                                        contentDescription = null,
                                        tint = opt.accentColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                            Text(
                                text = if (selectedRole.isEmpty()) "Choose role…" else selectedRole,
                                color = if (selectedRole.isEmpty()) Color.Gray else Color.Black,
                                fontSize = 15.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Open role selector",
                                tint = Color.Gray
                            )
                        }

                        DropdownMenu(
                            expanded = roleDropdownOpen,
                            onDismissRequest = { roleDropdownOpen = false },
                            modifier = Modifier
                                .fillMaxWidth(0.88f)
                                .background(Color.White)
                        ) {
                            ROLES.forEach { option ->
                                DropdownMenuItem(
                                    text = {
                                        Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = option.icon,
                                                    contentDescription = null,
                                                    tint = option.accentColor,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = option.label,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = option.accentColor
                                                )
                                            }
                                            Text(
                                                text = option.description,
                                                fontSize = 11.sp,
                                                color = Color.DarkGray
                                            )
                                        }
                                    },
                                    onClick = {
                                        selectedRole = option.label
                                        roleDropdownOpen = false
                                        roleError = ""
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            if (selectedRole == option.label)
                                                option.accentColor.copy(alpha = 0.08f)
                                            else Color.White
                                        )
                                )
                            }
                        }
                    }

                    if (roleError.isNotEmpty()) {
                        Text(roleError, color = Color.Red, fontSize = 11.sp)
                    }

                    if (selectedRole == "Admin") {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFF3E5F5))
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Shield,
                                contentDescription = null,
                                tint = Color(0xFF7B1FA2),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Admin accounts are limited to $MAX_ADMINS system-wide. " +
                                        "Registration will fail if the limit is already reached.",
                                fontSize = 11.sp,
                                color = Color(0xFF7B1FA2)
                            )
                        }
                    }


                    if (selectedRole == "Doctor") {
                        Text(
                            text = "Specialization",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = Color(0xFF388E3C)
                        )

                        Box {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        width = if (specializationError.isNotEmpty()) 2.dp else 1.dp,
                                        color = when {
                                            specializationError.isNotEmpty() -> Color.Red
                                            specialization.isNotEmpty()      -> Color(0xFF388E3C)
                                            else                             -> Color.Gray
                                        },
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        specializationDropdownOpen = true
                                        specializationError = ""
                                    }
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (specialization.isEmpty()) "Choose specialization…" else specialization,
                                    color = if (specialization.isEmpty()) Color.Gray else Color.Black,
                                    fontSize = 15.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Open specialization selector",
                                    tint = Color.Gray
                                )
                            }

                            DropdownMenu(
                                expanded = specializationDropdownOpen,
                                onDismissRequest = { specializationDropdownOpen = false },
                                modifier = Modifier
                                    .fillMaxWidth(0.88f)
                                    .background(Color.White)
                            ) {
                                SPECIALIZATIONS.forEach { spec ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = spec,
                                                fontSize = 14.sp,
                                                color = if (specialization == spec)
                                                    Color(0xFF388E3C) else Color.Black,
                                                fontWeight = if (specialization == spec)
                                                    FontWeight.SemiBold else FontWeight.Normal
                                            )
                                        },
                                        onClick = {
                                            specialization = spec
                                            specializationDropdownOpen = false
                                            specializationError = ""
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                if (specialization == spec)
                                                    Color(0xFF388E3C).copy(alpha = 0.08f)
                                                else Color.White
                                            )
                                    )
                                }
                            }
                        }

                        if (specializationError.isNotEmpty()) {
                            Text(specializationError, color = Color.Red, fontSize = 11.sp)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = firstName,
                                onValueChange = { firstName = it; firstNameError = "" },
                                label = { Text("First Name") },
                                leadingIcon = { Icon(Icons.Default.Person, null) },
                                isError = firstNameError.isNotEmpty(),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = accentColor,
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
                                onValueChange = { lastName = it; lastNameError = "" },
                                label = { Text("Last Name") },
                                leadingIcon = { Icon(Icons.Default.Person, null) },
                                isError = lastNameError.isNotEmpty(),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = accentColor,
                                    unfocusedBorderColor = Color.Gray
                                )
                            )
                            if (lastNameError.isNotEmpty()) {
                                Text(lastNameError, color = Color.Red, fontSize = 11.sp)
                            }
                        }
                    }

                    // ── EMAIL ─────────────────────────────────────────────────
                    val emailValid = email.isEmpty() ||
                            Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; emailError = "" },
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
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    // ── PASSWORD ──────────────────────────────────────────────
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; passwordError = ""; confirmPasswordError = "" },
                        label = { Text("Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, null) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        isError = passwordError.isNotEmpty(),
                        supportingText = {
                            when {
                                passwordError.isNotEmpty() ->
                                    Text(passwordError, color = Color.Red, fontSize = 11.sp)
                                password.isNotEmpty() && password.length < 6 ->
                                    Text("At least 6 characters", color = Color.Red, fontSize = 11.sp)
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    // ── CONFIRM PASSWORD ──────────────────────────────────────
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it; confirmPasswordError = "" },
                        label = { Text("Confirm Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, null) },
                        trailingIcon = {
                            IconButton(onClick = { confirmPwVisible = !confirmPwVisible }) {
                                Icon(
                                    if (confirmPwVisible) Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (confirmPwVisible)
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
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.Gray
                        )
                    )

                    // ── GENDER ────────────────────────────────────────────────
                    Text(text = "Gender", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = gender == "Male",
                            onClick = { gender = "Male"; genderError = "" }
                        )
                        Text("Male", modifier = Modifier.clickable { gender = "Male"; genderError = "" })
                        Spacer(modifier = Modifier.width(20.dp))
                        RadioButton(
                            selected = gender == "Female",
                            onClick = { gender = "Female"; genderError = "" }
                        )
                        Text("Female", modifier = Modifier.clickable { gender = "Female"; genderError = "" })
                    }
                    if (genderError.isNotEmpty()) {
                        Text(genderError, color = Color.Red, fontSize = 11.sp)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // REGISTER BUTTON//
                    Button(
                        onClick = {
                            // Client-side validation
                            var isValid = true
                            if (selectedRole.isBlank())     { roleError = "Please select a role";          isValid = false }
                            if (firstName.isBlank())        { firstNameError = "First name is required";   isValid = false }
                            if (lastName.isBlank())         { lastNameError = "Last name is required";     isValid = false }
                            if (email.isBlank())            { emailError = "Email is required";            isValid = false }
                            if (password.length < 6)        { passwordError = "Password too short";        isValid = false }
                            if (confirmPassword != password){ confirmPasswordError = "Passwords do not match"; isValid = false }
                            if (gender.isBlank())           { genderError = "Select gender";               isValid = false }
                            if (selectedRole == "Doctor" && specialization.isBlank()) {
                                specializationError = "Please select your specialization"
                                isValid = false
                            }

                            if (isValid && !isLoading) {
                                isLoading = true
                                authViewModel.signup(
                                    firstname        = firstName,
                                    lastname         = lastName,
                                    email            = email,
                                    password         = password,
                                    confirmpassword  = confirmPassword,
                                    gender           = gender,
                                    role             = selectedRole,
                                    specialization   = specialization, // ← pass doctor specialization
                                    onSuccess = {
                                        isLoading = false
                                        navController.navigate(ROUTE_LOGIN) {
                                            popUpTo(ROUTE_LOGIN) { inclusive = true }
                                        }
                                    },
                                    onError = { errorMsg ->
                                        isLoading = false
                                        Toast.makeText(
                                            context, errorMsg, Toast.LENGTH_LONG
                                        ).show()
                                    }
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        enabled = !isLoading,
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier
                                    .height(24.dp)
                                    .width(24.dp)
                            )
                        } else {
                            Text(
                                text = if (selectedRole.isEmpty()) "Create Account"
                                else "Register as $selectedRole",
                                fontSize = 16.sp
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Already have an account?", color = Color.DarkGray)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Sign In",
                            color = Color(0xFF004D40),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier .clip(RoundedCornerShape(20.dp)).
                            clickable { navController.navigate(ROUTE_LOGIN) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}


private const val MAX_ADMINS = 3

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(rememberNavController())
}
