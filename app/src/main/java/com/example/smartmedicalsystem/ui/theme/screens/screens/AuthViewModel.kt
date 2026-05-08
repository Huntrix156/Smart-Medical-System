package com.example.smartmedicalsystem.ui.theme.screens.screens


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Tracks if the app is currently communicating with Firebase
    var isLoading = mutableStateOf(false)

    // Tracks error messages to show to the user
    var errorMessage = mutableStateOf<String?>(null)

    // Tracks success status
    var isRegistrationSuccess = mutableStateOf(false)

    fun registerUser(email: String, pass: String) {
        if (email.isEmpty() || pass.isEmpty()) {
            errorMessage.value = "Please fill in all fields"
            return
        }

        isLoading.value = true
        errorMessage.value = null

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                isLoading.value = false
                if (task.isSuccessful) {
                    isRegistrationSuccess.value = true
                } else {
                    // Provides the specific error (e.g., "Email already in use")
                    errorMessage.value = task.exception?.localizedMessage
                }
            }
    }
}