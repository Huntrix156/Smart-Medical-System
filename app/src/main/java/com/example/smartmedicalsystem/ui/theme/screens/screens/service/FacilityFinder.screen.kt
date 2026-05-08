package com.example.smartmedicalsystem.ui.theme.screens.screens.service
//
//import android.content.Intent
//import android.net.Uri
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//
//import androidx.compose.foundation.lazy.LazyColumn
//
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.room.util.TableInfo
//import com.example.nexora.ui.theme.screens.component.FacilityItem
//
//import com.example.smartmedicalsystem.data.FinderViewModel
//import com.google.android.gms.maps.internal.IGoogleMapDelegate
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//
//import com.google.maps.android.compose.GoogleMap
//import com.google.maps.android.compose.Marker
//import com.google.maps.android.compose.MarkerState
//import com.google.maps.android.compose.rememberCameraPositionState
//
//@Composable
//fun FacilityFinderScreen(
//    viewModel: FinderViewModel = viewModel()
//) {
//    val context = LocalContext.current
//    val userLocation = viewModel.userLocation
//    val facilities: Int = viewModel.filteredFacilities
//
//    var search by remember { mutableStateOf("") }
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchLocation()
//    }
//
//    TableInfo.Column(modifier = Modifier.fillMaxSize()) {
//
//        // 🔍 Search Bar
//        TextField(
//            value = search,
//            onValueChange = {
//                search = it
//                viewModel.search(it)
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp),
//            placeholder = { Text("Search hospital or specialty") }
//        )
//
//        // 🗺️ Map Section
//        Box(modifier = Modifier.weight(1f)) {
//
//            userLocation?.let { location ->
//
//                val cameraPositionState = rememberCameraPositionState {
//                    position = CameraPosition.fromLatLngZoom(location, 14f)
//                }
//
//                GoogleMap(
//                    modifier = Modifier.fillMaxSize(),
//                    cameraPositionState = cameraPositionState
//                ) {
//
//                    // 📍 User Marker
//                    Marker(
//                        state = MarkerState(position = location),
//                        title = "You are here"
//                    )
//
//                    // 🏥 Facility Markers
//                    facilities.forEach {
//                        Marker(
//                            state = MarkerState(
//                                position = LatLng(it.lat, it.lng)
//                            ),
//                            title = it.name
//                        )
//                    }
//                } as IGoogleMapDelegate
//            }
//        }
//
//        // 📋 Facility List
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//        ) {
//            items(facilities) { facility ->
//                FacilityItem(
//                    facility = facility,
//                    onClick = { selectedFacility ->
//
//                        val uri = Uri.parse(
//                            "google.navigation:q=${selectedFacility.lat},${selectedFacility.lng}"
//                        )
//                        val intent = Intent(Intent.ACTION_VIEW, uri)
//                        intent.setPackage("com.google.android.apps.maps")
//                        context.startActivity(intent)
//                    }
//                )
//            }
//        }
//    }
//}