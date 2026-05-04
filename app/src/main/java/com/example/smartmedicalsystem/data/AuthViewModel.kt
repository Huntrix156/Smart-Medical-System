//package com.example.smartmedicalsystem.data
//
//
//import android.content.Context
//import android.widget.Toast
//import androidx.lifecycle.ViewModel
//import androidx.navigation.NavController
//import com.example.smartmedicalsystem.models.UserModel
//import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
//
//
//class AuthViewModel:ViewModel() {
//    private val auth: FirebaseAuth=FirebaseAuth.getInstance()
//
//    fun signup(
//        firstname: String,
//        lastname: String,
//        email: String,
//        password: String,
//        confirmpassword: String,
//        gender: String,
//        navController: NavController,
//        context: Context
//    ) {
//
//        if (firstname.isBlank() ||lastname.isBlank() || email.isBlank() || password.isBlank() || confirmpassword.isBlank()|| gender.isBlank()) {
//            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        if (password != confirmpassword) {
//            Toast.makeText(context, "Password do not match", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//
//                if (task.isSuccessful) {
//
//                    val userId = auth.currentUser?.uid ?: ""
//
//                    // ✅ FIX: ADD ROLE HERE
//                    val user = UserModel(
//                        firstname = firstname,
//                        lastname = lastname,
//                        email = email,
//                        gender = gender,
//                        userId = userId,
//                        role = "patient" // default role (or let user choose later)
//                    )
//
//                    saveUserToDatabase(user, navController, context)
//
//                } else {
//                    Toast.makeText(
//                        context,
//                        task.exception?.message ?: "Registration failed",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//    }
//
//
//
//
//
////    fun signup(username:String,email:String,password:String,confirmpassword:String,navController: NavController,context:Context){
////        if (username.isBlank() || email.isBlank() || password.isBlank() || confirmpassword.isBlank()){
////            Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_LONG).show()
////            return
////        }
////        if (password != confirmpassword){
////            Toast.makeText(context,"Password do not match",Toast.LENGTH_LONG).show()
////            return
////        }
////        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
////                task ->
////            if (task.isSuccessful){
////                val userId = auth.currentUser?.uid ?: ""
////                val user = UserModel(username = username, email = email, userId = userId)
////
////                saveUserToDatabase(user,navController,context)
////            }else{
////                Toast.makeText(context,task.exception?.message ?:
////                "Registration failed",Toast.LENGTH_LONG).show()
////            }
////        }
////    }
//    private fun saveUserToDatabase(user:UserModel,navController: NavController,context: Context){
//        val dbRef = FirebaseDatabase.getInstance().getReference("User/${user.userId}")
//        dbRef.setValue(user).addOnCompleteListener{
//                task ->
//            if (task.isSuccessful){
//                Toast.makeText(context,"User Registered successfully",
//                    Toast.LENGTH_LONG).show()
//                navController.navigate(ROUTE_LOGIN){
//                    popUpTo(0)
//                }
//            }else{
//                Toast.makeText(context,task.exception?.message ?: "Failed to save user",
//                    Toast.LENGTH_LONG).show()
//            }
//        }
//
//
//    }
//
//    fun login(
//        email: String,
//        password: String,
//        navController: NavController,
//        context: Context
//    ) {
//
//        if (email.isBlank() || password.isBlank()) {
//            Toast.makeText(context, "Email and Password required", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//
//                if (task.isSuccessful) {
//
//                    val uid = auth.currentUser?.uid
//
//                    if (uid != null) {
//
//                        val ref = FirebaseDatabase.getInstance()
//                            .getReference("User")
//                            .child(uid)
//
//                        ref.get().addOnSuccessListener { snapshot ->
//
//                            if (!snapshot.exists()) {
//                                Toast.makeText(
//                                    context,
//                                    "User profile not found",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                                return@addOnSuccessListener
//                            }
//
//                            val role = snapshot.child("role")
//                                .value?.toString()
//                                ?.lowercase()
//                                ?.trim()
//
//                            if (role == null) {
//                                Toast.makeText(
//                                    context,
//                                    "Role missing in database",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                                return@addOnSuccessListener
//                            }
//
//                            when (role) {
//
//                                "admin" -> navController.navigate("admin_dashboard") {
//                                    popUpTo(0)
//                                }
//
//                                "doctor" -> navController.navigate("doctor_dashboard") {
//                                    popUpTo(0)
//                                }
//
//                                "patient" -> navController.navigate("patient_dashboard") {
//                                    popUpTo(0)
//                                }
//
//                                else -> {
//                                    Toast.makeText(
//                                        context,
//                                        "Unknown role",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                }
//                            }
//                        }
//
//                    }
//
//                } else {
//                    Toast.makeText(
//                        context,
//                        task.exception?.message ?: "Login failed",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//    }
//
//    fun logout(navController: NavController, context: Context) {
//        auth.signOut()
//
//        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
//
//        navController.navigate(ROUTE_LOGIN) {
//            popUpTo(0)
//        }
//    }
//}
//



