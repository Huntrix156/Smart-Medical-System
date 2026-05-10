package com.example.smartmedicalsystem.data//package com.example.smartmedicalsystem.data
//
//import androidx.lifecycle.ViewModel
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.State
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//
//class DashboardStatsViewModel : ViewModel() {
//
//    private val db = FirebaseDatabase.getInstance().reference
//
//
//    private val _doctorCount = mutableStateOf(0)
//    val doctorCount: State<Int> = _doctorCount
//
//
//    private val _upcomingAppointments = mutableStateOf(0)
//    val upcomingAppointments: State<Int> = _upcomingAppointments
//
//    private val _appVisits = mutableStateOf(0)
//    val appVisits: State<Int> = _appVisits
//
//
//    private val _pendingAppointments = mutableStateOf(0)
//    val pendingAppointments: State<Int> = _pendingAppointments
//
//    private val _patientCount = mutableStateOf(0)
//    val patientCount: State<Int> = _patientCount
//
//    private var doctorListener: ValueEventListener? = null
//    private var upcomingListener: ValueEventListener? = null
//    private var visitsListener: ValueEventListener? = null
//    private var pendingListener: ValueEventListener? = null
//    private var patientListener: ValueEventListener? = null
//
//
//
//
//    fun listenDoctorCount() {
//        if (doctorListener != null) return
//
//        doctorListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                val count = snapshot.children.count { child ->
//                    child.child("role")
//                        .getValue(String::class.java)
//                        .equals("Doctor", ignoreCase = true)
//                }
//                _doctorCount.value = count
//            }
//            override fun onCancelled(error: DatabaseError) {
//            }
//        }
//        db.child("users").addValueEventListener(doctorListener!!)
//    }
//
//
//
//
//    fun listenPatientStats(patientUid: String) {
//        if (upcomingListener != null) return  // already attached
//
//        upcomingListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var count = 0
//                for (child in snapshot.children) {
//                    val status = child.child("status").getValue(String::class.java) ?: ""
//                    if (status.equals("upcoming", ignoreCase = true)) count++
//                }
//                _upcomingAppointments.value = count
//            }
//            override fun onCancelled(error: DatabaseError) {}
//        }
//        db.child("appointments").child(patientUid)
//            .addValueEventListener(upcomingListener!!)
//
//
//        val visitsRef = db.child("appVisits").child(patientUid).child("count")
//        visitsRef.get().addOnSuccessListener { snap ->
//            val current = snap.getValue(Int::class.java) ?: 0
//            val updated = current + 1
//            visitsRef.setValue(updated)
//        }
//
//        visitsListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                _appVisits.value = snapshot.getValue(Int::class.java) ?: 0
//            }
//            override fun onCancelled(error: DatabaseError) {}
//        }
//        visitsRef.addValueEventListener(visitsListener!!)
//    }
//
//
//    fun listenDoctorPendingAppointments(doctorUid: String) {
//        if (pendingListener != null) return
//
//        pendingListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var count = 0
//
//
//                for (patientNode in snapshot.children) {
//                    for (appt in patientNode.children) {
//                        val assignedDoctor = appt.child("doctorId").getValue(String::class.java) ?: ""
//                        val status = appt.child("status").getValue(String::class.java) ?: ""
//                        if (assignedDoctor == doctorUid && status.equals("pending", ignoreCase = true)) {
//                            count++
//                        }
//                    }
//                }
//                _pendingAppointments.value = count
//            }
//            override fun onCancelled(error: DatabaseError) {}
//        }
//        db.child("appointments").addValueEventListener(pendingListener!!)
//    }
//
//
//
//    fun listenPatientCount() {
//        if (patientListener != null) return
//
//        patientListener = object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val count = snapshot.children.count { child ->
//                    child.child("role")
//                        .getValue(String::class.java)
//                        .equals("Patient", ignoreCase = true)
//                }
//                _patientCount.value = count
//            }
//            override fun onCancelled(error: DatabaseError) {}
//        }
//        db.child("users").addValueEventListener(patientListener!!)
//    }
//
//
//    override fun onCleared() {
//        super.onCleared()
//        doctorListener?.let { db.child("users").removeEventListener(it) }
//        patientListener?.let { db.child("users").removeEventListener(it) }
//        upcomingListener?.let { db.child("appointments").removeEventListener(it) }
//        visitsListener?.let { db.child("appVisits").removeEventListener(it) }
//        pendingListener?.let { db.child("appointments").removeEventListener(it) }
//    }
//}



