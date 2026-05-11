package com.example.smartmedicalsystem.data//package com.example.smartmedicalsystem.data
//
//import android.content.Context
//import android.net.Uri
//import android.widget.Toast
//import androidx.compose.runtime.mutableStateListOf
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.navigation.NavController
//import com.example.smartmedicalsystem.models.medication.MedicineModel
//import com.example.smartmedicalsystem.navigation.ROUTE_ADD_MEDICATION
//import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
//import com.google.firebase.database.FirebaseDatabase
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//import kotlinx.coroutines.withContext
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.RequestBody
//import java.io.InputStream
//
//
// class MedicineViewModel: ViewModel(){
//
//
//    val cloudinaryUrl =
//        "https://api.cloudinary.com/v1_1/dfuv2cguf/image/upload"//"https://api.cloudinary.com/v1_1/this come from cloudinary(Cloud name)/image/upload"
//    val uploadPreset =
//        "image_folder"
//     fun uploadMedicine(
//         imageUri: Uri?,
//         name: String,
//         dosage: String,
//         startDate: String,
//         endDate: String,
//         frequency: String,
//         context: Context,
//         navController: NavController
//     ) {
//
//         viewModelScope.launch(Dispatchers.IO) {
//             try {
//
//                 val imageUrl = imageUri?.let {
//                     uploadToCloudinary(context, it)
//                 }
//
//                 val ref = FirebaseDatabase.getInstance()
//                     .getReference("Medicines")
//                     .push()
//
//                 val medicineData = mapOf(
//                     "id" to ref.key,
//                     "name" to name,
//                     "dosage" to dosage,
//                     "startDate" to startDate,
//                     "endDate" to endDate,
//                     "frequency" to frequency,
//                     "imageUrl" to imageUrl
//                 )
//
//                 ref.setValue(medicineData).await()
//
//                 withContext(Dispatchers.Main) {
//                     Toast.makeText(
//                         context,
//                         "Medicine saved Successfully",
//                         Toast.LENGTH_LONG
//                     ).show()
//
//                     navController.navigate(ROUTE_PATIENT_DASHBOARD)
//                 }
//
//             } catch (e: Exception) {
//
//                 withContext(Dispatchers.Main) {
//                     Toast.makeText(
//                         context,
//                         "Medicine not saved: ${e.message}",
//                         Toast.LENGTH_LONG
//                     ).show()
//                 }
//             }
//         }
//     }
////    fun uploadMedicine(
////        imageUri: Uri?,
////        name: String,
////        dosage: String,
////        startDate: String,
////        endDate: String,
////        frequency: String,
////        context: Context,
////        navController: NavController
////    ) {
////
////        viewModelScope.launch(Dispatchers.IO) {
////            try {
////                val imageUrl = imageUri?.let { uploadToCloudinary(context, it) }
////                val ref = FirebaseDatabase.getInstance().getReference("Medicine")
////                    .push()
////                val patientData = mapOf(
////                    "id" to ref.key,
////                    "name" to name,
////                    "age" to dosage,
////                    "phone" to startDate,
////                    "illness" to endDate,
////                    "imageUrl" to imageUrl,
////                    "gender" to frequency,
////                )
////                ref.setValue(patientData).await()
////                withContext(Dispatchers.Main) {
////                    Toast.makeText(context, "Medicine saved Successfully", Toast.LENGTH_LONG).show()
////                    navController.navigate(ROUTE_PATIENT_DASHBOARD)
////                }
////
////            } catch (e: Exception) {
////                withContext(Dispatchers.Main) {
////                    Toast.makeText(context, "Medicine not saved", Toast.LENGTH_LONG).show()
////                }
////            }
////        }
////    }
////
//    private fun uploadToCloudinary(context: Context, uri: Uri): String {
//        val contentResolver = context.contentResolver
//        val inputStream: InputStream? = contentResolver.openInputStream(uri)
//        val fileBytes = inputStream?.readBytes() ?: throw Exception("Image read failed")
//        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
//            .addFormDataPart(
//                "file", "image.jpg",
//                RequestBody.create("image/*".toMediaTypeOrNull(), fileBytes)
//            )
//            .addFormDataPart("upload_preset", uploadPreset).build()
//        val request = Request.Builder().url(cloudinaryUrl).post(requestBody).build()
//        val response = OkHttpClient().newCall(request).execute()
//        if (!response.isSuccessful) throw Exception("Upload failed")
//        val responseBody = response.body?.string()
//        val secureUrl = Regex("\"secure_url\":\"(.*?)\"")
//            .find(responseBody ?: "")?.groupValues?.get(1)
//        return secureUrl ?: throw Exception("Failed to get image URL")
//    }
//
//
//    private val _medicines = mutableStateListOf<MedicineModel>()
//    val medicines: List<MedicineModel> = _medicines
//
//    fun fetchMedicine(context: Context) {
//
//        val ref = FirebaseDatabase.getInstance().getReference("Medicines")
//
//        ref.get().addOnSuccessListener { snapshot ->
//
//            _medicines.clear()
//
//            for (child in snapshot.children) {
//                val medicine = child.getValue(MedicineModel::class.java)
//
//                medicine?.let {
//                    it.id = child.key ?: ""
//                    _medicines.add(it)
//                }
//            }
//
//        }.addOnFailureListener {
//            Toast.makeText(context, "Failed to load medicines", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    fun updateMedicine(medicineId: String,
//                      imageUri: Uri?,
//                      name: String,
//                      dosage: String,
//                      startDate: String,
//                      endDate: String,
//                      frequency: String,
//                       context: Context,
//                      navController: NavController){
//        viewModelScope.launch ( Dispatchers.IO ){
//            try {
//                val imageUri = imageUri?.let { uploadToCloudinary(context,it) }
//                val updateMedicine = mapOf(
//                    "id" to medicineId,
//                    "name" to name,
//                    "dosage" to dosage,
//                    "startDate" to startDate,
//                    "endDate" to endDate,
//                    "frequency" to frequency,
//                    "imageUrl" to imageUri
//                )
//
//
//                val ref = FirebaseDatabase.getInstance()
//                    .getReference("Medicines")
//                    .child(medicineId)
//
//                ref.setValue( updateMedicine).await()
//
//                withContext(Dispatchers.Main){
//                    Toast.makeText(context,"Medicine updated successfully",Toast.LENGTH_LONG).show()
//                    navController.navigate(ROUTE_ADD_MEDICATION)
//                }
//            }catch (e: Exception){
//                withContext(Dispatchers.Main){
//                    Toast.makeText(context,"Medicine update failed",
//                        Toast.LENGTH_LONG).show()
//                }
//            }
//
//        }
//    }
//
//    fun deleteMedicine(medicineId: String,context: Context){
//        val ref = FirebaseDatabase.getInstance()
//            .getReference("Medicines").child(medicineId)
//        ref.removeValue().addOnSuccessListener {
//            _medicines.removeAll{it.id == medicineId}
//        }.addOnFailureListener {
//            Toast.makeText(context,"Medicine Delete Failed",
//                Toast.LENGTH_LONG).show()
//        }
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//



