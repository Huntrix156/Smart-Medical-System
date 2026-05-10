package com.example.smartmedicalsystem.models.ProfileUiState

import android.net.Uri
import com.example.smartmedicalsystem.models.ProfileModel

data class ProfileUiState(

    val user: ProfileModel? = null,

    val isLoading: Boolean = false,

    val isEditMode: Boolean = false,

    val error: String? = null,

    val imageUri: Uri? = null
)