package com.example.smartmedicalsystem.navigation

import java.net.URLEncoder



const val ROUTE_REGISTER = "register_screen"

const val ROUTE_LOGIN = "login_screen"

const val ROUTE_MAIN_DASHBOARD = "dashboard_screen"

const val ROUTE_PATIENT_DASHBOARD = "patient_dashboard"

const val ROUTE_DOCTOR_DASHBOARD = "doctor_dashboard"

const val ROUTE_ADMIN_DASHBOARD = "admin_dashboard"

const val ROUTE_FORGOT_PASSWORD = "forgot_password_screen"

const val ROUTE_ADD_MEDICATION = "add_medication"

const val ROUTE_ADD_MEDICINE = "add_medicine"

const val ROUTE_UPDATE_MEDICATION = "update_medicine/{medicineId}"

const val ROUTE_MEDICINE_LIST = "medicine_list"

const val ROUTE_MEDICATION_SCREEN = "medication_screen"

const val ROUTE_INVENTORY_SCREEN = "inventory_screen"

const val ROUTE_MEDICATION_REMINDER_COMPONENT = "medication_reminder_component"

const val ROUTE_UPCOMING_APPOINTMENT = "upcoming_appointment"

const val ROUTE_ADMIN_ADD_DOCTOR = "admin_add_doctor_screen"

const val ROUTE_CHANGE_PASSWORD = "change_password"

const val ROUTE_WRITE_PRESCRIPTION = "write_prescriptions/{patientId}/{patientName}/{appointmentId}"

fun writePrescriptionRoute(patientId: String, patientName: String, appointmentId: String): String {
    val encodedName = URLEncoder.encode(patientName, "UTF-8")
    return "write_prescriptions/$patientId/$encodedName/$appointmentId"
}

const val ROUTE_REMINDER = "reminder"

const val ROUTE_SETTINGS = "settings"

const val ROUTE_EMERGENCY_SOS = "emergency_sos"

const val ROUTE_PROFILE = "profile"
const val ROUTE_GENERATE_REPORT = "generate_report"
const val ROUTE_UPDATE_PROFILE = "update_profile"

const val ROUTE_ADMIN_APPOINTMENTS = "admin_appointments_screen"
const val ROUTE_DOCTOR_APPOINTMENTS = "doctor_appointments_screen"


const val ROUTE_DOCTOR_REPORT_HUB = "doctor_report_hub"
const val ROUTE_REPORT_DIAGNOSIS = "report_diagnosis/{patientId}/{patientName}/{appointmentId}"
const val ROUTE_REPORT_TREATMENT_SUMMARY = "report_treatment_summary/{patientId}/{patientName}/{appointmentId}"
const val ROUTE_REPORT_PRESCRIPTION = "report_prescription/{patientId}/{patientName}/{appointmentId}"
const val ROUTE_REPORT_FOLLOWUP = "report_followup/{patientId}/{patientName}/{diagnosisId}"
const val ROUTE_REPORT_TRENDING_DISEASE = "report_trending_disease"
const val ROUTE_DEPT_TRENDING_DISEASES = "dept_trending_diseases/{department}"

const val ROUTE_ADMIN_REPORT_HUB = "admin_report_hub"
const val ROUTE_ADMIN_HOSPITAL_REPORT = "admin_hospital_report/{period}"
const val ROUTE_ADMIN_ANALYTICS_REPORT = "admin_analytics_report/{period}"

const val ROUTE_PATIENT_MEDICAL_HISTORY = "patient_medical_history/{patientId}"