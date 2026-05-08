package com.example.smartmedicalsystem.models

data class Appointment(
    val appointmentId: String = "",
    val patientId: String = "",
    val patientName: String = "",
    val doctorId: String = "",
    val doctorName: String = "",
    val date: String = "",
    val time: String = "",
    val reason: String = "",
    val status: String = "pending",           // pending | completed | referred
    val referredDoctorId: String = "",
    val referredDoctorName: String = "",
    val notificationMessage: String = ""
)


data class DoctorProfile(
    val uid: String = "",
    val name: String = "",
    val specialization: String = ""
)


data class AppointmentNotification(
    val notificationId: String = "",
    val title: String = "",
    val message: String = "",
    val timestamp: Long = 0L,
    val read: Boolean = false
)
