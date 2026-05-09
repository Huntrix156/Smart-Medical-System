package com.example.smartmedicalsystem.data


import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class DashboardStatsViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().reference


    private val _doctorCount = mutableStateOf(0)
    val doctorCount: State<Int> = _doctorCount


    private val _upcomingAppointments = mutableStateOf(0)
    val upcomingAppointments: State<Int> = _upcomingAppointments

    private val _appVisits = mutableStateOf(0)
    val appVisits: State<Int> = _appVisits


    private val _pendingAppointments = mutableStateOf(0)
    val pendingAppointments: State<Int> = _pendingAppointments


    private var doctorListener: ValueEventListener? = null
    private var upcomingListener: ValueEventListener? = null
    private var visitsListener: ValueEventListener? = null
    private var pendingListener: ValueEventListener? = null




    fun listenDoctorCount() {
        if (doctorListener != null) return

        doctorListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val count = snapshot.children.count { child ->
                    child.child("role")
                        .getValue(String::class.java)
                        .equals("Doctor", ignoreCase = true)
                }
                _doctorCount.value = count
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        db.child("users").addValueEventListener(doctorListener!!)
    }




    fun listenPatientStats(patientUid: String) {
        if (upcomingListener != null) return  // already attached

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


    fun listenDoctorPendingAppointments(doctorUid: String) {
        if (pendingListener != null) return

        pendingListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var count = 0


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



    override fun onCleared() {
        super.onCleared()
        doctorListener?.let { db.child("users").removeEventListener(it) }
        upcomingListener?.let { db.child("appointments").removeEventListener(it) }
        visitsListener?.let { db.child("appVisits").removeEventListener(it) }
        pendingListener?.let { db.child("appointments").removeEventListener(it) }
    }
}