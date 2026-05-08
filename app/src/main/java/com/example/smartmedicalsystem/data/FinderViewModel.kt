//package com.example.smartmedicalsystem.data
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//
//import com.example.smartmedicalsystem.models.FacilityModel
//import com.google.type.LatLng
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//class FinderViewModel : ViewModel() {
//    private val _userLocation = MutableStateFlow(LatLng(-1.286389, 36.817223))
//    val userLocation: StateFlow<LatLng> = _userLocation
//}
//    var userLocation by mutableStateOf<LatLng?>(null)
//        private set
//
//    private val allFacilities = listOf(
//
//        FacilityModel(
//            id = "1",
//            name = "Nairobi Hospital",
//            lat = -1.3000,
//            lng = 36.8080,
//            type = "Hospital",
//            specialty = "General Care"
//        ),
//
//        FacilityModel(
//            id = "2",
//            name = "Aga Khan Hospital",
//            lat = -1.2630,
//            lng = 36.8172,
//            type = "Hospital",
//            specialty = "Cardiology"
//        )
//    )
//
//    var filteredFacilities by mutableStateOf(allFacilities)
//        private set
//
//    fun fetchLocation() {
//
//        userLocation = LatLng(
//            -1.286389,
//            36.817223
//        )
//    }
//
//    fun search(query: String) {
//
//        filteredFacilities = allFacilities.filter {
//
//            it.name.contains(query, ignoreCase = true) ||
//                    it.specialty.contains(query, ignoreCase = true)
//        }
//    }
//}