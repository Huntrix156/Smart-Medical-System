//package com.example.dosetracker.ui.theme.screens.changepassword.screen
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material.icons.filled.Create
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material3.Icon
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
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.nexora.navigation.ROUTE_REGISTER
//
//
//@Composable
//fun ChangePassword(navController: NavController){
//    var oldpassword by remember { mutableStateOf("") }
//    var newpassword by remember { mutableStateOf("") }
//    var confirmpassword by remember { mutableStateOf("") }
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "Change Password", fontWeight = FontWeight.SemiBold,
//            fontSize= 30.sp)
//        OutlinedTextField(
//            value = oldpassword,
//            onValueChange = { oldpassword = it },
//            label = { Text(text = "Enter Old Password") },
//            leadingIcon = {Icon(Icons.Default.Lock, contentDescription = null)},
//            modifier = Modifier
//                .fillMaxWidth(),
//            visualTransformation = PasswordVisualTransformation()
//
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//        OutlinedTextField(
//            value = newpassword,
//            onValueChange = { newpassword = it },
//            label = { Text(text = "Enter New Password") },
//            leadingIcon = {Icon(Icons.Default.Create, contentDescription = null)},
//            modifier = Modifier
//                .fillMaxWidth(),
//            visualTransformation = PasswordVisualTransformation()
//        )
//
//
//        Spacer(modifier = Modifier.height(10.dp))
//        OutlinedTextField(
//            value = confirmpassword,
//            onValueChange = { confirmpassword = it },
//            label = { Text(text = "Enter Confirm New Password") },
//            leadingIcon = {Icon(Icons.Default.Check, contentDescription = null)},
//            modifier = Modifier
//                .fillMaxWidth(),
//            visualTransformation = PasswordVisualTransformation()
//        )
//
//        Row() {
//            Text(
//                text = "Save", fontSize = 20.sp, color = Color.Blue,
//            )
//            Spacer(Modifier.width(180.dp))
//            Text(
//                text = "Submit", fontSize = 20.sp,
//                modifier = Modifier
//                    .clickable { navController.navigate(ROUTE_REGISTER) },
//            )
//        }
////        Button(onClick = ) { }
//    }
//}
//
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun ChangePasswordScreen(){
//    ChangePassword(navController=rememberNavController())
//}



package com.example.dosetracker.ui.theme.screens.changepassword.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartmedicalsystem.data.changepassword.ChangePasswordViewModel

@Composable
fun ChangePassword(
    navController: NavController,
    viewModel: ChangePasswordViewModel = viewModel()
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var oldPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(oldPassword, newPassword, confirmPassword) {
        if (state.errorMessage.isNotEmpty() || state.successMessage.isNotEmpty()) {
            viewModel.clearMessages()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Change Password",
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Old Password
        OutlinedTextField(
            value = oldPassword,
            onValueChange = { oldPassword = it },
            label = { Text("Enter Old Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { oldPasswordVisible = !oldPasswordVisible }) {
                    Icon(
                        imageVector = if (oldPasswordVisible) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (oldPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // New Password
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Enter New Password") },
            leadingIcon = { Icon(Icons.Default.Create, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                    Icon(
                        imageVector = if (newPasswordVisible) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (newPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Confirm New Password
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm New Password") },
            leadingIcon = { Icon(Icons.Default.Check, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Error message
        if (state.errorMessage.isNotEmpty()) {
            Text(
                text = state.errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Success message
        if (state.successMessage.isNotEmpty()) {
            Text(
                text = state.successMessage,
                color = Color(0xFF2E7D32),
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Submit Button
        Button(
            onClick = {
                viewModel.changePassword(oldPassword, newPassword, confirmPassword)
            },
            enabled = !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.height(20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Changing...")
            } else {
                Text("Change Password", fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChangePasswordScreen() {
    ChangePassword(navController = rememberNavController())
}