package com.example.smartmedicalsystem.data

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AdminViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    fun addDoctor(
        firstname: String,
        lastname: String,
        email: String,
        password: String,
        specialization: String
    ) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val uid = task.result.user?.uid

                    val doctor = mapOf(
                        "firstname" to firstname,
                        "lastname" to lastname,
                        "email" to email,
                        "specialization" to specialization,
                        "role" to "doctor"
                    )

                    database.child("doctors").child(uid!!).setValue(doctor)
                    database.child("users").child(uid).setValue(doctor)

                } else {
                }
            }
    }
}