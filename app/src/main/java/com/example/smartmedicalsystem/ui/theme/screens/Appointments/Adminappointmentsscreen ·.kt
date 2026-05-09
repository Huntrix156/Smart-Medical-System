package com.example.smartmedicalsystem.ui.theme.screens.Appointments

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Warning
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAppointmentsScreen(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel = viewModel()
) {
    val context = LocalContext.current

    val appointments by appointmentViewModel.adminAppointments
    val doctorList by appointmentViewModel.doctorList
    val isLoading by appointmentViewModel.isLoading
    val errorMessage by appointmentViewModel.errorMessage
    val successMessage by appointmentViewModel.successMessage

    LaunchedEffect(Unit) {
        appointmentViewModel.listenAdminAppointments()
    }

    LaunchedEffect(successMessage) {
        successMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            appointmentViewModel.clearMessages()
        }
    }
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            appointmentViewModel.clearMessages()
        }
    }

    // State for the assign-doctor bottom sheet
    var selectedAppointment by remember { mutableStateOf<Appointment?>(null) }
    var showAssignSheet by remember { mutableStateOf(false) }

    if (showAssignSheet && selectedAppointment != null) {
        AssignDoctorSheet(
            appointment = selectedAppointment!!,
            doctorList = doctorList,
            onAssign = { doctor ->
                appointmentViewModel.assignDoctorToAppointment(
                    appointment = selectedAppointment!!,
                    doctor = doctor
                )
                showAssignSheet = false
                selectedAppointment = null
            },
            onDismiss = {
                showAssignSheet = false
                selectedAppointment = null
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Appointments") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00604E),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        if (appointments.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No appointments yet.", color = Color.Gray)
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(appointments, key = { it.appointmentId }) { appt ->
                AdminAppointmentCard(
                    appointment = appt,
                    onAssignClick = {
                        selectedAppointment = appt
                        // Load doctors filtered by the patient's requested specialization
                        appointmentViewModel.reloadDoctors(
                            specialization = appt.specialization,
                            excludeDoctorId = appt.doctorId
                        )
                        showAssignSheet = true
                    }
                )
            }
        }
    }
}

@Composable
private fun AdminAppointmentCard(
    appointment: Appointment,
    onAssignClick: () -> Unit
) {
    val isReferral = appointment.status == "referral_requested"
    val isPending  = appointment.status == "pending_admin"

    val statusLabel = when (appointment.status) {
        "pending_admin"      -> "Awaiting Assignment"
        "assigned"           -> "Assigned"
        "referral_requested" -> "⚠ Referral Requested"
        "taken"              -> "Taken"
        "completed"          -> "Completed"
        else                 -> appointment.status.replaceFirstChar { it.uppercase() }
    }

    val statusColor = when (appointment.status) {
        "pending_admin"      -> Color(0xFFFFA000)
        "assigned"           -> Color(0xFF1976D2)
        "referral_requested" -> Color(0xFFD32F2F)
        "taken"              -> Color(0xFF388E3C)
        "completed"          -> Color(0xFF757575)
        else                 -> Color.Gray
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isReferral) 6.dp else 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isReferral) Color(0xFFFFF3E0) else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            // ── Status badge + alert icon ─────────────────────────────
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
                        text = statusLabel,
                        color = statusColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                if (isReferral) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = "Referral Alert",
                        tint = Color(0xFFD32F2F),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Patient info ──────────────────────────────────────────
            Text(
                text = "Patient: ${appointment.patientName}",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Text(
                text = "Date: ${appointment.date}  •  Time: ${appointment.time}",
                fontSize = 13.sp,
                color = Color.Gray
            )
            if (appointment.specialization.isNotBlank()) {
                Text(
                    text = "Specialization: ${appointment.specialization}",
                    fontSize = 13.sp,
                    color = Color(0xFF00604E),
                    fontWeight = FontWeight.Medium
                )
            }
            if (appointment.reason.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Reason: ${appointment.reason}",
                    fontSize = 13.sp
                )
            }
            if (appointment.doctorName.isNotBlank()) {
                Text(
                    text = "Doctor: Dr. ${appointment.doctorName}",
                    fontSize = 13.sp,
                    color = Color(0xFF1976D2)
                )
            }

            // Referral note from doctor
            if (isReferral && appointment.referralNote.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Doctor's note: ${appointment.referralNote}",
                        fontSize = 12.sp,
                        color = Color(0xFFB71C1C),
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            // ── Action button ─────────────────────────────────────────
            if (isPending || isReferral) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onAssignClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isReferral) Color(0xFFD32F2F) else Color(0xFF00604E)
                    )
                ) {
                    Icon(
                        Icons.Default.AssignmentInd,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isReferral) "Re-assign Doctor" else "Assign Doctor")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssignDoctorSheet(
    appointment: Appointment,
    doctorList: List<DoctorProfile>,
    onAssign: (DoctorProfile) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                "Assign a Doctor",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
            Text(
                "Specialization: ${appointment.specialization.ifBlank { "Any" }}",
                fontSize = 13.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(12.dp))

            if (doctorList.isEmpty()) {
                Text(
                    "No doctors found for this specialization.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                doctorList.forEach { doctor ->
                    Card(
                        onClick = { onAssign(doctor) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    "Dr. ${doctor.name}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    doctor.specialization,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            TextButton(onClick = { onAssign(doctor) }) {
                                Text("Assign", color = Color(0xFF00604E))
                            }
                        }
                    }
                }
            }
        }
    }
}