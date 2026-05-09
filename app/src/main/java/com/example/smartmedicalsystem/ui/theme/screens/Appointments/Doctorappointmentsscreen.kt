package com.example.smartmedicalsystem.ui.theme.screens.Appointments

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.SwapHoriz
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
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorAppointmentsScreen(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel = viewModel()
) {
    val context = LocalContext.current
    val currentDoctorId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    val appointments by appointmentViewModel.doctorAppointments
    val isLoading by appointmentViewModel.isLoading
    val errorMessage by appointmentViewModel.errorMessage
    val successMessage by appointmentViewModel.successMessage

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
    var referralNote by remember { mutableStateOf("") }

    if (referralAppointment != null) {
        AlertDialog(
            onDismissRequest = {
                referralAppointment = null
                referralNote = ""
            },
            title = { Text("Request Referral") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Enter a reason for the referral. The admin will be alerted and will re-assign the appointment.",
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
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (referralNote.isBlank()) {
                            Toast.makeText(context, "Please enter a referral reason.", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        appointmentViewModel.requestReferral(
                            appointment = referralAppointment!!,
                            referralNote = referralNote.trim()
                        )
                        referralAppointment = null
                        referralNote = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) { Text("Send to Admin") }
            },
            dismissButton = {
                TextButton(onClick = {
                    referralAppointment = null
                    referralNote = ""
                }) { Text("Cancel") }
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
                Text("No appointments assigned yet.", color = Color.Gray)
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
                    isLoading = isLoading,
                    onProceed = {
                        appointmentViewModel.proceedWithAppointment(appt)
                    },
                    onReferral = {
                        referralAppointment = appt
                        referralNote = ""
                    },
                    onComplete = {
                        appointmentViewModel.markCompleted(appt)
                    }
                )
            }
        }
    }
}

@Composable
private fun DoctorAppointmentCard(
    appointment: Appointment,
    isLoading: Boolean,
    onProceed: () -> Unit,
    onReferral: () -> Unit,
    onComplete: () -> Unit
) {

    val displayStatus = when (appointment.status) {
        "assigned"           -> "New"
        "taken"              -> "Pending"
        "referral_requested" -> "Referral Pending"
        "completed"          -> "Completed"
        else                 -> appointment.status.replaceFirstChar { it.uppercase() }
    }

    val statusColor = when (appointment.status) {
        "assigned"           -> Color(0xFFFFA000)
        "taken"              -> Color(0xFF1976D2)
        "referral_requested" -> Color(0xFF9E9E9E)
        "completed"          -> Color(0xFF388E3C)
        else                 -> Color.Gray
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
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
            if (appointment.reason.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Reason: ${appointment.reason}",
                    fontSize = 13.sp
                )
            }
            if (appointment.specialization.isNotBlank()) {
                Text(
                    text = "Specialization: ${appointment.specialization}",
                    fontSize = 12.sp,
                    color = Color(0xFF00604E)
                )
            }

            if (appointment.status == "assigned") {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onReferral,
                        enabled = !isLoading,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFD32F2F)
                        )
                    ) {
                        Icon(
                            Icons.Default.SwapHoriz,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Referral", fontSize = 13.sp)
                    }

                    Button(
                        onClick = onProceed,
                        enabled = !isLoading,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00604E)
                        )
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Proceed", fontSize = 13.sp)
                    }
                }
            }

            if (appointment.status == "taken") {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onComplete,
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
                ) {
                    Text("Mark as Completed")
                }
            }

            if (appointment.status == "referral_requested") {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "⏳ Referral sent to admin. Awaiting re-assignment.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}