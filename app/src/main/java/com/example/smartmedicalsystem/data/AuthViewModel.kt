package com.example.smartmedicalsystem.data


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.smartmedicalsystem.models.UserModel
import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN
import com.example.smartmedicalsystem.navigation.ROUTE_MAIN_DASHBOARD
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


//class AuthViewModel:ViewModel() {
//    private val auth: FirebaseAuth=FirebaseAuth.getInstance()
//    fun signup(username:String,email:String,password:String,confirmpassword:String,navController: NavController,context:Context){
//        if (username.isBlank() || email.isBlank() || password.isBlank() || confirmpassword.isBlank()){
//            Toast.makeText(context,"Please fill all the fields",Toast.LENGTH_LONG).show()
//            return
//        }
//        if (password != confirmpassword){
//            Toast.makeText(context,"Password do not match",Toast.LENGTH_LONG).show()
//            return
//        }
//        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
//                task ->
//            if (task.isSuccessful){
//                val userId = auth.currentUser?.uid ?: ""
//                val user = UserModel(username = username, email = email, userId = userId)
//
//                saveUserToDatabase(user,navController,context)
//            }else{
//                Toast.makeText(context,task.exception?.message ?:
//                "Registration failed",Toast.LENGTH_LONG).show()
//            }
//        }
//    }
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
//    fun login(email: String,password: String,navController: NavController,context: Context){
//        if (email.isBlank() || password.isBlank()){
//            Toast.makeText(context,"Email and Password required",Toast.LENGTH_LONG).show()
//            return
//        }
//        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
//                task ->
//            if (task.isSuccessful){
//                Toast.makeText(context,"Login Successful",Toast.LENGTH_LONG).show()
//                navController.navigate(ROUTE_DASHBOARD){
//                    popUpTo(0)
//                }
//            }else{
//                Toast.makeText(context,task.exception?.message ?: "Login failed",
//                    Toast.LENGTH_LONG).show()
//            }}}
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
class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // ✅ SIGNUP
    fun signup(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        confirmpassword: String,
        gender: String,   // ✅ NEW FIELD
        navController: NavController,
        context: Context
    ) {

        if (firstname.isBlank() || lastname.isBlank() || email.isBlank() || password.isBlank() || confirmpassword.isBlank() || gender.isBlank()) {
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

                    val userId = auth.currentUser?.uid ?: ""

                    // ✅ INCLUDE
                    val user = UserModel(
                        firstname = firstname,
                        lastname = lastname,
                        email = email,
                        userId = userId,
                        gender = gender
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

    // ✅ SAVE USER
    private fun saveUserToDatabase(
        user: UserModel,
        navController: NavController,
        context: Context
    ) {

        // ✅ CLEAN DATABASE STRUCTURE
        val dbRef = FirebaseDatabase.getInstance()
            .getReference("Users")
            .child(user.userId)

        dbRef.setValue(user).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                Toast.makeText(
                    context,
                    "User Registered successfully",
                    Toast.LENGTH_LONG
                ).show()

                navController.navigate(ROUTE_LOGIN) {
                    popUpTo(ROUTE_LOGIN) { inclusive = true }
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

    // ✅ LOGIN
    fun login(
        email: String,
        password: String,
        gender: String,
        navController: NavController,
        context: Context
    ) {

        if (email.isBlank() || password.isBlank()|| gender.isBlank()) {
            Toast.makeText(context, "Email and Password required", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()

                    navController.navigate(ROUTE_MAIN_DASHBOARD) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                    }

                } else {
                    Toast.makeText(
                        context,
                        task.exception?.message ?: "Login failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    // ✅ LOGOUT (CORRECT)
    fun logout(navController: NavController, context: Context) {

        auth.signOut()

        Toast.makeText(context, "Logged out Successfully", Toast.LENGTH_LONG).show()

        navController.navigate(ROUTE_LOGIN) {
            popUpTo(ROUTE_MAIN_DASHBOARD) { inclusive = true }
        }
    }
}