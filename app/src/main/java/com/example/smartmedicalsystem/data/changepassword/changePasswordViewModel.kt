package com.example.smartmedicalsystem.data.changepassword

// class changePasswordViewModel()

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartmedicalsystem.models.ChangePasswordState
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChangePasswordViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow(ChangePasswordState())
    val state: StateFlow<ChangePasswordState> = _state

    fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        val user = auth.currentUser
        val email = user?.email

        if (oldPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank()) {
            _state.value = ChangePasswordState(errorMessage = "All fields are required.")
            return
        }
        if (newPassword != confirmPassword) {
            _state.value = ChangePasswordState(errorMessage = "New passwords do not match.")
            return
        }
        if (newPassword.length < 6) {
            _state.value = ChangePasswordState(errorMessage = "Password must be at least 6 characters.")
            return
        }
        if (user == null || email == null) {
            _state.value = ChangePasswordState(errorMessage = "No logged-in user found.")
            return
        }

        _state.value = ChangePasswordState(isLoading = true)

        viewModelScope.launch {
            try {
                val credential = EmailAuthProvider.getCredential(email, oldPassword)
                user.reauthenticate(credential).await()
                user.updatePassword(newPassword).await()
                _state.value = ChangePasswordState(successMessage = "Password changed successfully!")
            } catch (e: Exception) {
                val message = when {
                    e.message?.contains("password is invalid") == true ||
                            e.message?.contains("wrong-password") == true ->
                        "Old password is incorrect."
                    e.message?.contains("network") == true ->
                        "Network error. Check your connection."
                    else -> e.message ?: "Failed to change password."
                }
                _state.value = ChangePasswordState(errorMessage = message)
            }
        }
    }

    fun clearMessages() {
        _state.value = ChangePasswordState()
    }
}

