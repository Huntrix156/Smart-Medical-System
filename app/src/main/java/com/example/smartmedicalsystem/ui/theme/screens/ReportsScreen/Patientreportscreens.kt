package com.example.smartmedicalsystem.ui.theme.screens.ReportsScreen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartmedicalsystem.data.ReportViewModel
import com.example.smartmedicalsystem.models.AppointmentSummary
import com.example.smartmedicalsystem.models.DiagnosisReport
import com.example.smartmedicalsystem.models.FollowUpReport
import com.example.smartmedicalsystem.models.PatientMedicalHistory
import com.example.smartmedicalsystem.models.PrescriptionReport
import com.example.smartmedicalsystem.models.TreatmentSummaryReport
import com.example.smartmedicalsystem.ui.theme.component.OrangeAccent
import com.example.smartmedicalsystem.ui.theme.component.ReportTopBar
import com.example.smartmedicalsystem.ui.theme.component.SectionHeader
import com.example.smartmedicalsystem.ui.theme.component.SurfaceGray
import com.example.smartmedicalsystem.ui.theme.component.TealDark
import com.example.smartmedicalsystem.ui.theme.component.TealLight
import com.example.smartmedicalsystem.ui.theme.component.TealPrimary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientMedicalHistoryScreen(
    navController: NavController,
    patientId: String,
    viewModel: ReportViewModel
) {
    val history by viewModel.patientHistory
    val isLoading by viewModel.isLoading

    LaunchedEffect(patientId) { viewModel.loadPatientMedicalHistory(patientId) }

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("My Health", "Prescriptions", "Appointments")

    Scaffold(
        topBar = { ReportTopBar("My Medical History") { navController.popBackStack() } }
    ) { pad ->
        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(pad), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TealPrimary)
            }
        } else {
            Column(Modifier.fillMaxSize().padding(pad)) {
                TabRow(selectedTabIndex = selectedTab, containerColor = TealLight, contentColor = TealPrimary) {
                    tabs.forEachIndexed { i, title ->
                        Tab(selected = selectedTab == i, onClick = { selectedTab = i }, text = { Text(title) })
                    }
                }

                when (selectedTab) {
                    0 -> MyHealthTab(history)
                    1 -> PrescriptionsTab(history?.prescriptions ?: emptyList())
                    2 -> AppointmentSummariesTab(history?.appointmentSummaries ?: emptyList())
                }
            }
        }
    }
}


@Composable
private fun MyHealthTab(history: PatientMedicalHistory?) {
    if (history == null) {
        EmptyState("No health records found")
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SectionHeader(1, "What is wrong with me?")
        }
        if (history.diagnoses.isEmpty()) {
            item { PlaceholderCard("No diagnosis records yet") }
        } else {
            items(history.diagnoses.size) { i ->
                DiagnosisHistoryCard(history.diagnoses[i])
            }
        }

        item { Spacer(Modifier.height(4.dp)); SectionHeader(2, "What treatment is ongoing?") }
        if (history.activeTreatments.isEmpty()) {
            item { PlaceholderCard("No active treatment summaries") }
        } else {
            items(history.activeTreatments.size) { i ->
                TreatmentHistoryCard(history.activeTreatments[i])
            }
        }

        item { Spacer(Modifier.height(4.dp)); SectionHeader(3, "My recovery progress") }
        if (history.followUps.isEmpty()) {
            item { PlaceholderCard("No follow-up records yet") }
        } else {
            items(history.followUps.size) { i ->
                FollowUpHistoryCard(history.followUps[i])
            }
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
private fun DiagnosisHistoryCard(d: DiagnosisReport) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.MedicalServices, null, tint = TealPrimary, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(d.diagnosis.ifBlank { "Diagnosis" }, fontWeight = FontWeight.SemiBold)
            }
            if (d.chiefComplaint.isNotBlank())
                Text("Complaint: ${d.chiefComplaint}", fontSize = 12.sp, color = Color.Gray)
            if (d.treatmentPlan.isNotBlank())
                Text("Treatment: ${d.treatmentPlan}", fontSize = 12.sp)
            RecoveryBadge(d.recoveryProgress)
            if (d.nextReviewDate.isNotBlank())
                Text("Next review: ${d.nextReviewDate}", fontSize = 11.sp, color = TealDark)
        }
    }
}