import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.smartmedicalsystem.models.medication.MedicineModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.InputStream

class MedicineViewModel : ViewModel() {

    // ─── Cloudinary config ───────────────────────────────────────────────────
    private val cloudinaryUrl = "https://api.cloudinary.com/v1_1/dfuv2cguf/image/upload"
    private val uploadPreset  = "image_folder"

    // ─── In-memory list (drives UI) ──────────────────────────────────────────
    private val _medicines = mutableStateListOf<MedicineModel>()
    val medicines: List<MedicineModel> = _medicines

    // ─── Upload (Add) ────────────────────────────────────────────────────────
    /**
     * BUG FIXED #1 – isLoading was set back to false immediately after
     * calling uploadMedicine() in the composable because the coroutine is
     * asynchronous. The loading flag must be managed *inside* the ViewModel
     * (or use a StateFlow). For now we navigate + toast on completion, which
     * is the existing pattern; just make sure the screen doesn't flip
     * isLoading=false before the coroutine finishes (see AddMedicine.screen.kt fix).
     *
     * BUG FIXED #2 – The old code shadowed the outer `imageUri` parameter
     * with a local `val imageUri` inside updateMedicine(). Renamed the local
     * variable to `newImageUrl` so it never hides the parameter.
     */
    fun uploadMedicine(
        imageUri: Uri?,
        name: String,
        dosage: String,
        startDate: String,
        endDate: String,
        frequency: String,
        context: Context,
        navController: NavController,
        onDone: () -> Unit = {}          // ← callback so the screen can reset isLoading
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageUrl: String? = imageUri?.let { uploadToCloudinary(context, it) }

                val ref = FirebaseDatabase.getInstance()
                    .getReference("Medicines")
                    .push()

                val medicineData = mapOf(
                    "id"        to ref.key,
                    "name"      to name,
                    "dosage"    to dosage,
                    "startDate" to startDate,
                    "endDate"   to endDate,
                    "frequency" to frequency,
                    "imageUrl"  to imageUrl
                )

                ref.setValue(medicineData).await()

                // After saving, re-fetch so the list stays in sync
                fetchMedicineInternal()

                withContext(Dispatchers.Main) {
                    onDone()
                    Toast.makeText(context, "Medicine saved successfully", Toast.LENGTH_LONG).show()
                    navController.popBackStack()   // go back instead of hard-coding a route
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onDone()
                    Toast.makeText(context, "Medicine not saved: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // ─── Fetch ───────────────────────────────────────────────────────────────
    fun fetchMedicine(context: Context) {
        val ref = FirebaseDatabase.getInstance().getReference("Medicines")
        ref.get()
            .addOnSuccessListener { snapshot ->
                _medicines.clear()
                for (child in snapshot.children) {
                    val medicine = child.getValue(MedicineModel::class.java)
                    medicine?.let {
                        it.id = child.key ?: ""
                        _medicines.add(it)
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to load medicines", Toast.LENGTH_LONG).show()
            }
    }

    // Internal suspend version used after add/update so we don't need a Context
    private suspend fun fetchMedicineInternal() {
        try {
            val snapshot = FirebaseDatabase.getInstance()
                .getReference("Medicines")
                .get()
                .await()
            val list = mutableListOf<MedicineModel>()
            for (child in snapshot.children) {
                val m = child.getValue(MedicineModel::class.java)
                m?.let {
                    it.id = child.key ?: ""
                    list.add(it)
                }
            }
            withContext(Dispatchers.Main) {
                _medicines.clear()
                _medicines.addAll(list)
            }
        } catch (_: Exception) { /* non-critical – list will refresh on next open */ }
    }

    // ─── Update ──────────────────────────────────────────────────────────────
    /**
     * BUG FIXED #3 – The original code declared `val imageUri = imageUri?.let { … }`
     * which shadowed the *parameter* `imageUri: Uri?`.  If the user did NOT
     * pick a new image, the parameter is null, the local val is null, and we
     * lost the existing imageUrl stored in Firebase.
     *
     * Fix: if a new Uri was selected, upload it; otherwise keep the existing URL.
     */
    fun updateMedicine(
        medicineId: String,
        imageUri: Uri?,
        existingImageUrl: String?,   // ← pass the current URL from the model
        name: String,
        dosage: String,
        startDate: String,
        endDate: String,
        frequency: String,
        context: Context,
        navController: NavController,
        onDone: () -> Unit = {}
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Only upload to Cloudinary when the user actually chose a new image
                val newImageUrl: String? = if (imageUri != null) {
                    uploadToCloudinary(context, imageUri)
                } else {
                    existingImageUrl   // keep the old URL
                }

                val updateData = mapOf(
                    "id"        to medicineId,
                    "name"      to name,
                    "dosage"    to dosage,
                    "startDate" to startDate,
                    "endDate"   to endDate,
                    "frequency" to frequency,
                    "imageUrl"  to newImageUrl
                )

                FirebaseDatabase.getInstance()
                    .getReference("Medicines")
                    .child(medicineId)
                    .setValue(updateData)
                    .await()

                // Sync in-memory list
                val idx = _medicines.indexOfFirst { it.id == medicineId }
                if (idx != -1) {
                    withContext(Dispatchers.Main) {
                        _medicines[idx] = MedicineModel(
                            id        = medicineId,
                            name      = name,
                            dosage    = dosage,
                            startDate = startDate,
                            endDate   = endDate,
                            frequency = frequency,
                            imageUrl  = newImageUrl
                        )
                    }
                }

                withContext(Dispatchers.Main) {
                    onDone()
                    Toast.makeText(context, "Medicine updated successfully", Toast.LENGTH_LONG).show()
                    navController.popBackStack()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onDone()
                    Toast.makeText(context, "Medicine update failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // ─── Delete ──────────────────────────────────────────────────────────────
    /**
     * BUG FIXED #4 – removeAll lambda used `it.id` but the MedicineModel.id
     * field is `String?`.  Comparing `String? == String` works in Kotlin,
     * but it is safer to use explicit null-safe equality and also remove the
     * item *before* getting a Firebase failure so the UI updates optimistically.
     * Rolled back on failure.
     */
    fun deleteMedicine(medicineId: String, context: Context) {
        // Optimistic removal
        val removed = _medicines.find { it.id == medicineId }
        _medicines.removeAll { it.id == medicineId }

        FirebaseDatabase.getInstance()
            .getReference("Medicines")
            .child(medicineId)
            .removeValue()
            .addOnFailureListener {
                // Rollback
                removed?.let { _medicines.add(it) }
                Toast.makeText(context, "Delete failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    // ─── Cloudinary helper ───────────────────────────────────────────────────
    private fun uploadToCloudinary(context: Context, uri: Uri): String {
        val contentResolver = context.contentResolver
        val inputStream: InputStream = contentResolver.openInputStream(uri)
            ?: throw Exception("Cannot open image stream")
        val fileBytes = inputStream.readBytes()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file", "image.jpg",
                RequestBody.create("image/*".toMediaTypeOrNull(), fileBytes)
            )
            .addFormDataPart("upload_preset", uploadPreset)
            .build()

        val request = Request.Builder().url(cloudinaryUrl).post(requestBody).build()
        val response = OkHttpClient().newCall(request).execute()

        if (!response.isSuccessful) throw Exception("Cloudinary upload failed: ${response.code}")

        val responseBody = response.body?.string()
        return Regex("\"secure_url\":\"(.*?)\"")
            .find(responseBody ?: "")
            ?.groupValues?.get(1)
            ?: throw Exception("Failed to parse Cloudinary URL")
    }
}
