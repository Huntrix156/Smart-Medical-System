package com.example.smartmedicalsystem.models


data class ChangePasswordState(
    val isLoading: Boolean = false,
    val successMessage: String = "",
    val errorMessage: String = ""
)
