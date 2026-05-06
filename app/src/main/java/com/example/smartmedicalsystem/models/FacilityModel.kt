package com.example.smartmedicalsystem.models

data class FacilityModel(
    val id: String = "",
    val name: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val type: String = "",
    val specialty: String = ""
)