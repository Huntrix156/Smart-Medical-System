package com.example.smartmedicalsystem.data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.smartmedicalsystem.models.medication.MedicineModel
import com.example.smartmedicalsystem.navigation.ROUTE_ADD_MEDICATION
import com.example.smartmedicalsystem.navigation.ROUTE_PATIENT_DASHBOARD
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


//credintial functions
 class MedicineViewModel: ViewModel(){


    val cloudinaryUrl =
        "https://api.cloudinary.com/v1_1/dfuv2cguf/image/upload"//"https://api.cloudinary.com/v1_1/this come from cloudinary(Cloud name)/image/upload"
    val uploadPreset =
        "image_folder"//this is done  in the cloudinary to the ((upload) in the setting)

    //    capturing patient details//
    fun uploadMedicine(
        imageUri: Uri?,
        name: String,
        dosage: String,
        startDate: String,
        endDate: String,
        frequency: String,
        context: Context,
        navController: NavController
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageUrl = imageUri?.let { uploadToCloudinary(context, it) }
                val ref = FirebaseDatabase.getInstance().getReference("Medicine")
                    .push()//to be saved in the database table Medicine
                val patientData = mapOf(
                    "id" to ref.key,//if successful this try works if not //CATCH work meaning not saved
                    "name" to name,
                    "age" to dosage,
                    "phone" to startDate,
                    "illness" to endDate,
                    "imageUrl" to imageUrl,
                    "gender" to frequency,
                )
                ref.setValue(patientData).await()//Save function//
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Patient saved Successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_PATIENT_DASHBOARD)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Patient not saved", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun uploadToCloudinary(context: Context, uri: Uri): String {
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.readBytes() ?: throw Exception("Image read failed")
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart(
                "file", "image.jpg",
                RequestBody.create("image/*".toMediaTypeOrNull(), fileBytes)
            )
            .addFormDataPart("upload_preset", uploadPreset).build()
        val request = Request.Builder().url(cloudinaryUrl).post(requestBody).build()
        val response = OkHttpClient().newCall(request).execute()
        if (!response.isSuccessful) throw Exception("Upload failed")
        val responseBody = response.body?.string()
        val secureUrl = Regex("\"secure_url\":\"(.*?)\"")
            .find(responseBody ?: "")?.groupValues?.get(1)
        return secureUrl ?: throw Exception("Failed to get image URL")
    }


    // Correct state for a list
    private val _medicines = mutableStateListOf<MedicineModel>()
    val medicines: List<MedicineModel> = _medicines

    fun fetchMedicine(context: Context) {      //CHANGE HERE//

        val ref = FirebaseDatabase.getInstance().getReference("Patients")

        ref.get().addOnSuccessListener { snapshot ->

            _medicines.clear()

            for (child in snapshot.children) {
                val medicine = child.getValue(MedicineModel::class.java)      //CHANGE HERE//

                medicine?.let {       //CHANGE HERE//
                    it.id = child.key ?: ""
                    _medicines.add(it)
                }
            }

        }.addOnFailureListener {
            Toast.makeText(context, "Failed to load medicines", Toast.LENGTH_LONG).show()
        }
    }
    //-----UPDATE MEDICINE FUNCTION-------
    fun updateMedicine(medicineId: String,
                      imageUri: Uri?,
                      name: String,
                      dosage: String,
                      startDate: String,
                      endDate: String,
                      frequency: String,
                       context: Context,
                      navController: NavController){
        viewModelScope.launch ( Dispatchers.IO ){
            try {
                val imageUri = imageUri?.let { uploadToCloudinary(context,it) }
                val updateMedicine = mapOf(
                    "id" to medicineId,
                    "name" to name,
                    "dosage" to dosage,
                    "startDate" to startDate,
                    "endDate" to endDate,
                    "frequency" to frequency,
                    "imageUrl" to imageUri
                )


                val ref = FirebaseDatabase.getInstance()
                    .getReference("Medicines")
                    .child(medicineId) // ✅ FIX: correct update path

                ref.setValue( updateMedicine).await() // ✅ FIX: actually update data

                withContext(Dispatchers.Main){ // ✅ FIX: UI thread
                    Toast.makeText(context,"Medicine updated successfully",Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_ADD_MEDICATION)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"Medicine update failed",
                        Toast.LENGTH_LONG).show()
                }
            }

        }
    }
    //--------Delete Function------------//
    fun deleteMedicine(medicineId: String,context: Context){
        val ref = FirebaseDatabase.getInstance()
            .getReference("Medicines").child(medicineId)
        ref.removeValue().addOnSuccessListener {
            _medicines.removeAll{it.id == medicineId}
        }.addOnFailureListener {
            Toast.makeText(context,"Medicine Delete Failed",
                Toast.LENGTH_LONG).show()
        }
    }
}








//}