import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DashboardStatsViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().reference

    // ── Doctor count (Admin) ──────────────────────────────────────────────────
    private val _doctorCount = mutableStateOf(0)
    val doctorCount: State<Int> = _doctorCount

    // ── Total user count (Admin) ──────────────────────────────────────────────
    private val _totalUserCount = mutableStateOf(0)
    val totalUserCount: State<Int> = _totalUserCount

    // ── Patient stats ─────────────────────────────────────────────────────────
    private val _upcomingAppointments = mutableStateOf(0)
    val upcomingAppointments: State<Int> = _upcomingAppointments

    private val _appVisits = mutableStateOf(0)
    val appVisits: State<Int> = _appVisits

    // Patient prescription count (prescriptions written specifically for this patient)
    private val _prescriptionCount = mutableStateOf(0)
    val prescriptionCount: State<Int> = _prescriptionCount

    // ── Doctor stats ──────────────────────────────────────────────────────────
    private val _pendingAppointments = mutableStateOf(0)
    val pendingAppointments: State<Int> = _pendingAppointments

    // Total booked (confirmed/assigned) appointments for this doctor
    private val _bookedAppointmentCount = mutableStateOf(0)
    val bookedAppointmentCount: State<Int> = _bookedAppointmentCount

    // Total unique patients who have had an appointment with this doctor
    private val _uniquePatientCount = mutableStateOf(0)
    val uniquePatientCount: State<Int> = _uniquePatientCount

    // ── Patient count (Admin) ─────────────────────────────────────────────────
    private val _patientCount = mutableStateOf(0)
    val patientCount: State<Int> = _patientCount

    // ── Listener references for cleanup ──────────────────────────────────────
    private var doctorListener: ValueEventListener? = null
    private var totalUserListener: ValueEventListener? = null
    private var upcomingListener: ValueEventListener? = null
    private var visitsListener: ValueEventListener? = null
    private var prescriptionListener: ValueEventListener? = null
    private var pendingListener: ValueEventListener? = null
    private var bookedListener: ValueEventListener? = null
    private var patientListener: ValueEventListener? = null

    // ─────────────────────────────────────────────────────────────────────────
    // ADMIN
    // ─────────────────────────────────────────────────────────────────────────

    /** Counts all users whose role == "Doctor". */
    fun listenDoctorCount() {
        if (doctorListener != null) return
        doctorListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _doctorCount.value = snapshot.children.count { child ->
                    child.child("role").getValue(String::class.java)
                        .equals("Doctor", ignoreCase = true)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        db.child("users").addValueEventListener(doctorListener!!)
    }

    /** Counts every user in the /users node regardless of role. */
    fun listenTotalUserCount() {
        if (totalUserListener != null) return
        totalUserListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _totalUserCount.value = snapshot.childrenCount.toInt()
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        db.child("users").addValueEventListener(totalUserListener!!)
    }

    /** Counts all users whose role == "Patient". */
    fun listenPatientCount() {
        if (patientListener != null) return
        patientListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _patientCount.value = snapshot.children.count { child ->
                    child.child("role").getValue(String::class.java)
                        .equals("Patient", ignoreCase = true)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        db.child("users").addValueEventListener(patientListener!!)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PATIENT
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Listens to:
     *  - appointments/{patientUid}  → upcoming appointment count
     *  - appVisits/{patientUid}/count → increments + observes visit counter
     *  - prescriptions/{patientUid}  → prescription count for this patient
     */
    fun listenPatientStats(patientUid: String) {
        if (upcomingListener != null) return  // already attached

        // Upcoming appointments
        upcomingListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var count = 0
                for (child in snapshot.children) {
                    val status = child.child("status").getValue(String::class.java) ?: ""
                    if (status.equals("upcoming", ignoreCase = true) ||
                        status.equals("assigned", ignoreCase = true) ||
                        status.equals("pending", ignoreCase = true)
                    ) count++
                }
                _upcomingAppointments.value = count
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        db.child("appointments").child(patientUid)
            .addValueEventListener(upcomingListener!!)

        // App visit counter
        val visitsRef = db.child("appVisits").child(patientUid).child("count")
        visitsRef.get().addOnSuccessListener { snap ->
            val updated = (snap.getValue(Int::class.java) ?: 0) + 1
            visitsRef.setValue(updated)
        }
        visitsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _appVisits.value = snapshot.getValue(Int::class.java) ?: 0
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        visitsRef.addValueEventListener(visitsListener!!)

        // Prescription count for this patient
        // Prescriptions are stored at: prescriptions/{patientId}/{prescriptionId}
        prescriptionListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _prescriptionCount.value = snapshot.childrenCount.toInt()
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        db.child("prescriptions").child(patientUid)
            .addValueEventListener(prescriptionListener!!)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // DOCTOR
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Listens to doctorAppointments/{doctorUid} and computes:
     *  - pendingAppointments  → status == "pending" or "pending_admin"
     *  - bookedAppointmentCount → status == "assigned" or "confirmed" or any non-pending
     *  - uniquePatientCount  → distinct patientId values across all appointments
     */
    fun listenDoctorPendingAppointments(doctorUid: String) {
        if (pendingListener != null) return

        pendingListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var pending = 0
                var booked = 0
                val uniquePatients = mutableSetOf<String>()

                for (appt in snapshot.children) {
                    val status = appt.child("status").getValue(String::class.java) ?: ""
                    val patientId = appt.child("patientId").getValue(String::class.java) ?: ""

                    when {
                        status.equals("pending", ignoreCase = true) ||
                                status.equals("pending_admin", ignoreCase = true) -> pending++

                        status.equals("assigned", ignoreCase = true) ||
                                status.equals("confirmed", ignoreCase = true) ||
                                status.equals("upcoming", ignoreCase = true) ||
                                status.equals("completed", ignoreCase = true) -> {
                            booked++
                            if (patientId.isNotBlank()) uniquePatients.add(patientId)
                        }
                    }
                }
                _pendingAppointments.value = pending
                _bookedAppointmentCount.value = booked
                _uniquePatientCount.value = uniquePatients.size
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        // Use doctorAppointments node which is keyed directly by appointmentId
        db.child("doctorAppointments").child(doctorUid)
            .addValueEventListener(pendingListener!!)
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Cleanup
    // ─────────────────────────────────────────────────────────────────────────

    override fun onCleared() {
        super.onCleared()
        doctorListener?.let  { db.child("users").removeEventListener(it) }
        totalUserListener?.let { db.child("users").removeEventListener(it) }
        patientListener?.let { db.child("users").removeEventListener(it) }
        upcomingListener?.let { db.child("appointments").removeEventListener(it) }
        visitsListener?.let  { db.child("appVisits").removeEventListener(it) }
        prescriptionListener?.let { db.child("prescriptions").removeEventListener(it) }
        pendingListener?.let { db.child("doctorAppointments").removeEventListener(it) }
    }
}