package com.example.smartmedicalsystem.ui.theme.screens.ReportsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.smartmedicalsystem.models.DiagnosisReport
import com.example.smartmedicalsystem.models.FollowUpReport
import com.example.smartmedicalsystem.models.PrescriptionItem
import com.example.smartmedicalsystem.models.PrescriptionReport
import com.example.smartmedicalsystem.models.ReportMeta
import com.example.smartmedicalsystem.models.TreatmentSummaryReport
import com.example.smartmedicalsystem.navigation.ROUTE_REPORT_DIAGNOSIS
import com.example.smartmedicalsystem.navigation.ROUTE_REPORT_FOLLOWUP
import com.example.smartmedicalsystem.navigation.ROUTE_REPORT_PRESCRIPTION
import com.example.smartmedicalsystem.navigation.ROUTE_REPORT_TREATMENT_SUMMARY
import com.example.smartmedicalsystem.navigation.ROUTE_REPORT_TRENDING_DISEASE
import com.example.smartmedicalsystem.ui.theme.component.ReportDropdown
import com.example.smartmedicalsystem.ui.theme.component.ReportSubmitButton
import com.example.smartmedicalsystem.ui.theme.component.ReportTextField
import com.example.smartmedicalsystem.ui.theme.component.ReportTopBar
import com.example.smartmedicalsystem.ui.theme.component.SectionHeader
import com.example.smartmedicalsystem.ui.theme.component.TealDark
import com.example.smartmedicalsystem.ui.theme.component.TealLight
import com.example.smartmedicalsystem.ui.theme.component.TealPrimary
import com.google.firebase.auth.FirebaseAuth



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorReportHubScreen(navController: NavController) {
    Scaffold(topBar = { ReportTopBar("Medical Reports") { navController.popBackStack() } }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Generate Reports", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(
                "Select the type of medical report to create",
                fontSize = 13.sp, color = Color.Gray
            )
            Spacer(Modifier.height(4.dp))

            val items = listOf(
                Triple("Diagnosis Report", "What is wrong with the patient", Icons.Default.MedicalServices),
                Triple("Treatment Summary", "Post-appointment clinical summary", Icons.Default.Assignment),
                Triple("Prescription", "Medicines & dosage for patient", Icons.Default.Medication),
                Triple("Follow-Up Report", "Recovery progress check", Icons.Default.EventRepeat),
                Triple("Trending Disease", "Report & peer review dept. trends", Icons.Default.TrendingUp)
            )
            val routes = listOf(
//                "report_diagnosis", "report_treatment_summary",
//                "report_prescription", "report_followup", "report_trending_disease"
                ROUTE_REPORT_DIAGNOSIS,
                ROUTE_REPORT_TREATMENT_SUMMARY,
                ROUTE_REPORT_PRESCRIPTION,
                ROUTE_REPORT_FOLLOWUP,
                ROUTE_REPORT_TRENDING_DISEASE
            )

            items.forEachIndexed { i, (title, subtitle, icon) ->
                Card(
                    onClick = { navController.navigate(routes[i]) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = if (i == 4) TealDark else TealPrimary),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, null, tint = Color.White, modifier = Modifier.size(28.dp))
                        Spacer(Modifier.width(14.dp))
                        Column {
                            Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(subtitle, color = Color.White.copy(alpha = 0.75f), fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosisReportScreen(
    navController: NavController,
    patientId: String,
    patientName: String,
    appointmentId: String,
    viewModel: ReportViewModel
) {
    val isLoading by viewModel.isLoading
    val scroll = rememberScrollState()
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    var chiefComplaint by remember { mutableStateOf("") }
    var diagnosis by remember { mutableStateOf("") }
    var diagnosisNotes by remember { mutableStateOf("") }
    var treatmentPlan by remember { mutableStateOf("") }
    var medications by remember { mutableStateOf("") }
    var procedures by remember { mutableStateOf("") }
    var recoveryProgress by remember { mutableStateOf("Good") }
    var progressNotes by remember { mutableStateOf("") }
    var nextReviewDate by remember { mutableStateOf("") }
    var progressExpanded by remember { mutableStateOf(false) }

    Scaffold(topBar = { ReportTopBar("Diagnosis Report") { navController.popBackStack() } }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PatientBadge(patientName, appointmentId)

            // Q1
            SectionHeader(1, "What is wrong with the patient?")
            ReportTextField("Chief Complaint", chiefComplaint, { chiefComplaint = it })
            ReportTextField("Diagnosis / Condition", diagnosis, { diagnosis = it })
            ReportTextField("Clinical Notes", diagnosisNotes, { diagnosisNotes = it }, maxLines = 4)

            Divider()

            // Q2
            SectionHeader(2, "What treatment is ongoing?")
            ReportTextField("Treatment Plan", treatmentPlan, { treatmentPlan = it }, maxLines = 4)
            ReportTextField("Prescribed Medications", medications, { medications = it }, maxLines = 3)
            ReportTextField("Procedures / Interventions", procedures, { procedures = it })

            Divider()

            // Q3
            SectionHeader(3, "What is the patient's recovery progress?")
            ReportDropdown(
                "Recovery Progress", recoveryProgress,
                listOf("Poor", "Fair", "Good", "Excellent"),
                { recoveryProgress = it }, progressExpanded
            ) { progressExpanded = it }
            ReportTextField("Progress Notes", progressNotes, { progressNotes = it })
            ReportTextField("Next Review Date (e.g. 2025-12-01)", nextReviewDate, { nextReviewDate = it }, maxLines = 1)

            Spacer(Modifier.height(8.dp))
            ReportSubmitButton("Save Diagnosis Report", isLoading) {
                val report = DiagnosisReport(
                    meta = ReportMeta(generatedBy = uid, role = "Doctor"),
                    patientId = patientId,
                    patientName = patientName,
                    appointmentId = appointmentId,
                    chiefComplaint = chiefComplaint,
                    diagnosis = diagnosis,
                    diagnosisNotes = diagnosisNotes,
                    treatmentPlan = treatmentPlan,
                    medicationsPresribed = medications,
                    procedures = procedures,
                    recoveryProgress = recoveryProgress,
                    progressNotes = progressNotes,
                    nextReviewDate = nextReviewDate
                )
                viewModel.saveDiagnosisReport(report) { navController.popBackStack() }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreatmentSummaryReportScreen(
    navController: NavController,
    patientId: String,
    patientName: String,
    appointmentId: String,
    viewModel: ReportViewModel
) {
    val isLoading by viewModel.isLoading
    val scroll = rememberScrollState()
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    var visitDate by remember { mutableStateOf("") }
    var symptoms by remember { mutableStateOf("") }
    var interventions by remember { mutableStateOf("") }
    var patientResponse by remember { mutableStateOf("") }
    var followUpRequired by remember { mutableStateOf(false) }
    var followUpDate by remember { mutableStateOf("") }
    var followUpInstructions by remember { mutableStateOf("") }

    Scaffold(topBar = { ReportTopBar("Treatment Summary") { navController.popBackStack() } }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PatientBadge(patientName, appointmentId)
            ReportTextField("Visit Date (YYYY-MM-DD)", visitDate, { visitDate = it }, maxLines = 1)

            SectionHeader(1, "Presenting Symptoms")
            ReportTextField("Symptoms observed", symptoms, { symptoms = it }, maxLines = 3)

            SectionHeader(2, "Interventions Done")
            ReportTextField("Procedures, therapies, medications given", interventions, { interventions = it }, maxLines = 4)

            SectionHeader(3, "Patient Response")
            ReportTextField("How did the patient respond to treatment?", patientResponse, { patientResponse = it }, maxLines = 3)

            Divider()
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = followUpRequired,
                    onCheckedChange = { followUpRequired = it },
                    colors = CheckboxDefaults.colors(checkedColor = TealPrimary)
                )
                Text("Follow-up required", fontWeight = FontWeight.Medium)
            }
            if (followUpRequired) {
                ReportTextField("Follow-up Date", followUpDate, { followUpDate = it }, maxLines = 1)
                ReportTextField("Follow-up Instructions", followUpInstructions, { followUpInstructions = it })
            }

            Spacer(Modifier.height(8.dp))
            ReportSubmitButton("Save Treatment Summary", isLoading) {
                val report = TreatmentSummaryReport(
                    meta = ReportMeta(generatedBy = uid, role = "Doctor"),
                    patientId = patientId, patientName = patientName,
                    appointmentId = appointmentId, visitDate = visitDate,
                    presentingSymptoms = symptoms, interventionsDone = interventions,
                    patientResponse = patientResponse, followUpRequired = followUpRequired,
                    followUpDate = followUpDate, followUpInstructions = followUpInstructions
                )
                viewModel.saveTreatmentSummary(report) { navController.popBackStack() }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrescriptionReportScreen(
    navController: NavController,
    patientId: String,
    patientName: String,
    appointmentId: String,
    viewModel: ReportViewModel
) {
    val isLoading by viewModel.isLoading
    val scroll = rememberScrollState()
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    var instructions by remember { mutableStateOf("") }
    var validUntil by remember { mutableStateOf("") }
    var refills by remember { mutableStateOf("0") }
    val items = remember { mutableStateListOf(PrescriptionItem()) }

    Scaffold(topBar = { ReportTopBar("Write Prescription") { navController.popBackStack() } }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PatientBadge(patientName, appointmentId)

            Text("Prescription Items", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

            items.forEachIndexed { idx, item ->
                PrescriptionItemCard(
                    index = idx + 1,
                    item = item,
                    onUpdate = { items[idx] = it },
                    onRemove = if (items.size > 1) ({ items.removeAt(idx) }) else null
                )
            }

            OutlinedButton(
                onClick = { items.add(PrescriptionItem()) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TealPrimary)
            ) { Text("+ Add Another Medicine") }

            Divider()
            ReportTextField("General Instructions", instructions, { instructions = it })
            ReportTextField("Valid Until (YYYY-MM-DD)", validUntil, { validUntil = it }, maxLines = 1)
            ReportTextField("Refills Allowed (number)", refills, { refills = it }, maxLines = 1)

            Spacer(Modifier.height(8.dp))
            ReportSubmitButton("Save & Issue Prescription", isLoading) {
                val report = PrescriptionReport(
                    meta = ReportMeta(generatedBy = uid, role = "Doctor"),
                    patientId = patientId, patientName = patientName,
                    appointmentId = appointmentId,
                    prescriptionItems = items.toList(),
                    instructions = instructions,
                    validUntil = validUntil,
                    refillsAllowed = refills.toIntOrNull() ?: 0
                )
                viewModel.savePrescription(report) { navController.popBackStack() }
            }
        }
    }
}



@Composable
private fun PrescriptionItemCard(
    index: Int,
    item: PrescriptionItem,
    onUpdate: (PrescriptionItem) -> Unit,
    onRemove: (() -> Unit)?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = TealLight),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp)
    ) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Medicine #$index", fontWeight = FontWeight.SemiBold, color = TealDark)
                if (onRemove != null) {
                    TextButton(onClick = onRemove) { Text("Remove", color = Color.Red, fontSize = 12.sp) }
                }
            }
            ReportTextField("Medicine Name", item.medicineName, { onUpdate(item.copy(medicineName = it)) }, maxLines = 1)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ReportTextField("Dosage", item.dosage, { onUpdate(item.copy(dosage = it)) }, maxLines = 1, modifier = Modifier.weight(1f))
                ReportTextField("Frequency", item.frequency, { onUpdate(item.copy(frequency = it)) }, maxLines = 1, modifier = Modifier.weight(1f))
            }
            ReportTextField("Duration", item.duration, { onUpdate(item.copy(duration = it)) }, maxLines = 1)
            ReportTextField("Special Notes", item.notes, { onUpdate(item.copy(notes = it)) })
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowUpReportScreen(
    navController: NavController,
    patientId: String,
    patientName: String,
    originalDiagnosisId: String,
    viewModel: ReportViewModel
) {
    val isLoading by viewModel.isLoading
    val scroll = rememberScrollState()
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    var currentStatus by remember { mutableStateOf("Stable") }
    var statusExpanded by remember { mutableStateOf(false) }
    var symptomChanges by remember { mutableStateOf("") }
    var compliance by remember { mutableStateOf("Fully compliant") }
    var complianceExpanded by remember { mutableStateOf(false) }
    var nextSteps by remember { mutableStateOf("") }
    var discharged by remember { mutableStateOf(false) }

    Scaffold(topBar = { ReportTopBar("Follow-Up Report") { navController.popBackStack() } }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PatientBadge(patientName, originalDiagnosisId)

            SectionHeader(3, "What is the patient's recovery progress?")

            ReportDropdown(
                "Current Recovery Status", currentStatus,
                listOf("Deteriorating", "Stable", "Improving", "Recovered"),
                { currentStatus = it }, statusExpanded
            ) { statusExpanded = it }

            ReportTextField("Symptom Changes since last visit", symptomChanges, { symptomChanges = it }, maxLines = 4)

            ReportDropdown(
                "Treatment Compliance", compliance,
                listOf("Non-compliant", "Partial", "Fully compliant"),
                { compliance = it }, complianceExpanded
            ) { complianceExpanded = it }

            ReportTextField("Next Steps / Recommendations", nextSteps, { nextSteps = it }, maxLines = 4)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = discharged,
                    onCheckedChange = { discharged = it },
                    colors = CheckboxDefaults.colors(checkedColor = TealPrimary)
                )
                Text("Mark patient as discharged", fontWeight = FontWeight.Medium)
            }

            Spacer(Modifier.height(8.dp))
            ReportSubmitButton("Save Follow-Up Report", isLoading) {
                val report = FollowUpReport(
                    meta = ReportMeta(generatedBy = uid, role = "Doctor"),
                    patientId = patientId, patientName = patientName,
                    originalDiagnosisId = originalDiagnosisId,
                    currentRecoveryStatus = currentStatus,
                    symptomChanges = symptomChanges,
                    complianceWithTreatment = compliance,
                    nextSteps = nextSteps,
                    discharged = discharged
                )
                viewModel.saveFollowUpReport(report) { navController.popBackStack() }
            }
        }
    }
}


@Composable
private fun PatientBadge(patientName: String, ref: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = TealLight),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Person, null, tint = TealPrimary, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(10.dp))
            Column {
                Text(patientName, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text("Ref: $ref", fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}