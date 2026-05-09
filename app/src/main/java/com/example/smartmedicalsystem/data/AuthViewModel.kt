package com.example.smartmedicalsystem.data//package com.example.smartmedicalsystem.data
//
//import android.content.Context
//import android.widget.Toast
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import androidx.navigation.NavController
//import com.example.smartmedicalsystem.models.UserModel
//import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
//
//
//sealed class AuthState {
//    object Idle    : AuthState()
//    object Loading : AuthState()
//    object Success : AuthState()
//    data class Error(val message: String) : AuthState()
//}
//
//class AuthViewModel : ViewModel() {
//
//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//    private val db   = FirebaseDatabase.getInstance().getReference("users")
//
//    private val MAX_ADMINS = 3
//
//    val authState = mutableStateOf<AuthState>(AuthState.Idle)
//
//    fun signup(
//        firstname: String,
//        lastname: String,
//        email: String,
//        password: String,
//        confirmpassword: String,
//        gender: String,
//        role: String,
//        specialization: String = "",
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//        if (firstname.isBlank() || lastname.isBlank() || email.isBlank() ||
//            password.isBlank() || confirmpassword.isBlank() ||
//            gender.isBlank()   || role.isBlank()
//        ) {
//            onError("Please fill all the fields")
//            return
//        }
//
//        if (password != confirmpassword) {
//            onError("Passwords do not match")
//            return
//        }
//
//        if (role == "Doctor" && specialization.isBlank()) {
//            onError("Please enter your specialization")
//            return
//        }
//
//        authState.value = AuthState.Loading
//
//        if (role == "Admin") {
//
//            db.get()
//                .addOnSuccessListener { snapshot ->
//                    val adminCount = snapshot.children
//                        .count { child ->
//                            child.child("role")
//                                .getValue(String::class.java)
//                                .equals("Admin", ignoreCase = true)
//                        }.toLong()
//
//                    if (adminCount >= MAX_ADMINS) {
//                        authState.value = AuthState.Error("Admin slots are full")
//                        onError(
//                            "Admin registration is closed. " +
//                                    "Only $MAX_ADMINS admin accounts are allowed."
//                        )
//                    } else {
//                        createFirebaseAccount(
//                            firstname, lastname, email, password, gender, role,
//                            specialization, onSuccess, onError
//                        )
//                    }
//                }
//                .addOnFailureListener { e ->
//                    authState.value = AuthState.Error(e.message ?: "Error checking admin count")
//                    onError("Could not verify admin limit. Please check your connection and try again.")
//                }
//        } else {
//            createFirebaseAccount(
//                firstname, lastname, email, password, gender, role,
//                specialization, onSuccess, onError
//            )
//        }
//    }
//
//    private fun createFirebaseAccount(
//        firstname: String,
//        lastname: String,
//        email: String,
//        password: String,
//        gender: String,
//        role: String,
//        specialization: String = "",
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val userId = auth.currentUser?.uid ?: ""
//                    val user = UserModel(
//                        firstname      = firstname,
//                        lastname       = lastname,
//                        email          = email,
//                        userId         = userId,
//                        gender         = gender,
//                        role           = role,
//                        specialization = if (role == "Doctor") specialization else ""
//                    )
//                    saveUserToDatabase(
//                        user      = user,
//                        onSuccess = {
//                            authState.value = AuthState.Success
//                            onSuccess()
//                        },
//                        onError   = { msg ->
//                            auth.currentUser?.delete()
//                            authState.value = AuthState.Error(msg)
//                            onError(msg)
//                        }
//                    )
//                } else {
//                    val msg = task.exception?.message ?: "Registration failed"
//                    authState.value = AuthState.Error(msg)
//                    onError(msg)
//                }
//            }
//    }
//
//
//    private fun saveUserToDatabase(
//        user: UserModel,
//        onSuccess: () -> Unit,
//        onError: (String) -> Unit
//    ) {
//        db.child(user.userId)
//            .setValue(user)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) onSuccess()
//                else onError(task.exception?.message ?: "Failed to save user data")
//            }
//    }
//
//    fun loginAndFetchRole(
//        email: String,
//        password: String,
//        context: Context,
//        onSuccess: (role: String, username: String) -> Unit,
//        onError: (String) -> Unit
//    ) {
//        if (email.isBlank() || password.isBlank()) {
//            onError("Email and Password are required")
//            return
//        }
//
//        authState.value = AuthState.Loading
//
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val uid = auth.currentUser?.uid ?: ""
//
//                    db.child(uid).get()
//                        .addOnSuccessListener { snapshot ->
//                            if (snapshot.exists()) {
//                                val rawRole = snapshot.child("role")
//                                    .getValue(String::class.java) ?: "Patient"
//
//                                val role = rawRole.replaceFirstChar { it.uppercase() }
//
//                                val firstName = snapshot.child("firstname")
//                                    .getValue(String::class.java)
//                                    ?: email.substringBefore("@")
//
//                                authState.value = AuthState.Success
//                                Toast.makeText(
//                                    context,
//                                    "Welcome back, $firstName!",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                                onSuccess(role, firstName)
//
//                            } else {
//                                authState.value = AuthState.Success
//                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
//                                onSuccess("Patient", email.substringBefore("@"))
//                            }
//                        }
//                        .addOnFailureListener { e ->
//                            val msg = e.message ?: "Failed to fetch user role"
//                            authState.value = AuthState.Error(msg)
//                            onError(msg)
//                        }
//
//                } else {
//                    val msg = task.exception?.message ?: "Login failed"
//                    authState.value = AuthState.Error(msg)
//                    onError(msg)
//                }
//            }
//    }
//
//
//    fun logout(navController: NavController, context: Context) {
//        auth.signOut()
//        authState.value = AuthState.Idle
//        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_LONG).show()
//        navController.navigate(ROUTE_LOGIN) {
//            popUpTo(0)
//        }
//    }
//}

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.smartmedicalsystem.models.UserModel
import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


