package com.example.smartmedicalsystem.data



import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.smartmedicalsystem.models.ProfileModel
import com.example.smartmedicalsystem.navigation.ROUTE_MAIN_DASHBOARD
import com.example.smartmedicalsystem.navigation.ROUTE_PROFILE
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
class ProfileViewModel: ViewModel(){


    val cloudinaryUrl =
        "https://api.cloudinary.com/v1_1/dfuv2cguf/image/upload"//"https://api.cloudinary.com/v1_1/this come from cloudinary(Cloud name)/image/upload"
    val uploadPreset =
        "image_folder"//this is done  in the cloudinary to the ((upload) in the setting)

    //    capturing user details//
    fun uploadUser(    //----CHANGE HERE----//
        imageUri: Uri?,
        firstname: String,
        lastname: String,
        username: String,
        email: String,
//        phonenumber: String,
        context: Context,
        navController: NavController
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageUrl = imageUri?.let { uploadToCloudinary(context, it) }
                val ref = FirebaseDatabase.getInstance().getReference("User")
                    .push()//to be saved in the database table Medicine
                val userData = mapOf(
                    "id" to ref.key,//if successful this try works if not //CATCH work meaning not saved
                    "firstname" to firstname,
                    "lastname" to lastname,
                    "username" to username,
                    "email" to email,
                    "imageUrl" to imageUrl,
//                    "phonenumber" to phonenumber,
                )
                ref.setValue(userData).await()//Save function//
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "User saved Successfully", Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_MAIN_DASHBOARD)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "User not saved", Toast.LENGTH_LONG).show()
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
    private val _users = mutableStateListOf<ProfileModel>()
    val users: List<ProfileModel> = _users

    fun fetchUser(context: Context) {    //-------CHANGE HERE-----//

        val ref = FirebaseDatabase.getInstance().getReference("Users")

        ref.get().addOnSuccessListener { snapshot ->

            _users.clear()

            for (child in snapshot.children) {
                val user = child.getValue(ProfileModel::class.java)

                user?.let {
                    it.id = child.key ?: ""
                    _users.add(it)
                }
            }

        }.addOnFailureListener {
            Toast.makeText(context, "Failed to load user", Toast.LENGTH_LONG).show()
        }
    }


    //-----UPDATE USER FUNCTION-------//
    fun updateUser(userId: String,
                     imageUri: Uri?,
                     firstname: String,
                     lastname: String,
                     username: String,
                     email: String,
                     gender: String,
                     context: Context,
                     navController: NavController){
        viewModelScope.launch ( Dispatchers.IO ){
            try {
                val imageUri = imageUri?.let { uploadToCloudinary(context,it) }
                val updateDoctor = mapOf(
                    "id" to userId,
                    "firstname" to firstname,
                    "lastname" to lastname,
                    "username" to username,
                    "email" to email,
                    "gender" to gender,
                    "imageUrl" to imageUri
                )


                val ref = FirebaseDatabase.getInstance()
                    .getReference("Users")
                    .child(userId) // ✅ FIX: correct update path

                ref.setValue( updateDoctor).await() // ✅ FIX: actually update data

                withContext(Dispatchers.Main){ // ✅ FIX: UI thread
                    Toast.makeText(context,"User updated successfully",Toast.LENGTH_LONG).show()
                    navController.navigate(ROUTE_PROFILE)
                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,"User update failed",
                        Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    //--------Delete Function------------//
    fun deleteUser(userId: String,context: Context){
        val ref = FirebaseDatabase.getInstance()
            .getReference("Users").child(userId)
        ref.removeValue().addOnSuccessListener {
            _users.removeAll{it.id == userId}
        }.addOnFailureListener {
            Toast.makeText(context,"User Delete Failed",
                Toast.LENGTH_LONG).show()
        }
    }


}













//)
