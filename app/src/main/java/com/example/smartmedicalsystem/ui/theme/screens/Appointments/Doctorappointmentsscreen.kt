package com.example.smartmedicalsystem.ui.theme.screens.Appointments

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

private val DR_GREEN = Color(0xFF00604E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorAppointmentsScreen(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentDoctorId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    val appointments   by appointmentViewModel.doctorAppointments
    val isLoading      by appointmentViewModel.isLoading
    val errorMessage   by appointmentViewModel.errorMessage
    val successMessage by appointmentViewModel.successMessage
    val doctorList     by appointmentViewModel.doctorList

    LaunchedEffect(currentDoctorId) {
        if (currentDoctorId.isNotBlank()) {
            appointmentViewModel.listenDoctorAppointments(currentDoctorId)
        }
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

    var referralAppointment by remember { mutableStateOf<Appointment?>(null) }
    var referralNote        by remember { mutableStateOf("") }
    var referralStep        by remember { mutableStateOf(0) } // 0=reason, 1=pick doctor
    var selectedReferralDoc by remember { mutableStateOf<DoctorProfile?>(null) }

    var rescheduleAppointment by remember { mutableStateOf<Appointment?>(null) }
    var newDate               by remember { mutableStateOf("") }
    var newTime               by remember { mutableStateOf("") }

    referralAppointment?.let { appt ->
        AlertDialog(
            onDismissRequest = {
                referralAppointment = null
                referralNote = ""
                referralStep = 0
                selectedReferralDoc = null
            },
            title = {
                Text(
                    if (referralStep == 0) "Referral – Reason" else "Select a Doctor",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                when (referralStep) {
                    0 -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            "Provide a reason for the referral. Then you will select another " +
                                    "doctor in the same specialization (${appt.specialization}).",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                        OutlinedTextField(
                            value = referralNote,
                            onValueChange = { referralNote = it },
                            label = { Text("Referral reason") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )
                    }
                    1 -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            "Doctors in ${appt.specialization}:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        if (doctorList.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = DR_GREEN, modifier = Modifier.size(28.dp))
                            }
                        } else {
                            doctorList.forEach { doc ->
                                val isSelected = selectedReferralDoc?.uid == doc.uid
                                Card(
                                    onClick = { selectedReferralDoc = doc },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) Color(0xFFE0F2F1)
                                        else MaterialTheme.colorScheme.surface
                                    ),
                                    elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                Icons.Default.Person,
                                                contentDescription = null,
                                                tint = DR_GREEN,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Column {
                                                Text("Dr. ${doc.name}", fontWeight = FontWeight.Medium)
                                                Text(doc.specialization, fontSize = 11.sp, color = Color.Gray)
                                            }
                                        }
                                        if (isSelected) {
                                            Icon(
                                                Icons.Default.CheckCircle,
                                                contentDescription = "Selected",
                                                tint = DR_GREEN,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                when (referralStep) {
                    0 -> Button(
                        onClick = {
                            if (referralNote.isBlank()) {
                                Toast.makeText(context, "Please enter a referral reason.", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            appointmentViewModel.reloadDoctors(
                                specialization  = appt.specialization,
                                excludeDoctorId = currentDoctorId
                            )
                            referralStep = 1
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DR_GREEN)
                    ) { Text("Next: Pick Doctor") }

                    1 -> Button(
                        onClick = {
                            val doc = selectedReferralDoc
                            if (doc == null) {
                                Toast.makeText(context, "Please select a doctor.", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            appointmentViewModel.referralToDoctor(
                                appointment   = appt,
                                referredDoctor = doc,
                                referralNote  = referralNote.trim()
                            )
                            referralAppointment = null
                            referralNote = ""
                            referralStep = 0
                            selectedReferralDoc = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = DR_GREEN)
                    ) { Text("Confirm Referral") }
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    referralAppointment = null
                    referralNote = ""
                    referralStep = 0
                    selectedReferralDoc = null
                }) {
                    Text(if (referralStep == 1) "Back" else "Cancel")
                }
            }
        )
    }

    rescheduleAppointment?.let { appt ->
        AlertDialog(
            onDismissRequest = { rescheduleAppointment = null; newDate = ""; newTime = "" },
            title = { Text("Reschedule Appointment", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        "Current: ${appt.date} at ${appt.time}\nPatient: ${appt.patientName}",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                    HorizontalDivider()
                    OutlinedTextField(
                        value = newDate,
                        onValueChange = { newDate = it },
                        label = { Text("New Date (e.g. 20/05/2026)") },
                        leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newTime,
                        onValueChange = { newTime = it },
                        label = { Text("New Time (e.g. 2:00 PM)") },
                        leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = null,
                                tint = Color(0xFFF57F17),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                "A notification with the new date and time will be sent to the patient.",
                                fontSize = 11.sp,
                                color = Color(0xFF6D4C41)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        when {
                            newDate.isBlank() -> Toast.makeText(context, "Please enter a new date.", Toast.LENGTH_SHORT).show()
                            newTime.isBlank() -> Toast.makeText(context, "Please enter a new time.", Toast.LENGTH_SHORT).show()
                            else -> {
                                appointmentViewModel.rescheduleAppointment(
                                    appointment = appt,
                                    newDate     = newDate.trim(),
                                    newTime     = newTime.trim()
                                )
                                rescheduleAppointment = null
                                newDate = ""
                                newTime = ""
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                ) { Text("Reschedule & Notify") }
            },
            dismissButton = {
                TextButton(onClick = { rescheduleAppointment = null; newDate = ""; newTime = "" }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Appointments") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = DR_GREEN,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->

        if (appointments.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("No appointments assigned yet.", color = Color.Gray)
                }
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
                DoctorAppointmentCard(
                    appointment = appt,
                    isLoading   = isLoading,
                    onAccept    = {
                        appointmentViewModel.acceptAppointment(appt)
                    },
                    onReferral  = {
                        referralAppointment = appt
                        referralNote = ""
                        referralStep = 0
                        selectedReferralDoc = null
                    },
                    onReschedule = {
                        rescheduleAppointment = appt
                        newDate = appt.date
                        newTime = appt.time
                    },
                    onProceed   = { appointmentViewModel.proceedWithAppointment(appt) },
                    onComplete  = { appointmentViewModel.markCompleted(appt) }
                )
            }
        }
    }
}

@Composable
private fun DoctorAppointmentCard(
    appointment: Appointment,
    isLoading: Boolean,
    onAccept: () -> Unit,
    onReferral: () -> Unit,
    onReschedule: () -> Unit,
    onProceed: () -> Unit,
    onComplete: () -> Unit
) {
    val (displayStatus, statusColor) = when (appointment.status) {
        "assigned"           -> "New"             to Color(0xFFFFA000)
        "accepted"           -> "Accepted"         to Color(0xFF388E3C)
        "referred"           -> "Referred"         to Color(0xFF7B1FA2)
        "rescheduled"        -> "Rescheduled"      to Color(0xFF1976D2)
        "taken"              -> "Pending"          to Color(0xFF1976D2)
        "referral_requested" -> "Referral Pending" to Color(0xFF9E9E9E)
        "completed"          -> "Completed"        to Color(0xFF388E3C)
        else                 -> appointment.status.replaceFirstChar { it.uppercase() } to Color.Gray
    }

    Card(
        modifier  = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

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
                        text = displayStatus,
                        color = statusColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Text(
                    text = appointment.date,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                    fontSize = 12.sp,
                    color = DR_GREEN
                )
            }
            if (appointment.reason.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Reason: ${appointment.reason}", fontSize = 13.sp)
            }

            if (appointment.status == "assigned") {
                Spacer(modifier = Modifier.height(12.dp))

                // Row 1: Accept + Referral
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick  = onAccept,
                        enabled  = !isLoading,
                        modifier = Modifier.weight(1f),
                        colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Accept", fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick  = onReferral,
                        enabled  = !isLoading,
                        modifier = Modifier.weight(1f),
                        colors   = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF7B1FA2))
                    ) {
                        Icon(Icons.Default.SwapHoriz, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Refer", fontSize = 13.sp)
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedButton(
                    onClick  = onReschedule,
                    enabled  = !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors   = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF1976D2))
                ) {
                    Icon(Icons.Default.EditCalendar, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Reschedule", fontSize = 13.sp)
                }
            }


            if (appointment.status == "accepted") {
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick  = onReschedule,
                        enabled  = !isLoading,
                        modifier = Modifier.weight(1f),
                        colors   = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF1976D2))
                    ) {
                        Icon(Icons.Default.EditCalendar, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reschedule", fontSize = 13.sp)
                    }
                    Button(
                        onClick  = onProceed,
                        enabled  = !isLoading,
                        modifier = Modifier.weight(1f),
                        colors   = ButtonDefaults.buttonColors(containerColor = DR_GREEN)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Start", fontSize = 13.sp)
                    }
                }
            }

            if (appointment.status == "rescheduled") {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    colors   = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = Color(0xFF1976D2), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Rescheduled to ${appointment.date} at ${appointment.time}. Patient notified.",
                            fontSize = 12.sp,
                            color = Color(0xFF0D47A1)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Button(
                    onClick  = onProceed,
                    enabled  = !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors   = ButtonDefaults.buttonColors(containerColor = DR_GREEN)
                ) { Text("Proceed with Appointment") }
            }

            if (appointment.status == "taken") {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick  = onComplete,
                    enabled  = !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
                ) { Text("Mark as Completed") }
            }

            if (appointment.status == "referred") {
                Spacer(modifier = Modifier.height(6.dp))
                Card(
                    colors   = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = Color(0xFF7B1FA2), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Referred to Dr. ${appointment.referredDoctorName} (${appointment.referredDoctorSpecialization}). " +
                                    "Patient has been notified.",
                            fontSize = 12.sp,
                            color = Color(0xFF4A148C)
                        )
                    }
                }
            }

            if (appointment.status == "referral_requested") {
                Spacer(modifier = Modifier.height(6.dp))
                Text("⏳ Referral sent to admin. Awaiting re-assignment.", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}