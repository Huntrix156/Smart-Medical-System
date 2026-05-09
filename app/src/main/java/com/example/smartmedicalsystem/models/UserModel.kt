package com.example.smartmedicalsystem.models

data class UserModel(
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val userId: String = "",
    val gender: String = "",
    val role: String = "Patient",
    val specialization: String = ""
)

