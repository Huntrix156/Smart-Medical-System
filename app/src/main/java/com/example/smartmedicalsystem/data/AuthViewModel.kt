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
                        gender = gender,
                        role = "Patient"   // ✅ every new signup defaults to Patient
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
            .getReference("users")        // ✅ lowercase — consistent across the app
            .child(user.userId)
            .setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) onSuccess()
                else onError(task.exception?.message ?: "Failed to save user data")
            }
    }

    // ── Login + fetch role from Realtime Database ─────────────────────────────
    fun loginAndFetchRole(
        email: String,
        password: String,
        context: Context,
        onSuccess: (role: String, username: String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            onError("Email and Password are required")
            return
        }

        authState.value = AuthResult.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""

                    FirebaseDatabase.getInstance()
                        .getReference("users")    // ✅ lowercase
                        .child(uid)
                        .get()
                        .addOnSuccessListener { snapshot ->
                            if (snapshot.exists()) {
                                val rawRole = snapshot.child("role")
                                    .getValue(String::class.java) ?: "Patient"

                                // ✅ Normalize casing: "admin" → "Admin"
                                val role = rawRole.replaceFirstChar { it.uppercase() }

                                val firstName = snapshot.child("firstname")
                                    .getValue(String::class.java)
                                    ?: email.substringBefore("@")

                                authState.value = AuthResult.Success
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                                onSuccess(role, firstName)

                            } else {
                                // Auth succeeded but no DB record found — default to Patient
                                authState.value = AuthResult.Success
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                                onSuccess("Patient", email.substringBefore("@"))
                            }
                        }
                        .addOnFailureListener { e ->
                            val msg = e.message ?: "Failed to fetch user role"
                            authState.value = AuthResult.Error(msg)
                            onError(msg)
                        }

                } else {
                    val msg = task.exception?.message ?: "Login failed"
                    authState.value = AuthResult.Error(msg)
                    onError(msg)
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