package com.example.smartmedicalsystem.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.smartmedicalsystem.models.UserModel
import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signup(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        confirmpassword: String,
        gender: String,
        navController: NavController,
        context: Context
    ) {

        if (firstname.isBlank() || lastname.isBlank() || email.isBlank()
            || password.isBlank() || confirmpassword.isBlank() || gender.isBlank()
        ) {
            Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_LONG).show()
            return
        }

        if (password != confirmpassword) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                    val user = UserModel(
                        firstname = firstname,
                        lastname = lastname,
                        email = email,
                        gender = gender,
                        userId = userId,
                        role = "patient"
                    )

                    saveUserToDatabase(user, navController, context)

                } else {
                    Toast.makeText(
                        context,
                        task.exception?.message ?: "Registration failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun saveUserToDatabase(
        user: UserModel,
        navController: NavController,
        context: Context
    ) {

        val dbRef = FirebaseDatabase.getInstance()
            .getReference("User")
            .child(user.userId)

        dbRef.setValue(user).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(context, "User registered successfully", Toast.LENGTH_LONG).show()

                navController.navigate(ROUTE_LOGIN) {
                    popUpTo(0) { inclusive = true }
                }

            } else {
                Toast.makeText(
                    context,
                    task.exception?.message ?: "Failed to save user",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

//    fun login(
//        email: String,
//        password: String,
//        navController: NavController,
//        context: Context
//    ) {
//
//        if (email.isBlank() || password.isBlank()) {
//            Toast.makeText(context, "Email and Password required", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//
//                if (!task.isSuccessful) {
//                    Toast.makeText(
//                        context,
//                        task.exception?.message ?: "Login failed",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    return@addOnCompleteListener
//                }
//
//                val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
//
//                val ref = FirebaseDatabase.getInstance()
//                    .getReference("User")
//                    .child(uid)
//
//                ref.get().addOnSuccessListener { snapshot ->
//
//                    if (!snapshot.exists()) {
//                        Toast.makeText(context, "User profile not found", Toast.LENGTH_LONG).show()
//                        return@addOnSuccessListener
//                    }
//
//                    val role = snapshot.child("role")
//                        .value?.toString()
//                        ?.lowercase()
//                        ?.trim()
//
//                    when (role) {
//
//                        "admin" -> navController.navigate("admin_dashboard") {
//                            popUpTo(0) { inclusive = true }
//                        }
//
//                        "doctor" -> navController.navigate("doctor_dashboard") {
//                            popUpTo(0) { inclusive = true }
//                        }
//
//                        "patient" -> navController.navigate("patient_dashboard") {
//                            popUpTo(0) { inclusive = true }
//                        }
//
//                        else -> {
//                            Toast.makeText(context, "Unknown role", Toast.LENGTH_LONG).show()
//                        }
//                    }
//                }
//            }
//    }

    fun login(
        email: String,
        password: String,
        navController: NavController,
        context: Context
    ) {

        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(context, "Email and Password required", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (!task.isSuccessful) {
                    Toast.makeText(
                        context,
                        task.exception?.message ?: "Login failed",
                        Toast.LENGTH_LONG
                    ).show()
                    return@addOnCompleteListener
                }

                val uid = auth.currentUser?.uid

                if (uid == null) {
                    Toast.makeText(context, "Login error: missing user ID", Toast.LENGTH_LONG).show()
                    return@addOnCompleteListener
                }

                val ref = FirebaseDatabase.getInstance()
                    .getReference("User")
                    .child(uid)

                ref.get()
                    .addOnSuccessListener { snapshot ->

                        if (!snapshot.exists()) {
                            Toast.makeText(context, "User not found in database", Toast.LENGTH_LONG).show()
                            return@addOnSuccessListener
                        }

                        val role = snapshot.child("role").value?.toString()?.trim()?.lowercase()

                        if (role.isNullOrEmpty()) {
                            Toast.makeText(context, "Role missing in database", Toast.LENGTH_LONG).show()
                            return@addOnSuccessListener
                        }

                        val route = when (role) {
                            "admin" -> "admin_dashboard"
                            "doctor" -> "doctor_dashboard"
                            "patient" -> "patient_dashboard"
                            else -> null
                        }

                        if (route == null) {
                            Toast.makeText(context, "Invalid role: $role", Toast.LENGTH_LONG).show()
                            return@addOnSuccessListener
                        }

                        navController.navigate(route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                    .addOnFailureListener { error ->
                        Toast.makeText(
                            context,
                            error.message ?: "Database error during login",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
    }












    fun logout(navController: NavController, context: Context) {
        auth.signOut()

        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()

        navController.navigate(ROUTE_LOGIN) {
            popUpTo(0) { inclusive = true }
        }
    }
}