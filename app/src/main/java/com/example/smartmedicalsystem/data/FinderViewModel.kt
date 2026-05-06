package com.example.smartmedicalsystem.data
//
//import android.Manifest
//import android.app.Application
//import android.health.connect.datatypes.ExerciseRoute
//import android.location.Location
//import androidx.annotation.RequiresPermission
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.AndroidViewModel
//import com.example.smartmedicalsystem.models.FacilityModel
//import com.google.android.gms.location.LocationServices
//import com.google.type.LatLng
//
//class FinderViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val fusedLocationClient =
//        LocationServices.getFusedLocationProviderClient(application)
//
//    var userLocation by mutableStateOf<LatLng?>(null)
//        private set
//
//    var facilities by mutableStateOf(listOf<FacilityModel>())
//        private set
//
//    var filteredFacilities by mutableStateOf(listOf<FacilityModel>())
//        private set
//
//    init {
//        loadDummyFacilities()
//    }
//
//    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
//    fun fetchLocation() {
//        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//            location?.let {
//                val latLng = LatLng(it.latitude, it.longitude)
//                userLocation = latLng
//                filterNearby()
//            }
//        }
//    }
//
//    private fun loadDummyFacilities() {
//        facilities = listOf(
//            FacilityModel("1", "City Hospital", -1.2921, 36.8219, "Hospital", "General"),
//            FacilityModel("2", "Care Clinic", -1.2950, 36.8200, "Clinic", "Dental"),
//            FacilityModel("3", "Med Pharmacy", -1.2900, 36.8250, "Pharmacy", "Drugs")
//        )
//        filteredFacilities = facilities
//    }
//
//    private fun distance(a: LatLng, b: LatLng): Float {
//        val results = FloatArray(1)
//        Location.distanceBetween(
//            a.latitude, a.longitude,
//            b.latitude, b.longitude,
//            results
//        )
//        return results[0]
//    }
//
//    fun filterNearby(radiusMeters: Float = 5000f) {
//        val userLoc = userLocation ?: return
//        filteredFacilities = facilities.filter {
//            distance(userLoc, LatLng(it.lat, it.lng)) <= radiusMeters
//        }
//    }
//
//    fun search(query: String) {
//        filteredFacilities = facilities.filter {
//            it.name.contains(query, true) ||
//                    it.specialty.contains(query, true)
//        }
//    }
//}