package com.example.smartmedicalsystem.data


import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * DashboardStatsViewModel
 *
 * Holds live-updating counts fetched from Firebase Realtime Database
 * for all three dashboards:
 *
 *  • Admin  → total doctors in the app
 *  • Patient → upcoming appointments booked + total app visits
 *  • Doctor  → pending appointments assigned to this doctor
 *
 * Each property uses ValueEventListener so the number updates in
 * real-time whenever Firebase data changes — no manual refresh needed.
 */
class DashboardStatsViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().reference

    // ── Admin stats ───────────────────────────────────────────────────────────

    private val _doctorCount = mutableStateOf(0)
    /** Total number of doctors registered in the app. */
    val doctorCount: State<Int> = _doctorCount

    // ── Patient stats ─────────────────────────────────────────────────────────

    private val _upcomingAppointments = mutableStateOf(0)
    /** Number of upcoming appointments booked for the current patient. */
    val upcomingAppointments: State<Int> = _upcomingAppointments

    private val _appVisits = mutableStateOf(0)
    /** Total number of times this patient has opened / visited the app. */
    val appVisits: State<Int> = _appVisits

    // ── Doctor stats ──────────────────────────────────────────────────────────

    private val _pendingAppointments = mutableStateOf(0)
    /** Number of appointments in "pending" status assigned to this doctor. */
    val pendingAppointments: State<Int> = _pendingAppointments

    // ── Listener references (kept so they can be cleaned up) ──────────────────

    private var doctorListener: ValueEventListener? = null
    private var upcomingListener: ValueEventListener? = null
    private var visitsListener: ValueEventListener? = null
    private var pendingListener: ValueEventListener? = null

    // ═════════════════════════════════════════════════════════════════════════
    //  Admin — listen for total doctor count
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Call this once from AdminDashboard's LaunchedEffect.
     * Attaches a real-time listener on the "doctors" node and updates
     * [doctorCount] whenever a doctor is added or removed.
     */
    fun listenDoctorCount() {
        // Avoid attaching duplicate listeners
        if (doctorListener != null) return

        doctorListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _doctorCount.value = snapshot.childrenCount.toInt()
            }
            override fun onCancelled(error: DatabaseError) {
                // Silently fail — count stays at last known value
            }
        }
        db.child("doctors").addValueEventListener(doctorListener!!)
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Patient — listen for upcoming appointments + app visits
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Call this once from PatientDashboard's LaunchedEffect.
     *
     * @param patientUid  Firebase UID of the currently logged-in patient.
     *
     * Listens on:
     *   • "appointments/{uid}" — counts children where status == "upcoming"
     *   • "appVisits/{uid}/count"  — increments by 1 on each call, then observes
     */
    fun listenPatientStats(patientUid: String) {
        if (upcomingListener != null) return  // already attached

        // ── Upcoming appointments ─────────────────────────────────────────────
        upcomingListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var count = 0
                for (child in snapshot.children) {
                    val status = child.child("status").getValue(String::class.java) ?: ""
                    if (status.equals("upcoming", ignoreCase = true)) count++
                }
                _upcomingAppointments.value = count
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        db.child("appointments").child(patientUid)
            .addValueEventListener(upcomingListener!!)

        // ── App visit counter ─────────────────────────────────────────────────
        // Read the current count, increment by 1 (marks this session), then
        // attach a listener so the displayed number stays live.
        val visitsRef = db.child("appVisits").child(patientUid).child("count")
        visitsRef.get().addOnSuccessListener { snap ->
            val current = snap.getValue(Int::class.java) ?: 0
            val updated = current + 1
            visitsRef.setValue(updated)
        }

        visitsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _appVisits.value = snapshot.getValue(Int::class.java) ?: 0
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        visitsRef.addValueEventListener(visitsListener!!)
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Doctor — listen for pending appointments assigned to this doctor
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Call this once from DoctorDashboard's LaunchedEffect.
     *
     * @param doctorUid  Firebase UID of the currently logged-in doctor.
     *
     * Listens on "appointments" (global node) and counts all appointments
     * where:
     *   • doctorId == [doctorUid]  AND
     *   • status   == "pending"
     */
    fun listenDoctorPendingAppointments(doctorUid: String) {
        if (pendingListener != null) return

        pendingListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var count = 0
                // appointments is a flat map: uid → { appointments }
                // iterate all patients' appointment lists
                for (patientNode in snapshot.children) {
                    for (appt in patientNode.children) {
                        val assignedDoctor = appt.child("doctorId").getValue(String::class.java) ?: ""
                        val status = appt.child("status").getValue(String::class.java) ?: ""
                        if (assignedDoctor == doctorUid && status.equals("pending", ignoreCase = true)) {
                            count++
                        }
                    }
                }
                _pendingAppointments.value = count
            }
            override fun onCancelled(error: DatabaseError) {}
        }
        db.child("appointments").addValueEventListener(pendingListener!!)
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Cleanup — remove all listeners when ViewModel is destroyed
    // ═════════════════════════════════════════════════════════════════════════

    override fun onCleared() {
        super.onCleared()
        doctorListener?.let { db.child("doctors").removeEventListener(it) }
        upcomingListener?.let { db.child("appointments").removeEventListener(it) }
        visitsListener?.let { db.child("appVisits").removeEventListener(it) }
        pendingListener?.let { db.child("appointments").removeEventListener(it) }
    }
}