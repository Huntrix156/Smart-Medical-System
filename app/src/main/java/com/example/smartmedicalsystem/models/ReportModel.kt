package com.example.smartmedicalsystem.models



data class ReportMeta(
    val reportId: String = "",
    val generatedBy: String = "",
    val generatedByName: String = "",
    val role: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val department: String = ""
)



data class DiagnosisReport(
    val meta: ReportMeta = ReportMeta(),
    val patientId: String = "",
    val patientName: String = "",
    val appointmentId: String = "",

    val chiefComplaint: String = "",
    val diagnosis: String = "",
    val diagnosisNotes: String = "",


    val treatmentPlan: String = "",
    val medicationsPresribed: String = "",
    val procedures: String = "",

    val recoveryProgress: String = "",
    val progressNotes: String = "",
    val nextReviewDate: String = ""
)

data class PrescriptionReport(
    val meta: ReportMeta = ReportMeta(),
    val patientId: String = "",
    val patientName: String = "",
    val appointmentId: String = "",
    val prescriptionItems: List<PrescriptionItem> = emptyList(),
    val instructions: String = "",
    val refillsAllowed: Int = 0,
    val validUntil: String = ""
)

data class PrescriptionItem(
    val medicineName: String = "",
    val dosage: String = "",
    val frequency: String = "",
    val duration: String = "",
    val notes: String = ""
)

data class TreatmentSummaryReport(
    val meta: ReportMeta = ReportMeta(),
    val patientId: String = "",
    val patientName: String = "",
    val appointmentId: String = "",
    val visitDate: String = "",
    val presentingSymptoms: String = "",
    val interventionsDone: String = "",
    val patientResponse: String = "",
    val followUpRequired: Boolean = false,
    val followUpDate: String = "",
    val followUpInstructions: String = ""
)

data class FollowUpReport(
    val meta: ReportMeta = ReportMeta(),
    val patientId: String = "",
    val patientName: String = "",
    val originalDiagnosisId: String = "",

    val currentRecoveryStatus: String = "",
    val symptomChanges: String = "",
    val complianceWithTreatment: String = "",
    val nextSteps: String = "",
    val discharged: Boolean = false
)



data class TrendingDiseaseReport(
    val reportId: String = "",
    val department: String = "",
    val submittedBy: String = "",
    val submittedByName: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val diseaseName: String = "",
    val caseCount: Int = 0,
    val severity: String = "",
    val affectedAgeGroup: String = "",
    val symptoms: String = "",
    val notes: String = "",
    val status: String = "pending",
    val peerReviews: List<PeerReview> = emptyList(),
    val addedDiseases: List<String> = emptyList()
)

data class PeerReview(
    val reviewerId: String = "",
    val reviewerName: String = "",
    val action: String = "",              // "approved"|"declined"|"added_disease"
    val comment: String = "",
    val addedDisease: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

// ─────────────────────────────────────────────
//  ADMIN / HOSPITAL MANAGEMENT REPORTS
// ─────────────────────────────────────────────

/** Admin daily snapshot — answers hospital mgmt questions */
data class HospitalManagementReport(
    val meta: ReportMeta = ReportMeta(),
    val reportDate: String = "",
    val period: String = "daily",          // "daily"|"weekly"|"monthly"
    // Q1 – How many patients used the system today?
    val totalPatientsToday: Int = 0,
    val newRegistrations: Int = 0,
    val activePatients: Int = 0,
    // Q2 – Which department was the busiest?
    val departmentStats: Map<String, Int> = emptyMap(),  // dept -> appointment count
    val busiestDepartment: String = "",
    // Q3 – Doctor workload analysis
    val doctorWorkload: List<DoctorWorkload> = emptyList(),
    val totalAppointments: Int = 0,
    val completedAppointments: Int = 0,
    val pendingAppointments: Int = 0,
    val cancelledAppointments: Int = 0
)

data class DoctorWorkload(
    val doctorId: String = "",
    val doctorName: String = "",
    val department: String = "",
    val appointmentsHandled: Int = 0,
    val avgConsultationTime: Int = 0,     // minutes
    val patientSatisfaction: Float = 0f
)

// ─────────────────────────────────────────────
//  SYSTEM ANALYTICS REPORTS
// ─────────────────────────────────────────────

data class SystemAnalyticsReport(
    val meta: ReportMeta = ReportMeta(),
    val period: String = "daily",         // "daily"|"weekly"|"monthly"
    val periodLabel: String = "",
    // Q1 – User growth
    val userGrowth: List<UserGrowthPoint> = emptyList(),
    val totalUsers: Int = 0,
    val newUsersThisPeriod: Int = 0,
    val growthRate: Float = 0f,           // percentage
    // Q2 – Appointment patterns
    val appointmentPatterns: List<AppointmentPattern> = emptyList(),
    val peakDay: String = "",
    val peakHour: String = "",
    val avgDailyAppointments: Float = 0f
)

data class UserGrowthPoint(
    val label: String = "",               // "Mon" | "Week 1" | "January" etc
    val patients: Int = 0,
    val doctors: Int = 0,
    val total: Int = 0
)

data class AppointmentPattern(
    val label: String = "",
    val count: Int = 0,
    val completed: Int = 0,
    val cancelled: Int = 0
)

// ─────────────────────────────────────────────
//  PATIENT-FACING (VIEW-ONLY)
// ─────────────────────────────────────────────

/** A patient's consolidated medical history view */
data class PatientMedicalHistory(
    val patientId: String = "",
    val patientName: String = "",
    // Q1 – What is wrong with me?
    val diagnoses: List<DiagnosisReport> = emptyList(),
    // Q2 – What treatment is ongoing?
    val activeTreatments: List<TreatmentSummaryReport> = emptyList(),
    // Q3 – Recovery progress
    val followUps: List<FollowUpReport> = emptyList(),
    val prescriptions: List<PrescriptionReport> = emptyList(),
    val appointmentSummaries: List<AppointmentSummary> = emptyList()
)

data class AppointmentSummary(
    val appointmentId: String = "",
    val date: String = "",
    val time: String = "",
    val doctorName: String = "",
    val department: String = "",
    val status: String = "",
    val reason: String = "",
    val notes: String = ""
)