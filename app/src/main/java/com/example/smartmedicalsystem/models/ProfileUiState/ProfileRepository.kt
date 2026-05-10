package com.example.smartmedicalsystem.models.ProfileUiState

import com.example.smartmedicalsystem.models.ProfileModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class ProfileRepository {

    private val db = FirebaseDatabase.getInstance().reference

    suspend fun getUserProfile(userId: String): ProfileModel? {

        return try {

            val snapshot = db.child("Users")
                .child(userId)
                .get()
                .await()

            snapshot.getValue(ProfileModel::class.java)

        } catch (e: Exception) {

            null
        }
    }

    suspend fun updateProfile(
        userId: String,
        profile: ProfileModel
    ): Boolean {

        return try {

            db.child("Users")
                .child(userId)
                .setValue(profile)
                .await()

            true

        } catch (e: Exception) {

            false
        }
    }
}