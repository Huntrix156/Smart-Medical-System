package com.example.smartmedicalsystem.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.smartmedicalsystem.models.UserModel
import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// ── Auth State sealed class ───────────────────────────────────────────────────
sealed class AuthResult {
    object Idle    : AuthResult()
    object Loading : AuthResult()
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Exposed so the UI can observe loading / error states
    val authState = mutableStateOf<AuthResult>(AuthResult.Idle)

    // ── Signup ────────────────────────────────────────────────────────────────
    fun signup(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        confirmpassword: String,
        gender: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (firstname.isBlank() || lastname.isBlank() || email.isBlank() ||
            password.isBlank() || confirmpassword.isBlank() || gender.isBlank()
        ) {
            onError("Please fill all the fields")
            return
        }

        if (password != confirmpassword) {
            onError("Passwords do not match")
            return
        }

        authState.value = AuthResult.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    val user = UserModel(
                        firstname = firstname,
                        lastname = lastname,
                        email = email,
                        userId = userId,
                        gender = gender
                    )
                    saveUserToDatabase(
                        user = user,
                        onSuccess = {
                            authState.value = AuthResult.Success
                            onSuccess()
                        },
                        onError = { msg ->
                            authState.value = AuthResult.Error(msg)
                            onError(msg)
                        }
                    )
                } else {
                    val msg = task.exception?.message ?: "Registration failed"
                    authState.value = AuthResult.Error(msg)
                    onError(msg)
                }
            }
    }

    // ── Save user to Realtime Database ────────────────────────────────────────
    private fun saveUserToDatabase(
        user: UserModel,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        FirebaseDatabase.getInstance()
            .getReference("Users")
            .child(user.userId)
            .setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) onSuccess()
                else onError(task.exception?.message ?: "Failed to save user data")
            }
    }

    // ── Login ─────────────────────────────────────────────────────────────────
    fun login(
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Email and Password are required", Toast.LENGTH_LONG).show()
            return
        }

        authState.value = AuthResult.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authState.value = AuthResult.Success
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
                    onSuccess()
                } else {
                    val msg = task.exception?.message ?: "Login failed"
                    authState.value = AuthResult.Error(msg)
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                }
            }
    }

    // ── Logout ────────────────────────────────────────────────────────────────
    fun logout(navController: NavController, context: Context) {
        auth.signOut()
        authState.value = AuthResult.Idle
        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_LONG).show()
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(0)
        }
    }
}