package com.example.smartmedicalsystem.ui.theme.screens.ReportsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.smartmedicalsystem.models.TrendingDiseaseReport
import com.example.smartmedicalsystem.ui.theme.component.PeerReviewActionRow
import com.example.smartmedicalsystem.ui.theme.component.ReportDropdown
import com.example.smartmedicalsystem.ui.theme.component.ReportSubmitButton
import com.example.smartmedicalsystem.ui.theme.component.ReportTextField
import com.example.smartmedicalsystem.ui.theme.component.ReportTopBar
import com.example.smartmedicalsystem.ui.theme.component.TealDark
import com.example.smartmedicalsystem.ui.theme.component.TealPrimary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingDiseaseReportScreen(
    navController: NavController,
    department: String,
    doctorId: String,
    doctorName: String,
    viewModel: ReportViewModel
) {
    val isLoading by viewModel.isLoading
    val scroll = rememberScrollState()

    var diseaseName by remember { mutableStateOf("") }
    var caseCountStr by remember { mutableStateOf("") }
    var severity by remember { mutableStateOf("Moderate") }
    var severityExpanded by remember { mutableStateOf(false) }
    var ageGroup by remember { mutableStateOf("") }
    var symptoms by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Scaffold(topBar = { ReportTopBar("Trending Disease Report") { navController.popBackStack() } }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .verticalScroll(scroll)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = TealDark),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocalHospital, null, tint = Color.White)
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text("Department: $department", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("Submitted by: $doctorName", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                }
            }

            Text(
                "This report will be sent to all doctors in your department for peer review.",
                fontSize = 12.sp, color = Color.Gray
            )

            ReportTextField("Disease / Condition Name", diseaseName, { diseaseName = it }, maxLines = 1)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                ReportTextField(
                    label = "Number of Cases",
                    value = caseCountStr,
                    onValueChange = { caseCountStr = it },
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )

                Box(modifier = Modifier.weight(1f)) {

                    ReportDropdown(
                        "Severity",
                        severity,
                        listOf("Low", "Moderate", "High", "Critical"),
                        { severity = it },
                        severityExpanded,
                        { severityExpanded = it }
                    )
                }
            }

            ReportTextField("Affected Age Group (e.g. 18–35)", ageGroup, { ageGroup = it }, maxLines = 1)
            ReportTextField("Common Symptoms", symptoms, { symptoms = it }, maxLines = 4)
            ReportTextField("Additional Notes / Observations", notes, { notes = it }, maxLines = 4)

            Spacer(Modifier.height(8.dp))
            ReportSubmitButton("Submit for Peer Review", isLoading) {
                val report = TrendingDiseaseReport(
                    department = department,
                    submittedBy = doctorId,
                    submittedByName = doctorName,
                    diseaseName = diseaseName,
                    caseCount = caseCountStr.toIntOrNull() ?: 0,
                    severity = severity,
                    affectedAgeGroup = ageGroup,
                    symptoms = symptoms,
                    notes = notes
                )
                viewModel.submitTrendingDiseaseReport(report) { navController.popBackStack() }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepartmentTrendingDiseasesScreen(
    navController: NavController,
    department: String,
    reviewerDoctorId: String,
    reviewerDoctorName: String,
    viewModel: ReportViewModel
) {
    val reports by viewModel.trendingDiseases

    LaunchedEffect(department) { viewModel.listenTrendingDiseases(department) }

    var showAddDialog by remember { mutableStateOf<String?>(null) }   // reportId when open
    var addedDiseaseName by remember { mutableStateOf("") }
    var reviewComment by remember { mutableStateOf("") }

    Scaffold(topBar = { ReportTopBar("$department — Trending Diseases") { navController.popBackStack() } }) { pad ->
        if (reports.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(pad), contentAlignment = Alignment.Center) {
                Text("No trending disease reports for this department yet.", color = Color.Gray)
            }
        } else {
            LazyColumn(
                Modifier.fillMaxSize().padding(pad),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reports.size) { i ->
                    val report = reports[i]
                    TrendingDiseaseCard(
                        report = report,
                        currentDoctorId = reviewerDoctorId,
                        onApprove = {
                            viewModel.reviewTrendingDisease(
                                department, report.reportId,
                                reviewerDoctorId, reviewerDoctorName,
                                "approved", "Approved"
                            ) {}
                        },
                        onDecline = {
                            viewModel.reviewTrendingDisease(
                                department, report.reportId,
                                reviewerDoctorId, reviewerDoctorName,
                                "declined", "Declined"
                            ) {}
                        },
                        onAddDisease = { showAddDialog = report.reportId }
                    )
                }
            }
        }
    }

    // Add disease dialog
    if (showAddDialog != null) {
        AlertDialog(
            onDismissRequest = { showAddDialog = null },
            title = { Text("Add Trending Disease") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ReportTextField("Disease Name", addedDiseaseName, { addedDiseaseName = it }, maxLines = 1)
                    ReportTextField("Your Comment", reviewComment, { reviewComment = it })
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val rid = showAddDialog ?: return@TextButton
                    viewModel.reviewTrendingDisease(
                        department, rid,
                        reviewerDoctorId, reviewerDoctorName,
                        "added_disease", reviewComment, addedDiseaseName
                    ) {}
                    showAddDialog = null
                    addedDiseaseName = ""
                    reviewComment = ""
                }) { Text("Submit", color = TealPrimary) }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = null }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun TrendingDiseaseCard(
    report: TrendingDiseaseReport,
    currentDoctorId: String,
    onApprove: () -> Unit,
    onDecline: () -> Unit,
    onAddDisease: () -> Unit
) {
    val statusColor = when (report.status) {
        "approved" -> Color(0xFF2E7D32)
        "declined" -> Color(0xFFC62828)
        else -> Color(0xFFF57F17)
    }
    val severityColor = when (report.severity) {
        "Critical" -> Color(0xFFB71C1C)
        "High" -> Color(0xFFE53935)
        "Moderate" -> Color(0xFFF57C00)
        else -> Color(0xFF388E3C)
    }

    val alreadyReviewed = report.peerReviews.any { it.reviewerId == currentDoctorId }
    val isOwn = report.submittedBy == currentDoctorId

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(report.diseaseName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Chip(report.severity, severityColor)
                    Chip(report.status.replaceFirstChar { it.uppercase() }, statusColor)
                }
            }

            Text("Submitted by: ${report.submittedByName}", fontSize = 12.sp, color = Color.Gray)
            Text("Cases: ${report.caseCount}  •  Age group: ${report.affectedAgeGroup}", fontSize = 12.sp)
            Text("Symptoms: ${report.symptoms}", fontSize = 12.sp, color = Color.DarkGray)

            if (report.addedDiseases.isNotEmpty()) {
                Text("Also reported by peers:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TealDark)
                report.addedDiseases.forEach { Text("• $it", fontSize = 12.sp, color = TealDark) }
            }

            if (report.peerReviews.isNotEmpty()) {
                Text("Reviews (${report.peerReviews.size}):", fontSize = 12.sp, color = Color.Gray)
                report.peerReviews.forEach { rev ->
                    Text(
                        "  ${rev.reviewerName}: ${rev.action}${if (rev.addedDisease.isNotBlank()) " (+${rev.addedDisease})" else ""}",
                        fontSize = 11.sp, color = Color.Gray
                    )
                }
            }

            Divider()

            if (!isOwn && !alreadyReviewed && report.status == "pending") {
                PeerReviewActionRow(onApprove, onDecline, onAddDisease)
            } else if (alreadyReviewed) {
                Text("✓ You have already reviewed this report", fontSize = 12.sp, color = TealPrimary)
            } else if (isOwn) {
                Text("This is your report — awaiting peer review", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
private fun Chip(label: String, color: Color) {
    Box(
        Modifier
            .background(color.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(label, fontSize = 11.sp, color = color, fontWeight = FontWeight.SemiBold)
    }
}