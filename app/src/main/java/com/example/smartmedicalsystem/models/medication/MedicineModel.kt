package com.example.smartmedicalsystem.models.medication

data class MedicineModel(
    var id: String?=null,
    var name: String?=null,
    var dosage: String?=null,
    var startDate: String?=null,
    var endDate: String?=null,
    var frequency: String?=null,
    var imageUrl: String?=null
)