sealed class AuthState {
    object Idle    : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db      = FirebaseDatabase.getInstance().getReference("users")
    private val doctorsDb = FirebaseDatabase.getInstance().getReference("doctors")

    private val MAX_ADMINS = 3

    val authState = mutableStateOf<AuthState>(AuthState.Idle)

    fun signup(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        confirmpassword: String,
        gender: String,
        role: String,
        specialization: String = "",
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (firstname.isBlank() || lastname.isBlank() || email.isBlank() ||
            password.isBlank() || confirmpassword.isBlank() ||
            gender.isBlank()   || role.isBlank()
        ) {
            onError("Please fill all the fields")
            return
        }

        if (password != confirmpassword) {
            onError("Passwords do not match")
            return
        }

        if (role == "Doctor" && specialization.isBlank()) {
            onError("Please enter your specialization")
            return
        }

        authState.value = AuthState.Loading

        if (role == "Admin") {

            db.get()
                .addOnSuccessListener { snapshot ->
                    val adminCount = snapshot.children
                        .count { child ->
                            child.child("role")
                                .getValue(String::class.java)
                                .equals("Admin", ignoreCase = true)
                        }.toLong()

                    if (adminCount >= MAX_ADMINS) {
                        authState.value = AuthState.Error("Admin slots are full")
                        onError(
                            "Admin registration is closed. " +
                                    "Only $MAX_ADMINS admin accounts are allowed."
                        )
                    } else {
                        createFirebaseAccount(
                            firstname, lastname, email, password, gender, role,
                            specialization, onSuccess, onError
                        )
                    }
                }
                .addOnFailureListener { e ->
                    authState.value = AuthState.Error(e.message ?: "Error checking admin count")
                    onError("Could not verify admin limit. Please check your connection and try again.")
                }
        } else {
            createFirebaseAccount(
                firstname, lastname, email, password, gender, role,
                specialization, onSuccess, onError
            )
        }
    }

    private fun createFirebaseAccount(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        gender: String,
        role: String,
        specialization: String = "",
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    val user = UserModel(
                        firstname      = firstname,
                        lastname       = lastname,
                        email          = email,
                        userId         = userId,
                        gender         = gender,
                        role           = role,
                        specialization = if (role == "Doctor") specialization else ""
                    )
                    saveUserToDatabase(
                        user      = user,
                        onSuccess = {
                            authState.value = AuthState.Success
                            onSuccess()
                        },
                        onError   = { msg ->
                            auth.currentUser?.delete()
                            authState.value = AuthState.Error(msg)
                            onError(msg)
                        }
                    )
                } else {
                    val msg = task.exception?.message ?: "Registration failed"
                    authState.value = AuthState.Error(msg)
                    onError(msg)
                }
            }
    }


    private fun saveUserToDatabase(
        user: UserModel,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        db.child(user.userId)
            .setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Mirror doctor profiles to doctors/ so AppointmentViewModel can filter by specialization
                    if (user.role.equals("Doctor", ignoreCase = true)) {
                        val doctorProfile = mapOf(
                            "uid"            to user.userId,
                            "name"           to "${user.firstname} ${user.lastname}",
                            "specialization" to user.specialization
                        )
                        doctorsDb.child(user.userId).setValue(doctorProfile)
                            .addOnCompleteListener { onSuccess() }
                    } else {
                        onSuccess()
                    }
                } else {
                    onError(task.exception?.message ?: "Failed to save user data")
                }
            }
    }

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

        authState.value = AuthState.Loading

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""

                    db.child(uid).get()
                        .addOnSuccessListener { snapshot ->
                            if (snapshot.exists()) {
                                val rawRole = snapshot.child("role")
                                    .getValue(String::class.java) ?: "Patient"

                                val role = rawRole.replaceFirstChar { it.uppercase() }

                                val firstName = snapshot.child("firstname")
                                    .getValue(String::class.java)
                                    ?: email.substringBefore("@")

                                authState.value = AuthState.Success
                                Toast.makeText(
                                    context,
                                    "Welcome back, $firstName!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onSuccess(role, firstName)

                            } else {
                                authState.value = AuthState.Success
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                                onSuccess("Patient", email.substringBefore("@"))
                            }
                        }
                        .addOnFailureListener { e ->
                            val msg = e.message ?: "Failed to fetch user role"
                            authState.value = AuthState.Error(msg)
                            onError(msg)
                        }

                } else {
                    val msg = task.exception?.message ?: "Login failed"
                    authState.value = AuthState.Error(msg)
                    onError(msg)
                }
            }
    }


    fun logout(navController: NavController, context: Context) {
        auth.signOut()
        authState.value = AuthState.Idle
        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_LONG).show()
        navController.navigate(ROUTE_LOGIN) {
            popUpTo(0)
        }
    }
}