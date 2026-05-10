package com.example.smartmedicalsystem.ui.theme.screens.Appointments//package com.example.smartmedicalsystem.ui.theme.screens.Appointments//package com.example.smartmedicalsystem.ui.theme.screens.Appointments
////
////import android.widget.Toast
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.rememberScrollState
////import androidx.compose.foundation.verticalScroll
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.ArrowBack
////import androidx.compose.material.icons.filled.CalendarToday
////import androidx.compose.material.icons.filled.MedicalServices
////import androidx.compose.material.icons.filled.Notes
////import androidx.compose.material.icons.filled.Schedule
////import androidx.compose.material3.*
////import androidx.compose.runtime.*
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.platform.LocalContext
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.tooling.preview.Preview
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.unit.sp
////import androidx.lifecycle.viewmodel.compose.viewModel
////import androidx.navigation.NavController
////import androidx.navigation.compose.rememberNavController
////import com.example.smartmedicalsystem.data.AppointmentViewModel
////import com.google.firebase.auth.FirebaseAuth
////
////@OptIn(ExperimentalMaterial3Api::class)
////@Composable
////fun UpcomingAppointmentsScreen(
////    navController: NavController,
////    appointmentViewModel: AppointmentViewModel = viewModel()
////) {
////    val context = LocalContext.current
////
////    var appointmentDate by remember { mutableStateOf("") }
////    var appointmentTime by remember { mutableStateOf("") }
////    var reason by remember { mutableStateOf("") }
////    var specialization by remember { mutableStateOf("") }
////
////    // Specialization dropdown
////    val specializationOptions = listOf(
////        "General Practice",
////        "Cardiology",
////        "Dermatology",
////        "Neurology",
////        "Orthopedics",
////        "Pediatrics",
////        "Psychiatry",
////        "Gynecology",
////        "Ophthalmology",
////        "ENT"
////    )
////    var specExpanded by remember { mutableStateOf(false) }
////
////    // ViewModel state
////    val isLoading by appointmentViewModel.isLoading
////    val successMessage by appointmentViewModel.successMessage
////    val errorMessage by appointmentViewModel.errorMessage
////
////    // Handle feedback toasts
////    LaunchedEffect(successMessage) {
////        successMessage?.let {
////            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
////            appointmentViewModel.clearMessages()
////            navController.popBackStack()
////        }
////    }
////    LaunchedEffect(errorMessage) {
////        errorMessage?.let {
////            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
////            appointmentViewModel.clearMessages()
////        }
////    }
////
////    Scaffold(
////        topBar = {
////            TopAppBar(
////                title = { Text("Book Appointment") },
////                navigationIcon = {
////                    IconButton(onClick = { navController.popBackStack() }) {
////                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
////                    }
////                },
////                colors = TopAppBarDefaults.topAppBarColors(
////                    containerColor = Color(0xFF00604E),
////                    titleContentColor = Color.White
////                )
////            )
////        }
////    ) { innerPadding ->
////
////        Column(
////            modifier = Modifier
////                .fillMaxSize()
////                .padding(innerPadding)
////                .verticalScroll(rememberScrollState())
////                .padding(horizontal = 20.dp, vertical = 16.dp),
////            verticalArrangement = Arrangement.spacedBy(14.dp)
////        ) {
////
////            Text(
////                text = "New Appointment",
////                fontSize = 20.sp,
////                fontWeight = FontWeight.Bold,
////                color = Color(0xFF00604E)
////            )
////            Text(
////                text = "Fill in the details below. The admin will review and assign you a doctor.",
////                fontSize = 13.sp,
////                color = Color.Gray
////            )
////
////            HorizontalDivider()
////
////            OutlinedTextField(
////                value = appointmentDate,
////                onValueChange = { appointmentDate = it },
////                label = { Text("Preferred Date (e.g. 15/05/2026)") },
////                leadingIcon = {
////                    Icon(Icons.Default.CalendarToday, contentDescription = null)
////                },
////                modifier = Modifier.fillMaxWidth(),
////                singleLine = true
////            )
////
////            OutlinedTextField(
////                value = appointmentTime,
////                onValueChange = { appointmentTime = it },
////                label = { Text("Preferred Time (e.g. 10:30 AM)") },
////                leadingIcon = {
////                    Icon(Icons.Default.Schedule, contentDescription = null)
////                },
////                modifier = Modifier.fillMaxWidth(),
////                singleLine = true
////            )
////
////            ExposedDropdownMenuBox(
////                expanded = specExpanded,
////                onExpandedChange = { specExpanded = !specExpanded }
////            ) {
////                OutlinedTextField(
////                    value = specialization,
////                    onValueChange = {},
////                    readOnly = true,
////                    label = { Text("Specialization Required") },
////                    leadingIcon = {
////                        Icon(Icons.Default.MedicalServices, contentDescription = null)
////                    },
////                    trailingIcon = {
////                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = specExpanded)
////                    },
////                    modifier = Modifier
////                        .menuAnchor()
////                        .fillMaxWidth()
////                )
////                ExposedDropdownMenu(
////                    expanded = specExpanded,
////                    onDismissRequest = { specExpanded = false }
////                ) {
////                    specializationOptions.forEach { option ->
////                        DropdownMenuItem(
////                            text = { Text(option) },
////                            onClick = {
////                                specialization = option
////                                specExpanded = false
////                            }
////                        )
////                    }
////                }
////            }
////
////            OutlinedTextField(
////                value = reason,
////                onValueChange = { reason = it },
////                label = { Text("Reason for Appointment") },
////                placeholder = { Text("Describe your symptoms or concern…") },
////                leadingIcon = {
////                    Icon(Icons.Default.Notes, contentDescription = null)
////                },
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .heightIn(min = 120.dp),
////                minLines = 4,
////                maxLines = 6
////            )
////
////            Spacer(modifier = Modifier.height(4.dp))
////
////            Card(
////                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1)),
////                modifier = Modifier.fillMaxWidth()
////            ) {
////                Column(modifier = Modifier.padding(12.dp)) {
////                    Text(
////                        "ℹ️ How it works",
////                        fontWeight = FontWeight.SemiBold,
////                        fontSize = 13.sp,
////                        color = Color(0xFF00604E)
////                    )
////                    Spacer(modifier = Modifier.height(4.dp))
////                    Text(
////                        "1. You submit this request.\n" +
////                                "2. Admin reviews and assigns a doctor based on specialization.\n" +
////                                "3. The doctor confirms or requests a referral.\n" +
////                                "4. You receive a notification about your appointment.",
////                        fontSize = 12.sp,
////                        color = Color(0xFF004D40),
////                        lineHeight = 18.sp
////                    )
////                }
////            }
////
////            Button(
////                onClick = {
////                    val user = FirebaseAuth.getInstance().currentUser
////                    if (user == null) {
////                        Toast.makeText(context, "You must be logged in.", Toast.LENGTH_SHORT).show()
////                        return@Button
////                    }
////                    when {
////                        appointmentDate.isBlank() ->
////                            Toast.makeText(context, "Please enter a preferred date.", Toast.LENGTH_SHORT).show()
////                        appointmentTime.isBlank() ->
////                            Toast.makeText(context, "Please enter a preferred time.", Toast.LENGTH_SHORT).show()
////                        specialization.isBlank() ->
////                            Toast.makeText(context, "Please select a specialization.", Toast.LENGTH_SHORT).show()
////                        reason.isBlank() ->
////                            Toast.makeText(context, "Please describe the reason for this appointment.", Toast.LENGTH_SHORT).show()
////                        else -> {
////                            val patientName = user.displayName
////                                ?: user.email?.substringBefore("@")
////                                ?: "Patient"
////
////                            appointmentViewModel.bookAppointment(
////                                patientId = user.uid,
////                                patientName = patientName,
////                                reason = reason.trim(),
////                                date = appointmentDate.trim(),
////                                time = appointmentTime.trim(),
////                                specialization = specialization,
////                                onSuccess = { /* handled by LaunchedEffect */ },
////                                onFailure = { /* handled by LaunchedEffect */ }
////                            )
////                        }
////                    }
////                },
////                enabled = !isLoading,
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .height(52.dp),
////                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00604E))
////            ) {
////                if (isLoading) {
////                    CircularProgressIndicator(
////                        color = Color.White,
////                        strokeWidth = 2.dp,
////                        modifier = Modifier.size(22.dp)
////                    )
////                } else {
////                    Text("Book Appointment", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
////                }
////            }
////        }
////    }
////}
////
////@Preview(showBackground = true, showSystemUi = true)
////@Composable
////fun UpcomingAppointmentsScreenPreview() {
////    UpcomingAppointmentsScreen(navController = rememberNavController())
////}
//
//
//import android.widget.Toast
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.example.smartmedicalsystem.data.AppointmentViewModel
//import com.example.smartmedicalsystem.models.Appointment
//import com.example.smartmedicalsystem.models.DoctorProfile
//import com.google.firebase.auth.FirebaseAuth
//
//private val GREEN = Color(0xFF00604E)
//
//// ─────────────────────────────────────────────────────────────────────────────
//// Main screen: two tabs — Book Appointment / My Appointments
//// ─────────────────────────────────────────────────────────────────────────────
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun UpcomingAppointmentsScreen(
//    navController: NavController,
//    appointmentViewModel: AppointmentViewModel = viewModel()
//) {
//    val context = LocalContext.current
//    val currentUser = FirebaseAuth.getInstance().currentUser
//    val patientId   = currentUser?.uid ?: ""
//
//    // Start real-time listener for this patient's appointments
//    LaunchedEffect(patientId) {
//        if (patientId.isNotBlank()) {
//            appointmentViewModel.listenPatientAppointments(patientId)
//        }
//    }
//
//    val appointments   by appointmentViewModel.patientAppointments
//    val successMessage by appointmentViewModel.successMessage
//    val errorMessage   by appointmentViewModel.errorMessage
//
//    LaunchedEffect(successMessage) {
//        successMessage?.let {
//            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
//            appointmentViewModel.clearMessages()
//        }
//    }
//    LaunchedEffect(errorMessage) {
//        errorMessage?.let {
//            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
//            appointmentViewModel.clearMessages()
//        }
//    }
//
//    var selectedTab by remember { mutableStateOf(0) }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Appointments") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor    = GREEN,
//                    titleContentColor = Color.White
//                )
//            )
//        }
//    ) { innerPadding ->
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//        ) {
//            // Tab row
//            TabRow(
//                selectedTabIndex = selectedTab,
//                containerColor   = GREEN,
//                contentColor     = Color.White
//            ) {
//                Tab(
//                    selected = selectedTab == 0,
//                    onClick  = { selectedTab = 0 },
//                    text     = { Text("Book Appointment") }
//                )
//                Tab(
//                    selected = selectedTab == 1,
//                    onClick  = { selectedTab = 1 },
//                    text     = {
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text("My Appointments")
//                            if (appointments.isNotEmpty()) {
//                                Spacer(modifier = Modifier.width(4.dp))
//                                Badge { Text("${appointments.size}") }
//                            }
//                        }
//                    }
//                )
//            }
//
//            when (selectedTab) {
//                0 -> BookAppointmentTab(
//                    appointmentViewModel = appointmentViewModel,
//                    patientId   = patientId,
//                    patientName = currentUser?.displayName
//                        ?: currentUser?.email?.substringBefore("@")
//                        ?: "Patient",
//                    onBooked = { selectedTab = 1 }
//                )
//                1 -> MyAppointmentsTab(appointments = appointments)
//            }
//        }
//    }
//}
//
//// ─────────────────────────────────────────────────────────────────────────────
//// Tab 0: Three-step booking — Specialization → Doctor → Details
//// ─────────────────────────────────────────────────────────────────────────────
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun BookAppointmentTab(
//    appointmentViewModel: AppointmentViewModel,
//    patientId: String,
//    patientName: String,
//    onBooked: () -> Unit
//) {
//    val context   = LocalContext.current
//    val isLoading by appointmentViewModel.isLoading
//    val doctorList by appointmentViewModel.doctorList
//
//    // Step tracker: 0 = pick specialization, 1 = pick doctor, 2 = fill details
//    var step               by remember { mutableStateOf(0) }
//    var specialization     by remember { mutableStateOf("") }
//    var selectedDoctor     by remember { mutableStateOf<DoctorProfile?>(null) }
//    var appointmentDate    by remember { mutableStateOf("") }
//    var appointmentTime    by remember { mutableStateOf("") }
//    var reason             by remember { mutableStateOf("") }
//
//    val specializationOptions = listOf(
//        "General Practice", "Cardiology", "Dermatology", "Neurology",
//        "Orthopedics", "Pediatrics", "Psychiatry", "Gynecology",
//        "Ophthalmology", "ENT"
//    )
//
//    // Observe booking success to jump to My Appointments tab
//    val successMessage by appointmentViewModel.successMessage
//    LaunchedEffect(successMessage) {
//        if (successMessage != null) onBooked()
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .padding(horizontal = 20.dp, vertical = 16.dp),
//        verticalArrangement = Arrangement.spacedBy(14.dp)
//    ) {
//        // Step indicator
//        StepIndicator(currentStep = step)
//
//        when (step) {
//
//            // ── Step 1: Choose specialization ─────────────────────────────
//            0 -> {
//                Text("Select Specialization", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GREEN)
//                Text(
//                    "Choose the department you want to visit.",
//                    fontSize = 13.sp, color = Color.Gray
//                )
//                HorizontalDivider()
//
//                specializationOptions.forEach { option ->
//                    Card(
//                        onClick = {
//                            specialization = option
//                            // Load doctors for this specialization
//                            appointmentViewModel.reloadDoctors(specialization = option)
//                            step = 1
//                        },
//                        modifier = Modifier.fillMaxWidth(),
//                        colors = CardDefaults.cardColors(
//                            containerColor = if (specialization == option)
//                                Color(0xFFE0F2F1) else MaterialTheme.colorScheme.surface
//                        ),
//                        elevation = CardDefaults.cardElevation(2.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(14.dp),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Row(verticalAlignment = Alignment.CenterVertically) {
//                                Icon(
//                                    Icons.Default.MedicalServices,
//                                    contentDescription = null,
//                                    tint = GREEN,
//                                    modifier = Modifier.size(20.dp)
//                                )
//                                Spacer(modifier = Modifier.width(12.dp))
//                                Text(option, fontWeight = FontWeight.Medium, fontSize = 15.sp)
//                            }
//                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
//                        }
//                    }
//                }
//            }
//
//            1 -> {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    IconButton(onClick = { step = 0 }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = GREEN)
//                    }
//                    Text(
//                        "Doctors in $specialization",
//                        fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GREEN
//                    )
//                }
//                Text(
//                    "Tap a doctor to proceed with booking.",
//                    fontSize = 13.sp, color = Color.Gray
//                )
//                HorizontalDivider()
//
//                if (doctorList.isEmpty()) {
//                    Box(
//                        modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            CircularProgressIndicator(color = GREEN, modifier = Modifier.size(32.dp))
//                            Spacer(modifier = Modifier.height(12.dp))
//                            Text("Loading doctors…", color = Color.Gray, fontSize = 13.sp)
//                        }
//                    }
//                } else {
//                    doctorList.forEach { doctor ->
//                        Card(
//                            onClick = {
//                                selectedDoctor = doctor
//                                step = 2
//                            },
//                            modifier = Modifier.fillMaxWidth(),
//                            elevation = CardDefaults.cardElevation(2.dp)
//                        ) {
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(14.dp),
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//                                Row(verticalAlignment = Alignment.CenterVertically) {
//                                    Surface(
//                                        shape = MaterialTheme.shapes.extraSmall,
//                                        color = Color(0xFFE0F2F1),
//                                        modifier = Modifier.size(40.dp)
//                                    ) {
//                                        Box(contentAlignment = Alignment.Center) {
//                                            Icon(
//                                                Icons.Default.Person,
//                                                contentDescription = null,
//                                                tint = GREEN,
//                                                modifier = Modifier.size(24.dp)
//                                            )
//                                        }
//                                    }
//                                    Spacer(modifier = Modifier.width(12.dp))
//                                    Column {
//                                        Text(
//                                            "Dr. ${doctor.name}",
//                                            fontWeight = FontWeight.SemiBold,
//                                            fontSize = 15.sp
//                                        )
//                                        Text(
//                                            doctor.specialization,
//                                            fontSize = 12.sp,
//                                            color = Color.Gray
//                                        )
//                                    }
//                                }
//                                TextButton(onClick = { selectedDoctor = doctor; step = 2 }) {
//                                    Text("Select", color = GREEN)
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            2 -> {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    IconButton(onClick = { step = 1 }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = GREEN)
//                    }
//                    Text("Appointment Details", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GREEN)
//                }
//
//                // Show selected doctor summary
//                selectedDoctor?.let { doc ->
//                    Card(
//                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1)),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(12.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = GREEN)
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Column {
//                                Text(
//                                    "Dr. ${doc.name}",
//                                    fontWeight = FontWeight.SemiBold,
//                                    color = GREEN
//                                )
//                                Text(doc.specialization, fontSize = 12.sp, color = Color.Gray)
//                            }
//                        }
//                    }
//                }
//
//                HorizontalDivider()
//
//                OutlinedTextField(
//                    value = appointmentDate,
//                    onValueChange = { appointmentDate = it },
//                    label = { Text("Preferred Date (e.g. 15/05/2026)") },
//                    leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true
//                )
//
//                OutlinedTextField(
//                    value = appointmentTime,
//                    onValueChange = { appointmentTime = it },
//                    label = { Text("Preferred Time (e.g. 10:30 AM)") },
//                    leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
//                    modifier = Modifier.fillMaxWidth(),
//                    singleLine = true
//                )
//
//                OutlinedTextField(
//                    value = reason,
//                    onValueChange = { reason = it },
//                    label = { Text("Reason for Appointment") },
//                    placeholder = { Text("Describe your symptoms or concern…") },
//                    leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null) },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .heightIn(min = 120.dp),
//                    minLines = 4,
//                    maxLines = 6
//                )
//
//                Button(
//                    onClick = {
//                        when {
//                            patientId.isBlank() ->
//                                Toast.makeText(context, "You must be logged in.", Toast.LENGTH_SHORT).show()
//                            appointmentDate.isBlank() ->
//                                Toast.makeText(context, "Please enter a preferred date.", Toast.LENGTH_SHORT).show()
//                            appointmentTime.isBlank() ->
//                                Toast.makeText(context, "Please enter a preferred time.", Toast.LENGTH_SHORT).show()
//                            reason.isBlank() ->
//                                Toast.makeText(context, "Please describe the reason.", Toast.LENGTH_SHORT).show()
//                            else -> {
//                                appointmentViewModel.bookAppointment(
//                                    patientId      = patientId,
//                                    patientName    = patientName,
//                                    reason         = reason.trim(),
//                                    date           = appointmentDate.trim(),
//                                    time           = appointmentTime.trim(),
//                                    specialization = specialization,
//                                    doctorId       = selectedDoctor?.uid ?: "",
//                                    doctorName     = selectedDoctor?.name ?: "",
//                                    onSuccess      = {},
//                                    onFailure      = {}
//                                )
//                            }
//                        }
//                    },
//                    enabled = !isLoading,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(52.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = GREEN)
//                ) {
//                    if (isLoading) {
//                        CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(22.dp))
//                    } else {
//                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
//                        Spacer(modifier = Modifier.width(6.dp))
//                        Text("Confirm Booking", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
//                    }
//                }
//            }
//        }
//    }
//}
//
//// ─────────────────────────────────────────────────────────────────────────────
//// Step indicator widget
//// ─────────────────────────────────────────────────────────────────────────────
//@Composable
//private fun StepIndicator(currentStep: Int) {
//    val steps = listOf("Specialization", "Doctor", "Details")
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        horizontalArrangement = Arrangement.spacedBy(4.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        steps.forEachIndexed { index, label ->
//            val active = index == currentStep
//            val done   = index < currentStep
//            Surface(
//                color = when {
//                    done   -> GREEN
//                    active -> GREEN
//                    else   -> Color(0xFFE0E0E0)
//                },
//                shape = MaterialTheme.shapes.small,
//                modifier = Modifier
//                    .weight(1f)
//                    .height(4.dp)
//            ) {}
//        }
//    }
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        listOf("1. Specialization", "2. Doctor", "3. Details").forEachIndexed { index, label ->
//            Text(
//                label,
//                fontSize = 10.sp,
//                color = if (index <= currentStep) GREEN else Color.Gray,
//                fontWeight = if (index == currentStep) FontWeight.Bold else FontWeight.Normal
//            )
//        }
//    }
//}
//
//// ─────────────────────────────────────────────────────────────────────────────
//// Tab 1: Patient's appointment list
//// ─────────────────────────────────────────────────────────────────────────────
//@Composable
//private fun MyAppointmentsTab(appointments: List<Appointment>) {
//    if (appointments.isEmpty()) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(
//                    Icons.Default.CalendarToday,
//                    contentDescription = null,
//                    tint = Color.LightGray,
//                    modifier = Modifier.size(56.dp)
//                )
//                Spacer(modifier = Modifier.height(12.dp))
//                Text("No appointments yet.", color = Color.Gray, fontSize = 15.sp)
//                Text("Book one from the Book tab.", color = Color.LightGray, fontSize = 12.sp)
//            }
//        }
//        return
//    }
//
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
//        verticalArrangement = Arrangement.spacedBy(10.dp)
//    ) {
//        items(appointments, key = { it.appointmentId }) { appt ->
//            PatientAppointmentCard(appt)
//        }
//    }
//}
//
//@Composable
//private fun PatientAppointmentCard(appointment: Appointment) {
//    val (statusLabel, statusColor) = when (appointment.status) {
//        "pending_admin"      -> "Awaiting Assignment" to Color(0xFFFFA000)
//        "assigned"           -> "Assigned" to Color(0xFF1976D2)
//        "taken"              -> "In Progress" to Color(0xFF388E3C)
//        "referral_requested" -> "Referral Pending" to Color(0xFFD32F2F)
//        "completed"          -> "Completed" to Color(0xFF757575)
//        else                 -> appointment.status.replaceFirstChar { it.uppercase() } to Color.Gray
//    }
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Column(modifier = Modifier.padding(14.dp)) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Surface(
//                    color = statusColor.copy(alpha = 0.15f),
//                    shape = MaterialTheme.shapes.small
//                ) {
//                    Text(
//                        text = statusLabel,
//                        color = statusColor,
//                        fontSize = 11.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
//                    )
//                }
//                Text(
//                    text = appointment.date,
//                    fontSize = 11.sp,
//                    color = Color.Gray
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            if (appointment.doctorName.isNotBlank()) {
//                Text(
//                    text = "Dr. ${appointment.doctorName}",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 15.sp,
//                    color = GREEN
//                )
//            } else {
//                Text(
//                    text = "Doctor not yet assigned",
//                    fontSize = 14.sp,
//                    color = Color.Gray,
//                    fontWeight = FontWeight.Medium
//                )
//            }
//
//            Text(
//                text = "${appointment.specialization}  •  ${appointment.time}",
//                fontSize = 13.sp,
//                color = Color.Gray
//            )
//
//            if (appointment.reason.isNotBlank()) {
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = "Reason: ${appointment.reason}",
//                    fontSize = 12.sp,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//
//            if (appointment.notificationMessage.isNotBlank() &&
//                appointment.status != "pending_admin"
//            ) {
//                Spacer(modifier = Modifier.height(6.dp))
//                Card(
//                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(
//                        text = appointment.notificationMessage,
//                        fontSize = 12.sp,
//                        color = Color(0xFF2E7D32),
//                        modifier = Modifier.padding(8.dp)
//                    )
//                }
//            }
//        }
//    }
//}



import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartmedicalsystem.data.AppointmentViewModel
import com.example.smartmedicalsystem.models.Appointment
import com.example.smartmedicalsystem.models.DoctorProfile
import com.google.firebase.auth.FirebaseAuth

private val GREEN = Color(0xFF00604E)

// ─────────────────────────────────────────────────────────────────────────────
// Main screen: two tabs — Book Appointment / My Appointments
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingAppointmentsScreen(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel = viewModel()
) {
    val context     = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser
    val patientId   = currentUser?.uid ?: ""

    LaunchedEffect(patientId) {
        if (patientId.isNotBlank()) {
            appointmentViewModel.listenPatientAppointments(patientId)
        }
    }

    val appointments   by appointmentViewModel.patientAppointments
    val successMessage by appointmentViewModel.successMessage
    val errorMessage   by appointmentViewModel.errorMessage

    LaunchedEffect(successMessage) {
        successMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            appointmentViewModel.clearMessages()
        }
    }
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            appointmentViewModel.clearMessages()
        }
    }

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Appointments") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = GREEN,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor   = GREEN,
                contentColor     = Color.White
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick  = { selectedTab = 0 },
                    text     = { Text("Book Appointment") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick  = { selectedTab = 1 },
                    text     = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("My Appointments")
                            if (appointments.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Badge { Text("${appointments.size}") }
                            }
                        }
                    }
                )
            }

            when (selectedTab) {
                0 -> BookAppointmentTab(
                    appointmentViewModel = appointmentViewModel,
                    patientId   = patientId,
                    patientName = currentUser?.displayName
                        ?: currentUser?.email?.substringBefore("@")
                        ?: "Patient",
                    onBooked = { selectedTab = 1 }
                )
                1 -> MyAppointmentsTab(appointments = appointments)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Tab 0: Three-step booking — Specialization → Doctor → Details
// ─────────────────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BookAppointmentTab(
    appointmentViewModel: AppointmentViewModel,
    patientId: String,
    patientName: String,
    onBooked: () -> Unit
) {
    val context    = LocalContext.current
    val isLoading  by appointmentViewModel.isLoading
    val doctorList by appointmentViewModel.doctorList

    var step            by remember { mutableStateOf(0) }
    var specialization  by remember { mutableStateOf("") }
    var selectedDoctor  by remember { mutableStateOf<DoctorProfile?>(null) }
    var appointmentDate by remember { mutableStateOf("") }
    var appointmentTime by remember { mutableStateOf("") }
    var reason          by remember { mutableStateOf("") }

    val specializationOptions = listOf(
        "General Practice", "Cardiology", "Dermatology", "Neurology",
        "Orthopedics", "Pediatrics", "Psychiatry", "Gynecology",
        "Ophthalmology", "ENT"
    )

    val successMessage by appointmentViewModel.successMessage
    LaunchedEffect(successMessage) {
        if (successMessage != null) onBooked()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        StepIndicator(currentStep = step)

        when (step) {

            // ── Step 1: Choose specialization ─────────────────────────────
            0 -> {
                Text("Select Specialization", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GREEN)
                Text("Choose the department you want to visit.", fontSize = 13.sp, color = Color.Gray)
                HorizontalDivider()

                specializationOptions.forEach { option ->
                    Card(
                        onClick = {
                            specialization = option
                            appointmentViewModel.reloadDoctors(specialization = option)
                            step = 1
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (specialization == option) Color(0xFFE0F2F1)
                            else MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.MedicalServices, contentDescription = null, tint = GREEN, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(option, fontWeight = FontWeight.Medium, fontSize = 15.sp)
                            }
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
                        }
                    }
                }
            }

            // ── Step 2: Choose doctor ─────────────────────────────────────
            1 -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { step = 0 }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = GREEN)
                    }
                    Text("Doctors in $specialization", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GREEN)
                }
                Text("Tap a doctor to proceed with booking.", fontSize = 13.sp, color = Color.Gray)
                HorizontalDivider()

                if (doctorList.isEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = GREEN, modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Loading doctors…", color = Color.Gray, fontSize = 13.sp)
                        }
                    }
                } else {
                    doctorList.forEach { doctor ->
                        Card(
                            onClick = { selectedDoctor = doctor; step = 2 },
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = MaterialTheme.shapes.extraSmall,
                                        color = Color(0xFFE0F2F1),
                                        modifier = Modifier.size(40.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.Person, contentDescription = null, tint = GREEN, modifier = Modifier.size(24.dp))
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text("Dr. ${doctor.name}", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                                        Text(doctor.specialization, fontSize = 12.sp, color = Color.Gray)
                                    }
                                }
                                TextButton(onClick = { selectedDoctor = doctor; step = 2 }) {
                                    Text("Select", color = GREEN)
                                }
                            }
                        }
                    }
                }
            }

            // ── Step 3: Date / time / reason ──────────────────────────────
            2 -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { step = 1 }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = GREEN)
                    }
                    Text("Appointment Details", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = GREEN)
                }

                selectedDoctor?.let { doc ->
                    Card(
                        colors   = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = GREEN)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Dr. ${doc.name}", fontWeight = FontWeight.SemiBold, color = GREEN)
                                Text(doc.specialization, fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }

                HorizontalDivider()

                OutlinedTextField(
                    value = appointmentDate,
                    onValueChange = { appointmentDate = it },
                    label = { Text("Preferred Date (e.g. 15/05/2026)") },
                    leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = appointmentTime,
                    onValueChange = { appointmentTime = it },
                    label = { Text("Preferred Time (e.g. 10:30 AM)") },
                    leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = reason,
                    onValueChange = { reason = it },
                    label = { Text("Reason for Appointment") },
                    placeholder = { Text("Describe your symptoms or concern…") },
                    leadingIcon = { Icon(Icons.Default.Notes, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
                    minLines = 4,
                    maxLines = 6
                )

                Button(
                    onClick = {
                        when {
                            patientId.isBlank() ->
                                Toast.makeText(context, "You must be logged in.", Toast.LENGTH_SHORT).show()
                            appointmentDate.isBlank() ->
                                Toast.makeText(context, "Please enter a preferred date.", Toast.LENGTH_SHORT).show()
                            appointmentTime.isBlank() ->
                                Toast.makeText(context, "Please enter a preferred time.", Toast.LENGTH_SHORT).show()
                            reason.isBlank() ->
                                Toast.makeText(context, "Please describe the reason.", Toast.LENGTH_SHORT).show()
                            else -> {
                                appointmentViewModel.bookAppointment(
                                    patientId      = patientId,
                                    patientName    = patientName,
                                    reason         = reason.trim(),
                                    date           = appointmentDate.trim(),
                                    time           = appointmentTime.trim(),
                                    specialization = specialization,
                                    doctorId       = selectedDoctor?.uid ?: "",
                                    doctorName     = selectedDoctor?.name ?: "",
                                    onSuccess      = {},
                                    onFailure      = {}
                                )
                            }
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = GREEN)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp, modifier = Modifier.size(22.dp))
                    } else {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Confirm Booking", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Step indicator
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun StepIndicator(currentStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            Surface(
                color = if (index <= currentStep) GREEN else Color(0xFFE0E0E0),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.weight(1f).height(4.dp)
            ) {}
        }
    }
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        listOf("1. Specialization", "2. Doctor", "3. Details").forEachIndexed { index, label ->
            Text(
                label,
                fontSize   = 10.sp,
                color      = if (index <= currentStep) GREEN else Color.Gray,
                fontWeight = if (index == currentStep) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Tab 1: Patient's appointment list
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun MyAppointmentsTab(appointments: List<Appointment>) {
    if (appointments.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(56.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Text("No appointments yet.", color = Color.Gray, fontSize = 15.sp)
                Text("Book one from the Book tab.", color = Color.LightGray, fontSize = 12.sp)
            }
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(appointments, key = { it.appointmentId }) { appt ->
            PatientAppointmentCard(appt)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Patient appointment card — shows referral banner, reschedule alert, etc.
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun PatientAppointmentCard(appointment: Appointment) {
    val (statusLabel, statusColor) = when (appointment.status) {
        "pending_admin"      -> "Awaiting Assignment" to Color(0xFFFFA000)
        "assigned"           -> "Assigned"             to Color(0xFF1976D2)
        "accepted"           -> "Confirmed ✅"          to Color(0xFF388E3C)
        "referred"           -> "Referred 🔄"           to Color(0xFF7B1FA2)
        "rescheduled"        -> "Rescheduled 📅"        to Color(0xFF1976D2)
        "taken"              -> "In Progress"           to Color(0xFF388E3C)
        "referral_requested" -> "Referral Pending"      to Color(0xFFD32F2F)
        "completed"          -> "Completed"             to Color(0xFF757575)
        else                 -> appointment.status.replaceFirstChar { it.uppercase() } to Color.Gray
    }

    Card(
        modifier  = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            // ── Referral banner — shown at very top if referred ───────────
            if (appointment.status == "referred" &&
                appointment.referredDoctorName.isNotBlank()
            ) {
                Card(
                    colors   = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.SwapHoriz,
                            contentDescription = null,
                            tint     = Color(0xFF7B1FA2),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                "Your appointment has been referred to:",
                                fontSize    = 11.sp,
                                color       = Color(0xFF4A148C),
                                fontWeight  = FontWeight.SemiBold
                            )
                            Text(
                                "Dr. ${appointment.referredDoctorName}",
                                fontSize   = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color      = Color(0xFF4A148C)
                            )
                            Text(
                                appointment.referredDoctorSpecialization,
                                fontSize = 11.sp,
                                color    = Color(0xFF7B1FA2)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // ── Reschedule banner ─────────────────────────────────────────
            if (appointment.status == "rescheduled" &&
                appointment.rescheduledDate.isNotBlank()
            ) {
                Card(
                    colors   = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = null,
                            tint     = Color(0xFF1976D2),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                "Your appointment has been rescheduled:",
                                fontSize   = 11.sp,
                                color      = Color(0xFF0D47A1),
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "New Date: ${appointment.rescheduledDate}  •  Time: ${appointment.rescheduledTime}",
                                fontSize   = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color      = Color(0xFF0D47A1)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // ── Status chip + date ────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = statusColor.copy(alpha = 0.15f),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text   = statusLabel,
                        color  = statusColor,
                        fontSize    = 11.sp,
                        fontWeight  = FontWeight.SemiBold,
                        modifier    = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Text(text = appointment.date, fontSize = 11.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Doctor name (or referral target)
            val displayDoctorName = when {
                appointment.status == "referred" && appointment.referredDoctorName.isNotBlank() ->
                    "Dr. ${appointment.referredDoctorName}"
                appointment.doctorName.isNotBlank() -> "Dr. ${appointment.doctorName}"
                else -> null
            }
            if (displayDoctorName != null) {
                Text(text = displayDoctorName, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = GREEN)
            } else {
                Text(text = "Doctor not yet assigned", fontSize = 14.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
            }

            Text(
                text = "${appointment.specialization}  •  ${appointment.time}",
                fontSize = 13.sp,
                color = Color.Gray
            )

            if (appointment.reason.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Reason: ${appointment.reason}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            // Notification message (accepted / confirmed / completed)
            if (appointment.notificationMessage.isNotBlank() &&
                appointment.status !in listOf("pending_admin", "referred", "rescheduled")
            ) {
                Spacer(modifier = Modifier.height(6.dp))
                Card(
                    colors   = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = null,
                            tint     = Color(0xFF2E7D32),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text     = appointment.notificationMessage,
                            fontSize = 12.sp,
                            color    = Color(0xFF2E7D32)
                        )
                    }
                }
            }
        }
    }
}