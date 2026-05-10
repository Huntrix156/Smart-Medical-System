package com.example.smartmedicalsystem.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.smartmedicalsystem.models.AppointmentPattern
import com.example.smartmedicalsystem.models.AppointmentSummary
import com.example.smartmedicalsystem.models.DiagnosisReport
import com.example.smartmedicalsystem.models.DoctorWorkload
import com.example.smartmedicalsystem.models.FollowUpReport
import com.example.smartmedicalsystem.models.HospitalManagementReport
import com.example.smartmedicalsystem.models.PatientMedicalHistory
import com.example.smartmedicalsystem.models.PeerReview
import com.example.smartmedicalsystem.models.PrescriptionReport
import com.example.smartmedicalsystem.models.ReportMeta
import com.example.smartmedicalsystem.models.SystemAnalyticsReport
import com.example.smartmedicalsystem.models.TreatmentSummaryReport
import com.example.smartmedicalsystem.models.TrendingDiseaseReport
import com.example.smartmedicalsystem.models.UserGrowthPoint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ReportViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _message = mutableStateOf<String?>(null)
    val message: State<String?> = _message

    private val _diagnosisReports = mutableStateOf<List<DiagnosisReport>>(emptyList())
    val diagnosisReports: State<List<DiagnosisReport>> = _diagnosisReports

    private val _prescriptions = mutableStateOf<List<PrescriptionReport>>(emptyList())
    val prescriptions: State<List<PrescriptionReport>> = _prescriptions

    private val _treatmentSummaries = mutableStateOf<List<TreatmentSummaryReport>>(emptyList())
    val treatmentSummaries: State<List<TreatmentSummaryReport>> = _treatmentSummaries

    private val _followUpReports = mutableStateOf<List<FollowUpReport>>(emptyList())
    val followUpReports: State<List<FollowUpReport>> = _followUpReports


    private val _trendingDiseases = mutableStateOf<List<TrendingDiseaseReport>>(emptyList())
    val trendingDiseases: State<List<TrendingDiseaseReport>> = _trendingDiseases

    private val _hospitalReport = mutableStateOf<HospitalManagementReport?>(null)
    val hospitalReport: State<HospitalManagementReport?> = _hospitalReport

    private val _systemAnalytics = mutableStateOf<SystemAnalyticsReport?>(null)
    val systemAnalytics: State<SystemAnalyticsReport?> = _systemAnalytics

    private val _patientHistory = mutableStateOf<PatientMedicalHistory?>(null)
    val patientHistory: State<PatientMedicalHistory?> = _patientHistory

    private val _patientPrescriptions = mutableStateOf<List<PrescriptionReport>>(emptyList())
    val patientPrescriptions: State<List<PrescriptionReport>> = _patientPrescriptions

    private val _patientAppointmentSummaries = mutableStateOf<List<AppointmentSummary>>(emptyList())
    val patientAppointmentSummaries: State<List<AppointmentSummary>> = _patientAppointmentSummaries



    fun saveDiagnosisReport(report: DiagnosisReport, onDone: (Boolean) -> Unit) {
        _isLoading.value = true
        val key = db.child("reports").child("diagnosis").push().key ?: run {
            _message.value = "Could not generate report ID"
            onDone(false)
            return
        }
        val full = report.copy(meta = report.meta.copy(reportId = key))
        db.child("reports").child("diagnosis").child(key).setValue(full)
            .addOnSuccessListener {

                db.child("patientReports").child(report.patientId)
                    .child("diagnosis").child(key).setValue(true)
                _isLoading.value = false
                _message.value = "Diagnosis report saved"
                onDone(true)
            }
            .addOnFailureListener {
                _isLoading.value = false
                _message.value = it.message
                onDone(false)
            }
    }

    fun savePrescription(report: PrescriptionReport, onDone: (Boolean) -> Unit) {
        _isLoading.value = true
        val key = db.child("reports").child("prescriptions").push().key ?: run {
            onDone(false); return
        }
        val full = report.copy(meta = report.meta.copy(reportId = key))
        db.child("reports").child("prescriptions").child(key).setValue(full)
            .addOnSuccessListener {
                db.child("patientReports").child(report.patientId)
                    .child("prescriptions").child(key).setValue(true)
                _isLoading.value = false
                _message.value = "Prescription saved"
                onDone(true)
            }
            .addOnFailureListener {
                _isLoading.value = false
                _message.value = it.message
                onDone(false)
            }
    }

    fun saveTreatmentSummary(report: TreatmentSummaryReport, onDone: (Boolean) -> Unit) {
        _isLoading.value = true
        val key = db.child("reports").child("treatmentSummaries").push().key ?: run {
            onDone(false); return
        }
        val full = report.copy(meta = report.meta.copy(reportId = key))
        db.child("reports").child("treatmentSummaries").child(key).setValue(full)
            .addOnSuccessListener {
                db.child("patientReports").child(report.patientId)
                    .child("treatmentSummaries").child(key).setValue(true)
                _isLoading.value = false
                _message.value = "Treatment summary saved"
                onDone(true)
            }
            .addOnFailureListener {
                _isLoading.value = false
                _message.value = it.message
                onDone(false)
            }
    }

    fun saveFollowUpReport(report: FollowUpReport, onDone: (Boolean) -> Unit) {
        _isLoading.value = true
        val key = db.child("reports").child("followUps").push().key ?: run {
            onDone(false); return
        }
        val full = report.copy(meta = report.meta.copy(reportId = key))
        db.child("reports").child("followUps").child(key).setValue(full)
            .addOnSuccessListener {
                db.child("patientReports").child(report.patientId)
                    .child("followUps").child(key).setValue(true)
                _isLoading.value = false
                _message.value = "Follow-up report saved"
                onDone(true)
            }
            .addOnFailureListener {
                _isLoading.value = false
                _message.value = it.message
                onDone(false)
            }
    }



    fun submitTrendingDiseaseReport(report: TrendingDiseaseReport, onDone: (Boolean) -> Unit) {
        _isLoading.value = true
        val key = db.child("trendingDiseases").child(report.department).push().key ?: run {
            onDone(false); return
        }
        val full = report.copy(reportId = key, status = "pending")
        db.child("trendingDiseases").child(report.department).child(key).setValue(full)
            .addOnSuccessListener {
                _isLoading.value = false
                _message.value = "Trending disease report submitted for peer review"
                onDone(true)
            }
            .addOnFailureListener {
                _isLoading.value = false
                _message.value = it.message
                onDone(false)
            }
    }

    fun reviewTrendingDisease(
        department: String,
        reportId: String,
        reviewerId: String,
        reviewerName: String,
        action: String,
        comment: String,
        addedDisease: String = "",
        onDone: (Boolean) -> Unit
    ) {
        val ref = db.child("trendingDiseases").child(department).child(reportId)
        ref.get().addOnSuccessListener { snap ->
            val report = snap.getValue(TrendingDiseaseReport::class.java) ?: run {
                onDone(false); return@addOnSuccessListener
            }
            val review = PeerReview(
                reviewerId = reviewerId,
                reviewerName = reviewerName,
                action = action,
                comment = comment,
                addedDisease = addedDisease
            )
            val updatedReviews = report.peerReviews + review
            val updatedDiseases = if (addedDisease.isNotBlank())
                report.addedDiseases + addedDisease else report.addedDiseases

            val approvals = updatedReviews.count { it.action == "approved" }
            val newStatus = when {
                action == "declined" -> "declined"
                approvals >= 1 -> "approved"
                else -> "pending"
            }

            ref.updateChildren(mapOf(
                "peerReviews" to updatedReviews,
                "addedDiseases" to updatedDiseases,
                "status" to newStatus
            )).addOnSuccessListener {
                _message.value = "Review submitted"
                onDone(true)
            }.addOnFailureListener {
                _message.value = it.message
                onDone(false)
            }
        }.addOnFailureListener { onDone(false) }
    }

    fun listenTrendingDiseases(department: String) {
        db.child("trendingDiseases").child(department)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _trendingDiseases.value = snapshot.children.mapNotNull {
                        it.getValue(TrendingDiseaseReport::class.java)
                    }.sortedByDescending { it.createdAt }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }



    fun loadDoctorDiagnosisReports(doctorId: String) {
        db.child("reports").child("diagnosis")
            .orderByChild("meta/generatedBy").equalTo(doctorId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _diagnosisReports.value = snapshot.children.mapNotNull {
                        it.getValue(DiagnosisReport::class.java)
                    }.sortedByDescending { it.meta.createdAt }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun loadDoctorPrescriptions(doctorId: String) {
        db.child("reports").child("prescriptions")
            .orderByChild("meta/generatedBy").equalTo(doctorId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _prescriptions.value = snapshot.children.mapNotNull {
                        it.getValue(PrescriptionReport::class.java)
                    }.sortedByDescending { it.meta.createdAt }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }



    fun generateHospitalReport(period: String = "daily", periodLabel: String) {
        _isLoading.value = true
        val usersRef = db.child("users")
        val appointmentsRef = db.child("appointments")

        usersRef.get().addOnSuccessListener { usersSnap ->
            var patientCount = 0
            var doctorCount = 0
            usersSnap.children.forEach { child ->
                when (child.child("role").getValue(String::class.java)?.lowercase()) {
                    "patient" -> patientCount++
                    "doctor" -> doctorCount++
                }
            }

            appointmentsRef.get().addOnSuccessListener { apptSnap ->
                val deptMap = mutableMapOf<String, Int>()
                val doctorMap = mutableMapOf<String, DoctorWorkload>()
                var total = 0; var completed = 0; var pending = 0; var cancelled = 0

                for (patientNode in apptSnap.children) {
                    for (appt in patientNode.children) {
                        total++
                        val status = appt.child("status").getValue(String::class.java) ?: ""
                        val dept = appt.child("specialization").getValue(String::class.java) ?: "General"
                        val doctorId = appt.child("doctorId").getValue(String::class.java) ?: ""
                        val doctorName = appt.child("doctorName").getValue(String::class.java) ?: ""

                        when (status.lowercase()) {
                            "completed" -> completed++
                            "cancelled" -> cancelled++
                            else -> pending++
                        }
                        deptMap[dept] = (deptMap[dept] ?: 0) + 1

                        if (doctorId.isNotBlank()) {
                            val existing = doctorMap[doctorId] ?: DoctorWorkload(
                                doctorId = doctorId,
                                doctorName = doctorName,
                                department = dept
                            )
                            doctorMap[doctorId] = existing.copy(
                                appointmentsHandled = existing.appointmentsHandled + 1
                            )
                        }
                    }
                }

                val busiestDept = deptMap.maxByOrNull { it.value }?.key ?: "N/A"

                val reportRef = db.child("adminReports").child(period).push()
                val report = HospitalManagementReport(
                    meta = ReportMeta(
                        reportId = reportRef.key ?: "",
                        role = "Admin",
                        createdAt = System.currentTimeMillis()
                    ),
                    reportDate = periodLabel,
                    period = period,
                    totalPatientsToday = patientCount,
                    activePatients = patientCount,
                    departmentStats = deptMap,
                    busiestDepartment = busiestDept,
                    doctorWorkload = doctorMap.values.toList(),
                    totalAppointments = total,
                    completedAppointments = completed,
                    pendingAppointments = pending,
                    cancelledAppointments = cancelled
                )
                reportRef.setValue(report)
                _hospitalReport.value = report
                _isLoading.value = false
                _message.value = "${period.replaceFirstChar { it.uppercase() }} hospital report generated"
            }.addOnFailureListener { _isLoading.value = false }
        }.addOnFailureListener { _isLoading.value = false }
    }



    fun generateSystemAnalytics(period: String = "daily", periodLabel: String) {
        _isLoading.value = true
        db.child("users").get().addOnSuccessListener { usersSnap ->
            var patients = 0; var doctors = 0
            usersSnap.children.forEach {
                when (it.child("role").getValue(String::class.java)?.lowercase()) {
                    "patient" -> patients++
                    "doctor" -> doctors++
                }
            }
            val growthPoint = UserGrowthPoint(
                label = periodLabel,
                patients = patients,
                doctors = doctors,
                total = patients + doctors
            )

            db.child("appointments").get().addOnSuccessListener { apptSnap ->
                var total = 0; var completed = 0; var cancelled = 0
                for (patientNode in apptSnap.children)
                    for (appt in patientNode.children) {
                        total++
                        val s = appt.child("status").getValue(String::class.java)?.lowercase() ?: ""
                        if (s == "completed") completed++ else if (s == "cancelled") cancelled++
                    }

                val pattern = AppointmentPattern(
                    label = periodLabel,
                    count = total,
                    completed = completed,
                    cancelled = cancelled
                )

                val reportRef = db.child("systemAnalytics").child(period).push()
                val report = SystemAnalyticsReport(
                    meta = ReportMeta(reportId = reportRef.key ?: "", role = "Admin"),
                    period = period,
                    periodLabel = periodLabel,
                    userGrowth = listOf(growthPoint),
                    totalUsers = patients + doctors,
                    newUsersThisPeriod = patients + doctors,
                    growthRate = 0f,
                    appointmentPatterns = listOf(pattern),
                    avgDailyAppointments = total.toFloat()
                )
                reportRef.setValue(report)
                _systemAnalytics.value = report
                _isLoading.value = false
                _message.value = "System analytics report generated"
            }.addOnFailureListener { _isLoading.value = false }
        }.addOnFailureListener { _isLoading.value = false }
    }

    fun listenLatestHospitalReport(period: String) {
        db.child("adminReports").child(period)
            .limitToLast(1)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _hospitalReport.value = snapshot.children.lastOrNull()
                        ?.getValue(HospitalManagementReport::class.java)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun listenLatestSystemAnalytics(period: String) {
        db.child("systemAnalytics").child(period)
            .limitToLast(1)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _systemAnalytics.value = snapshot.children.lastOrNull()
                        ?.getValue(SystemAnalyticsReport::class.java)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }



    fun loadPatientMedicalHistory(patientId: String) {
        _isLoading.value = true
        val indexRef = db.child("patientReports").child(patientId)

        indexRef.get().addOnSuccessListener { indexSnap ->
            val diagIds = indexSnap.child("diagnosis").children.map { it.key ?: "" }
            val rxIds = indexSnap.child("prescriptions").children.map { it.key ?: "" }
            val txIds = indexSnap.child("treatmentSummaries").children.map { it.key ?: "" }
            val fuIds = indexSnap.child("followUps").children.map { it.key ?: "" }

            fetchMultiple<DiagnosisReport>(
                "reports/diagnosis", diagIds
            ) { diags ->
                fetchMultiple<PrescriptionReport>(
                    "reports/prescriptions", rxIds
                ) { rxs ->
                    fetchMultiple<TreatmentSummaryReport>(
                        "reports/treatmentSummaries", txIds
                    ) { txs ->
                        fetchMultiple<FollowUpReport>(
                            "reports/followUps", fuIds
                        ) { fus ->



                            db.child("appointments").child(patientId)
                                .get().addOnSuccessListener { apptSnap ->
                                    val summaries = apptSnap.children.mapNotNull { snap ->
                                        AppointmentSummary(
                                            appointmentId = snap.child("appointmentId").getValue(String::class.java) ?: "",
                                            date = snap.child("date").getValue(String::class.java) ?: "",
                                            time = snap.child("time").getValue(String::class.java) ?: "",
                                            doctorName = snap.child("doctorName").getValue(String::class.java) ?: "",
                                            department = snap.child("specialization").getValue(String::class.java) ?: "",
                                            status = snap.child("status").getValue(String::class.java) ?: "",
                                            reason = snap.child("reason").getValue(String::class.java) ?: ""
                                        )
                                    }
                                    _patientHistory.value = PatientMedicalHistory(
                                        patientId = patientId,
                                        diagnoses = diags,
                                        activeTreatments = txs,
                                        followUps = fus,
                                        prescriptions = rxs,
                                        appointmentSummaries = summaries
                                    )
                                    _patientPrescriptions.value = rxs
                                    _patientAppointmentSummaries.value = summaries
                                    _isLoading.value = false
                                }
                        }
                    }
                }
            }
        }.addOnFailureListener { _isLoading.value = false }
    }

    private inline fun <reified T> fetchMultiple(
        path: String,
        ids: List<String>,
        crossinline onResult: (List<T>) -> Unit
    ) {
        if (ids.isEmpty()) { onResult(emptyList()); return }
        val results = mutableListOf<T>()
        var remaining = ids.size
        ids.forEach { id ->
            db.child(path).child(id).get().addOnSuccessListener { snap ->
                snap.getValue(T::class.java)?.let { results.add(it) }
                if (--remaining == 0) onResult(results)
            }.addOnFailureListener {
                if (--remaining == 0) onResult(results)
            }
        }
    }

    fun clearMessage() { _message.value = null }
}