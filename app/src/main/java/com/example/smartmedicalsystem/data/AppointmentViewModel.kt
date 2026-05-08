package com.example.smartmedicalsystem.data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.smartmedicalsystem.models.Appointment
import com.example.smartmedicalsystem.models.AppointmentNotification
import com.example.smartmedicalsystem.models.DoctorProfile
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * AppointmentViewModel
 *
 * Handles all appointment-related Firebase operations:
 *  • Patient books an appointment
 *  • Doctor views their pending appointments
 *  • Doctor marks an appointment as completed or referred
 *  • On referral, a notification is written for the patient
 *  • Patient views all their appointments with live status
 */
class AppointmentViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().reference

    // ── Doctor's appointment list ─────────────────────────────────────────────
    private val _doctorAppointments = mutableStateOf<List<Appointment>>(emptyList())
    val doctorAppointments: State<List<Appointment>> = _doctorAppointments

    // ── Patient's appointment list ────────────────────────────────────────────
    private val _patientAppointments = mutableStateOf<List<Appointment>>(emptyList())
    val patientAppointments: State<List<Appointment>> = _patientAppointments

    // ── All doctors (for referral picker) ────────────────────────────────────
    private val _doctorList = mutableStateOf<List<DoctorProfile>>(emptyList())
    val doctorList: State<List<DoctorProfile>> = _doctorList

    // ── Loading / error state ─────────────────────────────────────────────────
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _successMessage = mutableStateOf<String?>(null)
    val successMessage: State<String?> = _successMessage

    // ── Listener handles ─────────────────────────────────────────────────────
    private var doctorApptListener: ValueEventListener? = null
    private var patientApptListener: ValueEventListener? = null
    private var doctorListListener: ValueEventListener? = null

    // ═══════════════════════════════════════════════════════════════════════════
    //  PATIENT — Book an appointment
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Saves a new appointment to:
     *   appointments/{patientId}/{appointmentId}
     *   doctorAppointments/{doctorId}/{appointmentId}
     *
     * Both nodes hold the same data so both parties see it instantly.
     */
    fun bookAppointment(
        patientId: String,
        patientName: String,
        doctorId: String,
        doctorName: String,
        date: String,
        time: String,
        reason: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val appointmentId = db.push().key ?: return
        val appointment = Appointment(
            appointmentId = appointmentId,
            patientId = patientId,
            patientName = patientName,
            doctorId = doctorId,
            doctorName = doctorName,
            date = date,
            time = time,
            reason = reason,
            status = "pending"
        )

        val updates = mapOf(
            "appointments/$patientId/$appointmentId" to appointment,
            "doctorAppointments/$doctorId/$appointmentId" to appointment
        )

        db.updateChildren(updates)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it.message ?: "Failed to book appointment") }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  DOCTOR — Listen for appointments assigned to this doctor
    // ═══════════════════════════════════════════════════════════════════════════

    fun listenDoctorAppointments(doctorId: String) {
        if (doctorApptListener != null) return

        doctorApptListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Appointment>()
                for (child in snapshot.children) {
                    child.getValue(Appointment::class.java)?.let { list.add(it) }
                }
                // Sort: pending first, then by date
                _doctorAppointments.value = list.sortedWith(
                    compareBy({ if (it.status == "pending") 0 else 1 }, { it.date })
                )
            }
            override fun onCancelled(error: DatabaseError) {
                _errorMessage.value = error.message
            }
        }
        db.child("doctorAppointments").child(doctorId)
            .addValueEventListener(doctorApptListener!!)
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  PATIENT — Listen for all appointments booked by this patient
    // ═══════════════════════════════════════════════════════════════════════════

    fun listenPatientAppointments(patientId: String) {
        if (patientApptListener != null) return

        patientApptListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Appointment>()
                for (child in snapshot.children) {
                    child.getValue(Appointment::class.java)?.let { list.add(it) }
                }
                _patientAppointments.value = list.sortedByDescending { it.date }
            }
            override fun onCancelled(error: DatabaseError) {
                _errorMessage.value = error.message
            }
        }
        db.child("appointments").child(patientId)
            .addValueEventListener(patientApptListener!!)
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  DOCTOR — Mark appointment as Completed
    // ═══════════════════════════════════════════════════════════════════════════

    fun markCompleted(
        appointment: Appointment,
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        val message = "Your appointment with Dr. ${appointment.doctorName} on ${appointment.date} at ${appointment.time} has been marked as Completed."
        val updates = mapOf(
            "appointments/${appointment.patientId}/${appointment.appointmentId}/status" to "completed",
            "appointments/${appointment.patientId}/${appointment.appointmentId}/notificationMessage" to message,
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/status" to "completed",
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/notificationMessage" to message
        )
        db.updateChildren(updates)
            .addOnSuccessListener {
                sendNotification(
                    patientId = appointment.patientId,
                    title = "Appointment Completed",
                    message = message
                )
                onSuccess()
            }
            .addOnFailureListener { onFailure(it.message ?: "Update failed") }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  DOCTOR — Refer appointment to another doctor
    // ═══════════════════════════════════════════════════════════════════════════

    fun referAppointment(
        appointment: Appointment,
        newDoctor: DoctorProfile,
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        val message = "Your appointment originally with Dr. ${appointment.doctorName} on ${appointment.date} at ${appointment.time} has been referred to Dr. ${newDoctor.name}. Please attend as scheduled."

        val updates = mapOf(
            // Update patient's copy
            "appointments/${appointment.patientId}/${appointment.appointmentId}/status" to "referred",
            "appointments/${appointment.patientId}/${appointment.appointmentId}/referredDoctorId" to newDoctor.uid,
            "appointments/${appointment.patientId}/${appointment.appointmentId}/referredDoctorName" to newDoctor.name,
            "appointments/${appointment.patientId}/${appointment.appointmentId}/notificationMessage" to message,
            // Update original doctor's copy
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/status" to "referred",
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/referredDoctorId" to newDoctor.uid,
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/referredDoctorName" to newDoctor.name,
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/notificationMessage" to message,
            // Create a copy for the new (referred) doctor
            "doctorAppointments/${newDoctor.uid}/${appointment.appointmentId}/appointmentId" to appointment.appointmentId,
            "doctorAppointments/${newDoctor.uid}/${appointment.appointmentId}/patientId" to appointment.patientId,
            "doctorAppointments/${newDoctor.uid}/${appointment.appointmentId}/patientName" to appointment.patientName,
            "doctorAppointments/${newDoctor.uid}/${appointment.appointmentId}/doctorId" to newDoctor.uid,
            "doctorAppointments/${newDoctor.uid}/${appointment.appointmentId}/doctorName" to newDoctor.name,
            "doctorAppointments/${newDoctor.uid}/${appointment.appointmentId}/date" to appointment.date,
            "doctorAppointments/${newDoctor.uid}/${appointment.appointmentId}/time" to appointment.time,
            "doctorAppointments/${newDoctor.uid}/${appointment.appointmentId}/reason" to appointment.reason,
            "doctorAppointments/${newDoctor.uid}/${appointment.appointmentId}/status" to "pending"
        )

        db.updateChildren(updates)
            .addOnSuccessListener {
                sendNotification(
                    patientId = appointment.patientId,
                    title = "Appointment Referred",
                    message = message
                )
                onSuccess()
            }
            .addOnFailureListener { onFailure(it.message ?: "Referral failed") }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  Load all doctors for the referral picker
    // ═══════════════════════════════════════════════════════════════════════════

    fun loadDoctors(excludeDoctorId: String = "") {
        if (doctorListListener != null) return

        doctorListListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<DoctorProfile>()
                for (child in snapshot.children) {
                    val uid = child.key ?: continue
                    if (uid == excludeDoctorId) continue
                    val name = child.child("name").getValue(String::class.java)
                        ?: child.child("firstname").getValue(String::class.java) ?: "Unknown"
                    val spec = child.child("specialization").getValue(String::class.java) ?: ""
                    list.add(DoctorProfile(uid = uid, name = name, specialization = spec))
                }
                _doctorList.value = list
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        db.child("doctors").addValueEventListener(doctorListListener!!)
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  Private — write notification to Firebase
    // ═══════════════════════════════════════════════════════════════════════════

    private fun sendNotification(patientId: String, title: String, message: String) {
        val notifId = db.push().key ?: return
        val notification = AppointmentNotification(
            notificationId = notifId,
            title = title,
            message = message,
            timestamp = System.currentTimeMillis(),
            read = false
        )
        db.child("notifications").child(patientId).child(notifId).setValue(notification)
    }

    fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        doctorApptListener?.let {
            // listeners cleaned up automatically when ViewModel is destroyed
        }
        patientApptListener?.let {}
        doctorListListener?.let { db.child("doctors").removeEventListener(it) }
    }
}
