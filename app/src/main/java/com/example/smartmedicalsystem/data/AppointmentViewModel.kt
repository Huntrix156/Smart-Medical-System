package com.example.smartmedicalsystem.data//package com.example.smartmedicalsystem.data
//
//
//import androidx.compose.runtime.State
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.ViewModel
//import com.example.smartmedicalsystem.models.Appointment
//import com.example.smartmedicalsystem.models.AppointmentNotification
//import com.example.smartmedicalsystem.models.DoctorProfile
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//class AppointmentViewModel : ViewModel() {
//
//    private val db = FirebaseDatabase.getInstance().reference
//
//
//
//    private val _doctorAppointments = mutableStateOf<List<Appointment>>(emptyList())
//    val doctorAppointments: State<List<Appointment>> = _doctorAppointments
//
//    private val _patientAppointments = mutableStateOf<List<Appointment>>(emptyList())
//    val patientAppointments: State<List<Appointment>> = _patientAppointments
//
//
//    private val _adminAppointments = mutableStateOf<List<Appointment>>(emptyList())
//    val adminAppointments: State<List<Appointment>> = _adminAppointments
//
//    private val _doctorList = mutableStateOf<List<DoctorProfile>>(emptyList())
//    val doctorList: State<List<DoctorProfile>> = _doctorList
//
//    private val _isLoading = mutableStateOf(false)
//    val isLoading: State<Boolean> = _isLoading
//
//    private val _errorMessage = mutableStateOf<String?>(null)
//    val errorMessage: State<String?> = _errorMessage
//
//    private val _successMessage = mutableStateOf<String?>(null)
//    val successMessage: State<String?> = _successMessage
//
//    private var doctorApptListener: ValueEventListener? = null
//    private var patientApptListener: ValueEventListener? = null
//    private var adminApptListener: ValueEventListener? = null
//    private var doctorListListener: ValueEventListener? = null
//
//
//
//    fun bookAppointment(
//        patientId: String,
//        patientName: String,
//        reason: String,
//        date: String,
//        time: String,
//        specialization: String = "",
//        onSuccess: () -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        _isLoading.value = true
//        val appointmentId = db.push().key ?: run {
//            _isLoading.value = false
//            onFailure("Could not generate appointment ID")
//            return
//        }
//
//        val appointment = Appointment(
//            appointmentId = appointmentId,
//            patientId = patientId,
//            patientName = patientName,
//            date = date,
//            time = time,
//            reason = reason,
//            status = "pending_admin",
//            specialization = specialization,
//            notificationMessage = "Your appointment on $date at $time has been booked.",
//            referredDoctorId = "",
//            referredDoctorName = "",
//            doctorId = "",
//            doctorName = "",
//            referralNote = ""
//        )
//
//        val updates: Map<String, Any> = mapOf(
//            "appointments/$patientId/$appointmentId" to appointment,
//            "adminAppointments/$appointmentId" to appointment
//        )
//
//        db.updateChildren(updates)
//            .addOnSuccessListener {
//                _isLoading.value = false
//                _successMessage.value = "Appointment booked! The admin will assign you a doctor."
//                onSuccess()
//            }
//            .addOnFailureListener {
//                _isLoading.value = false
//                _errorMessage.value = it.message
//                onFailure(it.message ?: "Failed to book appointment")
//            }
//    }
//
//
//
//    fun listenAdminAppointments() {
//        if (adminApptListener != null) return
//
//        adminApptListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val list = mutableListOf<Appointment>()
//                for (child in snapshot.children) {
//                    child.getValue(Appointment::class.java)?.let { list.add(it) }
//                }
//                // Sort: referral alerts first, then pending_admin, then others
//                _adminAppointments.value = list.sortedWith(
//                    compareBy(
//                        {
//                            when (it.status) {
//                                "referral_requested" -> 0
//                                "pending_admin"      -> 1
//                                "assigned"           -> 2
//                                "taken"              -> 3
//                                else                 -> 4
//                            }
//                        },
//                        { it.date }
//                    )
//                )
//            }
//            override fun onCancelled(error: DatabaseError) {
//                _errorMessage.value = error.message
//            }
//        }
//        db.child("adminAppointments").addValueEventListener(adminApptListener!!)
//    }
//
//
//
//
//    fun assignDoctorToAppointment(
//        appointment: Appointment,
//        doctor: DoctorProfile,
//        onSuccess: () -> Unit = {},
//        onFailure: (String) -> Unit = {}
//    ) {
//        _isLoading.value = true
//        val msg = "Your appointment on ${appointment.date} at ${appointment.time} " +
//                "has been assigned to Dr. ${doctor.name} (${doctor.specialization})."
//
//        val updatedAppointment = appointment.copy(
//            doctorId = doctor.uid,
//            doctorName = doctor.name,
//            status = "assigned",
//            referralNote = "",
//            notificationMessage = msg
//        )
//
//        val updates: Map<String, Any> = mapOf(
//            "adminAppointments/${appointment.appointmentId}" to updatedAppointment,
//            "appointments/${appointment.patientId}/${appointment.appointmentId}" to updatedAppointment,
//            "doctorAppointments/${doctor.uid}/${appointment.appointmentId}" to updatedAppointment
//        )
//
//        db.updateChildren(updates)
//            .addOnSuccessListener {
//                _isLoading.value = false
//                sendNotification(
//                    patientId = appointment.patientId,
//                    title = "Doctor Assigned",
//                    message = msg
//                )
//                onSuccess()
//            }
//            .addOnFailureListener {
//                _isLoading.value = false
//                onFailure(it.message ?: "Assignment failed")
//            }
//    }
//
//
//
//    fun listenDoctorAppointments(doctorId: String) {
//        if (doctorApptListener != null) return
//
//        doctorApptListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val list = mutableListOf<Appointment>()
//                for (child in snapshot.children) {
//                    child.getValue(Appointment::class.java)?.let { list.add(it) }
//                }
//
//                _doctorAppointments.value = list.sortedWith(
//                    compareBy(
//                        {
//                            when (it.status) {
//                                "assigned", "taken" -> 0
//                                else                -> 1
//                            }
//                        },
//                        { it.date }
//                    )
//                )
//            }
//            override fun onCancelled(error: DatabaseError) {
//                _errorMessage.value = error.message
//            }
//        }
//        db.child("doctorAppointments").child(doctorId)
//            .addValueEventListener(doctorApptListener!!)
//    }
//
//
//
//
//    fun requestReferral(
//        appointment: Appointment,
//        referralNote: String,
//        onSuccess: () -> Unit = {},
//        onFailure: (String) -> Unit = {}
//    ) {
//        _isLoading.value = true
//        val updates: Map<String, Any> = mapOf(
//            "adminAppointments/${appointment.appointmentId}/status"       to "referral_requested",
//            "adminAppointments/${appointment.appointmentId}/referralNote" to referralNote,
//            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/status"       to "referral_requested",
//            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/referralNote" to referralNote,
//            "appointments/${appointment.patientId}/${appointment.appointmentId}/status"       to "referral_requested",
//            "appointments/${appointment.patientId}/${appointment.appointmentId}/referralNote" to referralNote
//        )
//        db.updateChildren(updates)
//            .addOnSuccessListener {
//                _isLoading.value = false
//                onSuccess()
//            }
//            .addOnFailureListener {
//                _isLoading.value = false
//                onFailure(it.message ?: "Referral request failed")
//            }
//    }
//
//
//
//    fun proceedWithAppointment(
//        appointment: Appointment,
//        onSuccess: () -> Unit = {},
//        onFailure: (String) -> Unit = {}
//    ) {
//        _isLoading.value = true
//        val msg = "Your appointment with Dr. ${appointment.doctorName} on ${appointment.date} " +
//                "at ${appointment.time} is being processed."
//
//        val updates: Map<String, Any> = mapOf(
//            "adminAppointments/${appointment.appointmentId}/status"                to "taken",
//            "adminAppointments/${appointment.appointmentId}/notificationMessage"   to msg,
//            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/status"              to "taken",
//            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/notificationMessage" to msg,
//            "appointments/${appointment.patientId}/${appointment.appointmentId}/status"              to "taken",
//            "appointments/${appointment.patientId}/${appointment.appointmentId}/notificationMessage" to msg
//        )
//        db.updateChildren(updates)
//            .addOnSuccessListener {
//                _isLoading.value = false
//                sendNotification(
//                    patientId = appointment.patientId,
//                    title = "Appointment Confirmed",
//                    message = msg
//                )
//                onSuccess()
//            }
//            .addOnFailureListener {
//                _isLoading.value = false
//                onFailure(it.message ?: "Could not proceed")
//            }
//    }
//
//
//    fun markCompleted(
//        appointment: Appointment,
//        onSuccess: () -> Unit = {},
//        onFailure: (String) -> Unit = {}
//    ) {
//        _isLoading.value = true
//        val msg = "Your appointment with Dr. ${appointment.doctorName} on ${appointment.date} " +
//                "at ${appointment.time} has been marked as Completed."
//
//        val updates: Map<String, Any> = mapOf(
//            "adminAppointments/${appointment.appointmentId}/status"                to "completed",
//            "adminAppointments/${appointment.appointmentId}/notificationMessage"   to msg,
//            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/status"              to "completed",
//            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/notificationMessage" to msg,
//            "appointments/${appointment.patientId}/${appointment.appointmentId}/status"              to "completed",
//            "appointments/${appointment.patientId}/${appointment.appointmentId}/notificationMessage" to msg
//        )
//        db.updateChildren(updates)
//            .addOnSuccessListener {
//                _isLoading.value = false
//                sendNotification(
//                    patientId = appointment.patientId,
//                    title = "Appointment Completed",
//                    message = msg
//                )
//                onSuccess()
//            }
//            .addOnFailureListener {
//                _isLoading.value = false
//                onFailure(it.message ?: "Update failed")
//            }
//    }
//
//
//    fun listenPatientAppointments(patientId: String) {
//        if (patientApptListener != null) return
//
//        patientApptListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val list = mutableListOf<Appointment>()
//                for (child in snapshot.children) {
//                    child.getValue(Appointment::class.java)?.let { list.add(it) }
//                }
//                _patientAppointments.value = list.sortedByDescending { it.date }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                _errorMessage.value = error.message
//            }
//        }
//        db.child("appointments").child(patientId)
//            .addValueEventListener(patientApptListener!!)
//    }
//
//
//
//
//    fun loadDoctors(
//        specialization: String = "",
//        excludeDoctorId: String = ""
//    ) {
//        if (doctorListListener != null) return
//
//        doctorListListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val list = mutableListOf<DoctorProfile>()
//                for (child in snapshot.children) {
//                    val uid = child.key ?: continue
//                    if (uid == excludeDoctorId) continue
//                    val name = child.child("name").getValue(String::class.java)
//                        ?: child.child("firstname").getValue(String::class.java) ?: "Unknown"
//                    val spec = child.child("specialization").getValue(String::class.java) ?: ""
//                    if (specialization.isBlank() || spec.equals(specialization, ignoreCase = true)) {
//                        list.add(DoctorProfile(uid = uid, name = name, specialization = spec))
//                    }
//                }
//                _doctorList.value = list
//            }
//            override fun onCancelled(error: DatabaseError) {}
//        }
//        db.child("doctors").addValueEventListener(doctorListListener!!)
//    }
//
//    fun reloadDoctors(specialization: String = "", excludeDoctorId: String = "") {
//        doctorListListener?.let { db.child("doctors").removeEventListener(it) }
//        doctorListListener = null
//        loadDoctors(specialization, excludeDoctorId)
//    }
//
//
//    private fun sendNotification(patientId: String, title: String, message: String) {
//        val notifId = db.push().key ?: return
//        val notification = AppointmentNotification(
//            notificationId = notifId,
//            title = title,
//            message = message,
//            timestamp = System.currentTimeMillis(),
//            read = false
//        )
//        db.child("notifications").child(patientId).child(notifId).setValue(notification)
//    }
//
//    fun clearMessages() {
//        _errorMessage.value = null
//        _successMessage.value = null
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        doctorListListener?.let { db.child("doctors").removeEventListener(it) }
//    }
//}



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
import java.util.HashMap

class AppointmentViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().reference

    private val _doctorAppointments = mutableStateOf<List<Appointment>>(emptyList())
    val doctorAppointments: State<List<Appointment>> = _doctorAppointments

    private val _patientAppointments = mutableStateOf<List<Appointment>>(emptyList())
    val patientAppointments: State<List<Appointment>> = _patientAppointments

    private val _adminAppointments = mutableStateOf<List<Appointment>>(emptyList())
    val adminAppointments: State<List<Appointment>> = _adminAppointments

    private val _doctorList = mutableStateOf<List<DoctorProfile>>(emptyList())
    val doctorList: State<List<DoctorProfile>> = _doctorList

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _successMessage = mutableStateOf<String?>(null)
    val successMessage: State<String?> = _successMessage

    private var doctorApptListener: ValueEventListener? = null
    private var patientApptListener: ValueEventListener? = null
    private var adminApptListener: ValueEventListener? = null
    private var doctorListListener: ValueEventListener? = null

    // ── Book appointment ──────────────────────────────────────────────────────
    // Patient now selects a doctor directly. The appointment is written to:
    //   appointments/{patientId}/{appointmentId}   — patient can see it
    //   doctorAppointments/{doctorId}/{appointmentId} — doctor can see it
    //   adminAppointments/{appointmentId}           — admin can manage it
    fun bookAppointment(
        patientId: String,
        patientName: String,
        reason: String,
        date: String,
        time: String,
        specialization: String = "",
        doctorId: String = "",
        doctorName: String = "",
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        _isLoading.value = true
        val appointmentId = db.push().key ?: run {
            _isLoading.value = false
            onFailure("Could not generate appointment ID")
            return
        }

        // If the patient picked a doctor, mark status as "assigned" straight away.
        // Otherwise it waits for admin assignment (legacy flow).
        val initialStatus = if (doctorId.isNotBlank()) "assigned" else "pending_admin"
        val notifMsg = if (doctorId.isNotBlank())
            "Your appointment with Dr. $doctorName on $date at $time has been booked."
        else
            "Your appointment on $date at $time has been booked. The admin will assign a doctor."

        val appointment = Appointment(
            appointmentId       = appointmentId,
            patientId           = patientId,
            patientName         = patientName,
            date                = date,
            time                = time,
            reason              = reason,
            status              = initialStatus,
            specialization      = specialization,
            doctorId            = doctorId,
            doctorName          = doctorName,
            referredDoctorId    = "",
            referredDoctorName  = "",
            notificationMessage = notifMsg,
            referralNote        = ""
        )

        val updates = mutableMapOf<String, Any>(
            "appointments/$patientId/$appointmentId"  to appointment,
            "adminAppointments/$appointmentId"         to appointment
        )

        // Write to doctor node immediately when a doctor was chosen
        if (doctorId.isNotBlank()) {
            updates["doctorAppointments/$doctorId/$appointmentId"] = appointment
        }

        db.updateChildren(updates)
            .addOnSuccessListener {
                _isLoading.value = false
                _successMessage.value = if (doctorId.isNotBlank())
                    "Appointment booked with Dr. $doctorName!"
                else
                    "Appointment booked! The admin will assign you a doctor."
                onSuccess()
            }
            .addOnFailureListener {
                _isLoading.value = false
                _errorMessage.value = it.message
                onFailure(it.message ?: "Failed to book appointment")
            }
    }

    // ── Admin listeners ───────────────────────────────────────────────────────
    fun listenAdminAppointments() {
        if (adminApptListener != null) return

        adminApptListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Appointment>()
                for (child in snapshot.children) {
                    child.getValue(Appointment::class.java)?.let { list.add(it) }
                }
                _adminAppointments.value = list.sortedWith(
                    compareBy(
                        {
                            when (it.status) {
                                "referral_requested" -> 0
                                "pending_admin"      -> 1
                                "assigned"           -> 2
                                "taken"              -> 3
                                else                 -> 4
                            }
                        },
                        { it.date }
                    )
                )
            }
            override fun onCancelled(error: DatabaseError) {
                _errorMessage.value = error.message
            }
        }
        db.child("adminAppointments").addValueEventListener(adminApptListener!!)
    }

    // ── Assign / reassign doctor (admin) ─────────────────────────────────────
    fun assignDoctorToAppointment(
        appointment: Appointment,
        doctor: DoctorProfile,
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        _isLoading.value = true
        val msg = "Your appointment on ${appointment.date} at ${appointment.time} " +
                "has been assigned to Dr. ${doctor.name} (${doctor.specialization})."

        val updatedAppointment = appointment.copy(
            doctorId    = doctor.uid,
            doctorName  = doctor.name,
            status      = "assigned",
            referralNote = "",
            notificationMessage = msg
        )

        val updates = mutableMapOf<String, Any>(
            "adminAppointments/${appointment.appointmentId}"                               to updatedAppointment,
            "appointments/${appointment.patientId}/${appointment.appointmentId}"           to updatedAppointment,
            "doctorAppointments/${doctor.uid}/${appointment.appointmentId}"                to updatedAppointment
        )

        // If previously assigned to a different doctor, remove their copy
        if (appointment.doctorId.isNotBlank() && appointment.doctorId != doctor.uid) {
            updates["doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}"] =
                HashMap<String, Any>() // will be overwritten by removeValue below
        }

        db.updateChildren(updates)
            .addOnSuccessListener {
                // Clean up old doctor's node if reassigned
                if (appointment.doctorId.isNotBlank() && appointment.doctorId != doctor.uid) {
                    db.child("doctorAppointments")
                        .child(appointment.doctorId)
                        .child(appointment.appointmentId)
                        .removeValue()
                }
                _isLoading.value = false
                sendNotification(
                    patientId = appointment.patientId,
                    title     = "Doctor Assigned",
                    message   = msg
                )
                onSuccess()
            }
            .addOnFailureListener {
                _isLoading.value = false
                onFailure(it.message ?: "Assignment failed")
            }
    }

    // ── Doctor listeners ──────────────────────────────────────────────────────
    fun listenDoctorAppointments(doctorId: String) {
        if (doctorApptListener != null) return

        doctorApptListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Appointment>()
                for (child in snapshot.children) {
                    child.getValue(Appointment::class.java)?.let { list.add(it) }
                }
                _doctorAppointments.value = list.sortedWith(
                    compareBy(
                        {
                            when (it.status) {
                                "assigned", "taken" -> 0
                                else                -> 1
                            }
                        },
                        { it.date }
                    )
                )
            }
            override fun onCancelled(error: DatabaseError) {
                _errorMessage.value = error.message
            }
        }
        db.child("doctorAppointments").child(doctorId)
            .addValueEventListener(doctorApptListener!!)
    }

    // ── Patient listeners ─────────────────────────────────────────────────────
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

    // ── Referral ──────────────────────────────────────────────────────────────
    fun requestReferral(
        appointment: Appointment,
        referralNote: String,
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        _isLoading.value = true
        val updates: Map<String, Any> = mapOf(
            "adminAppointments/${appointment.appointmentId}/status"       to "referral_requested",
            "adminAppointments/${appointment.appointmentId}/referralNote" to referralNote,
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/status"       to "referral_requested",
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/referralNote" to referralNote,
            "appointments/${appointment.patientId}/${appointment.appointmentId}/status"       to "referral_requested",
            "appointments/${appointment.patientId}/${appointment.appointmentId}/referralNote" to referralNote
        )
        db.updateChildren(updates)
            .addOnSuccessListener {
                _isLoading.value = false
                onSuccess()
            }
            .addOnFailureListener {
                _isLoading.value = false
                onFailure(it.message ?: "Referral request failed")
            }
    }

    // ── Proceed / complete ────────────────────────────────────────────────────
    fun proceedWithAppointment(
        appointment: Appointment,
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        _isLoading.value = true
        val msg = "Your appointment with Dr. ${appointment.doctorName} on ${appointment.date} " +
                "at ${appointment.time} is being processed."

        val updates: Map<String, Any> = mapOf(
            "adminAppointments/${appointment.appointmentId}/status"                to "taken",
            "adminAppointments/${appointment.appointmentId}/notificationMessage"   to msg,
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/status"              to "taken",
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/notificationMessage" to msg,
            "appointments/${appointment.patientId}/${appointment.appointmentId}/status"              to "taken",
            "appointments/${appointment.patientId}/${appointment.appointmentId}/notificationMessage" to msg
        )
        db.updateChildren(updates)
            .addOnSuccessListener {
                _isLoading.value = false
                sendNotification(patientId = appointment.patientId, title = "Appointment Confirmed", message = msg)
                onSuccess()
            }
            .addOnFailureListener {
                _isLoading.value = false
                onFailure(it.message ?: "Could not proceed")
            }
    }

    fun markCompleted(
        appointment: Appointment,
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        _isLoading.value = true
        val msg = "Your appointment with Dr. ${appointment.doctorName} on ${appointment.date} " +
                "at ${appointment.time} has been marked as Completed."

        val updates: Map<String, Any> = mapOf(
            "adminAppointments/${appointment.appointmentId}/status"                to "completed",
            "adminAppointments/${appointment.appointmentId}/notificationMessage"   to msg,
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/status"              to "completed",
            "doctorAppointments/${appointment.doctorId}/${appointment.appointmentId}/notificationMessage" to msg,
            "appointments/${appointment.patientId}/${appointment.appointmentId}/status"              to "completed",
            "appointments/${appointment.patientId}/${appointment.appointmentId}/notificationMessage" to msg
        )
        db.updateChildren(updates)
            .addOnSuccessListener {
                _isLoading.value = false
                sendNotification(patientId = appointment.patientId, title = "Appointment Completed", message = msg)
                onSuccess()
            }
            .addOnFailureListener {
                _isLoading.value = false
                onFailure(it.message ?: "Update failed")
            }
    }

    // ── Doctor list (for booking + admin assign) ──────────────────────────────
    fun loadDoctors(
        specialization: String = "",
        excludeDoctorId: String = ""
    ) {
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
                    if (specialization.isBlank() || spec.equals(specialization, ignoreCase = true)) {
                        list.add(DoctorProfile(uid = uid, name = name, specialization = spec))
                    }
                }
                _doctorList.value = list
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        db.child("doctors").addValueEventListener(doctorListListener!!)
    }

    fun reloadDoctors(specialization: String = "", excludeDoctorId: String = "") {
        doctorListListener?.let { db.child("doctors").removeEventListener(it) }
        doctorListListener = null
        loadDoctors(specialization, excludeDoctorId)
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private fun sendNotification(patientId: String, title: String, message: String) {
        val notifId = db.push().key ?: return
        val notification = AppointmentNotification(
            notificationId = notifId,
            title          = title,
            message        = message,
            timestamp      = System.currentTimeMillis(),
            read           = false
        )
        db.child("notifications").child(patientId).child(notifId).setValue(notification)
    }

    fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        doctorListListener?.let { db.child("doctors").removeEventListener(it) }
        doctorApptListener?.let { db.child("doctorAppointments").removeEventListener(it) }
        patientApptListener?.let { db.child("appointments").removeEventListener(it) }
        adminApptListener?.let { db.child("adminAppointments").removeEventListener(it) }
    }
}