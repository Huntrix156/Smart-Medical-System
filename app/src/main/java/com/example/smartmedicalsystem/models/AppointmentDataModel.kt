package com.example.smartmedicalsystem.models

data class Appointment(
    val doctor: String,
    val date: String,
    val time: String,
    val reason: String
)