@Composable
private fun TreatmentHistoryCard(t: TreatmentSummaryReport) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TealLight),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Assignment, null, tint = TealDark, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Visit: ${t.visitDate}", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }
            if (t.interventionsDone.isNotBlank())
                Text("Interventions: ${t.interventionsDone}", fontSize = 12.sp)
            if (t.patientResponse.isNotBlank())
                Text("Your response: ${t.patientResponse}", fontSize = 12.sp, color = Color.Gray)
            if (t.followUpRequired)
                Text("⚠ Follow-up on: ${t.followUpDate}", fontSize = 12.sp, color = OrangeAccent, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun FollowUpHistoryCard(f: FollowUpReport) {
    val statusColor = when (f.currentRecoveryStatus) {
        "Recovered" -> Color(0xFF2E7D32)
        "Improving" -> TealPrimary
        "Stable" -> Color(0xFF1565C0)
        else -> Color(0xFFC62828)
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                Text("Follow-Up Record", fontWeight = FontWeight.SemiBold)
                Text(f.currentRecoveryStatus, color = statusColor, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
            if (f.symptomChanges.isNotBlank())
                Text("Changes: ${f.symptomChanges}", fontSize = 12.sp, color = Color.Gray)
            Text("Compliance: ${f.complianceWithTreatment}", fontSize = 12.sp)
            if (f.discharged) Text("✓ Discharged", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Composable
private fun RecoveryBadge(progress: String) {
    val color = when (progress) {
        "Excellent" -> Color(0xFF2E7D32)
        "Good" -> TealPrimary
        "Fair" -> Color(0xFFF57F17)
        else -> Color(0xFFC62828)
    }
    Text(
        "Recovery: $progress",
        color = color,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    )
}


@Composable
private fun PrescriptionsTab(prescriptions: List<PrescriptionReport>) {
    if (prescriptions.isEmpty()) {
        EmptyState("No prescriptions yet")
        return
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(prescriptions.size) { i ->
            PrescriptionViewCard(prescriptions[i])
        }
    }
}

@Composable
private fun PrescriptionViewCard(rx: PrescriptionReport) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Medication, null, tint = TealPrimary)
                    Spacer(Modifier.width(8.dp))
                    Text("Prescription", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Text("Valid: ${rx.validUntil}", fontSize = 11.sp, color = Color.Gray)
            }

            rx.prescriptionItems.forEachIndexed { i, item ->
                Divider()
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text("${i + 1}. ${item.medicineName}", fontWeight = FontWeight.SemiBold)
                    Text("Dosage: ${item.dosage}  •  ${item.frequency}  •  ${item.duration}", fontSize = 12.sp, color = Color.Gray)
                    if (item.notes.isNotBlank())
                        Text("Note: ${item.notes}", fontSize = 11.sp, color = TealDark)
                }
            }

            if (rx.instructions.isNotBlank()) {
                Divider()
                Text("Instructions: ${rx.instructions}", fontSize = 12.sp)
            }
            Text("Refills allowed: ${rx.refillsAllowed}", fontSize = 11.sp, color = Color.Gray)

            Divider()
            // Download button — triggers PDF export (hook into PDF skill separately)
            Button(
                onClick = { /* TODO: Call PDF export utility with rx data */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Download, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text("Download Prescription")
            }
        }
    }
}


@Composable
private fun AppointmentSummariesTab(summaries: List<AppointmentSummary>) {
    if (summaries.isEmpty()) {
        EmptyState("No appointment history")
        return
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(summaries.size) { i ->
            AppointmentSummaryCard(summaries[i])
        }
    }
}

@Composable
private fun AppointmentSummaryCard(s: AppointmentSummary) {
    val statusColor = when (s.status.lowercase()) {
        "completed" -> Color(0xFF2E7D32)
        "cancelled" -> Color(0xFFC62828)
        "upcoming" -> TealPrimary
        else -> Color(0xFFF57F17)
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${s.date}  •  ${s.time}", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(s.status.replaceFirstChar { it.uppercase() }, color = statusColor, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Text("Dr. ${s.doctorName}", fontSize = 13.sp)
            Text("Dept: ${s.department}", fontSize = 12.sp, color = Color.Gray)
            if (s.reason.isNotBlank())
                Text("Reason: ${s.reason}", fontSize = 12.sp, color = TealDark)
        }
    }
}


@Composable
private fun EmptyState(message: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.FolderOpen, null, modifier = Modifier.size(60.dp), tint = Color.LightGray)
            Spacer(Modifier.height(12.dp))
            Text(message, color = Color.Gray)
        }
    }
}

@Composable
private fun PlaceholderCard(message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray)
    ) {
        Box(Modifier.padding(16.dp)) {
            Text(message, color = Color.Gray, fontSize = 13.sp)
        }
    }
}


