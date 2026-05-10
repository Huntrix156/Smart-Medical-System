package com.example.smartmedicalsystem.ui.theme.screens.ReportsScreen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import com.example.smartmedicalsystem.models.HospitalManagementReport
import com.example.smartmedicalsystem.models.SystemAnalyticsReport
import com.example.smartmedicalsystem.ui.theme.component.OrangeAccent
import com.example.smartmedicalsystem.ui.theme.component.ReportTopBar
import com.example.smartmedicalsystem.ui.theme.component.SectionHeader
import com.example.smartmedicalsystem.ui.theme.component.SimpleBarChart
import com.example.smartmedicalsystem.ui.theme.component.StatChip
import com.example.smartmedicalsystem.ui.theme.component.SurfaceGray
import com.example.smartmedicalsystem.ui.theme.component.TealDark
import com.example.smartmedicalsystem.ui.theme.component.TealLight
import com.example.smartmedicalsystem.ui.theme.component.TealPrimary
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.platform.LocalLocale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminReportHubScreen(navController: NavController, viewModel: ReportViewModel) {
    Scaffold(topBar = { ReportTopBar("Admin Reports") { navController.popBackStack() } }) { pad ->
        Column(
            Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Hospital & System Reports", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Auto-generated and on-demand reports", fontSize = 13.sp, color = Color.Gray)
            Spacer(Modifier.height(4.dp))

            SectionHeader(1, "Hospital Management Reports")
            Text(
                "Covers: patient activity, busiest department, doctor workload",
                fontSize = 12.sp, color = Color.Gray
            )

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PeriodButton("Daily", TealPrimary) { navController.navigate("admin_hospital_report/daily") }
                PeriodButton("Weekly", TealDark) { navController.navigate("admin_hospital_report/weekly") }
                PeriodButton("Monthly", OrangeAccent) { navController.navigate("admin_hospital_report/monthly") }
            }

            Divider()

            SectionHeader(2, "System Analytics Reports")
            Text(
                "Covers: user growth, appointment patterns",
                fontSize = 12.sp, color = Color.Gray
            )

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PeriodButton("Daily", TealPrimary) { navController.navigate("admin_analytics_report/daily") }
                PeriodButton("Weekly", TealDark) { navController.navigate("admin_analytics_report/weekly") }
                PeriodButton("Monthly", OrangeAccent) { navController.navigate("admin_analytics_report/monthly") }
            }
        }
    }
}

@Composable
private fun PeriodButton(label: String, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(12.dp)
    ) { Text(label) }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HospitalManagementReportScreen(
    navController: NavController,
    period: String,
    viewModel: ReportViewModel
) {
    val report by viewModel.hospitalReport
    val isLoading by viewModel.isLoading
    val scroll = rememberScrollState()
    val todayLabel = SimpleDateFormat("yyyy-MM-dd", LocalLocale.current.platformLocale).format(Date())

    LaunchedEffect(period) {
        viewModel.listenLatestHospitalReport(period)
    }

    Scaffold(
        topBar = {
            ReportTopBar(
                "${period.replaceFirstChar { it.uppercase() }} Hospital Report"
            ) { navController.popBackStack() }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.generateHospitalReport(period, todayLabel) },
                containerColor = TealPrimary,
                icon = { Icon(Icons.Default.Refresh, null, tint = Color.White) },
                text = { Text("Generate Now", color = Color.White) }
            )
        }
    ) { pad ->
        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(pad), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TealPrimary)
            }
        } else if (report == null) {
            Box(Modifier.fillMaxSize().padding(pad), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.BarChart, null, modifier = Modifier.size(60.dp), tint = Color.LightGray)
                    Spacer(Modifier.height(12.dp))
                    Text("No $period report found.", color = Color.Gray)
                    Text("Tap 'Generate Now' to create one.", fontSize = 12.sp, color = Color.LightGray)
                }
            }
        } else {
            HospitalReportContent(report = report!! as HospitalManagementReport, scroll = scroll, pad = pad)
        }
    }
}

@Composable
private fun HospitalReportContent(
    report: HospitalManagementReport,
    scroll: ScrollState,
    pad: PaddingValues
) {
    Column(
        Modifier
            .padding(pad)
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ReportHeaderBanner("Hospital Management Report", report.reportDate, report.period)

        SectionHeader(1, "How many patients used the system today?")
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatChip("Total Patients", "${report.totalPatientsToday}", TealPrimary)
            StatChip("Active", "${report.activePatients}", Color(0xFF2E7D32))
            StatChip("New", "${report.newRegistrations}", OrangeAccent)
        }

        SectionHeader(2, "Which department was the busiest today?")
        BusiestDeptCard(report.busiestDepartment, report.departmentStats)

        if (report.departmentStats.isNotEmpty()) {
            val chartData = report.departmentStats.entries
                .sortedByDescending { it.value }
                .take(6)
                .map { it.key.take(8) to it.value }
            SimpleBarChart(
                data = chartData,
                color = TealPrimary,
                modifier = Modifier.fillMaxWidth()
            )
        }

        SectionHeader(3, "Doctor Workload Analysis")
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatChip("Total Appts", "${report.totalAppointments}", TealPrimary)
            StatChip("Completed", "${report.completedAppointments}", Color(0xFF2E7D32))
            StatChip("Pending", "${report.pendingAppointments}", Color(0xFFF57F17))
            StatChip("Cancelled", "${report.cancelledAppointments}", Color(0xFFC62828))
        }

        if (report.doctorWorkload.isNotEmpty()) {
            Text("Per-Doctor Breakdown", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            report.doctorWorkload.sortedByDescending { it.appointmentsHandled }.forEach { dw ->
                DoctorWorkloadRow(
                    name = dw.doctorName.ifBlank { "Doctor" },
                    dept = dw.department,
                    count = dw.appointmentsHandled
                )
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun BusiestDeptCard(dept: String, stats: Map<String, Int>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = TealLight),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, null, tint = OrangeAccent, modifier = Modifier.size(32.dp))
            Spacer(Modifier.width(12.dp))
            Column {
                Text("Busiest Department", fontSize = 12.sp, color = Color.Gray)
                Text(dept, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TealDark)
                Text("${stats[dept] ?: 0} appointments", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
private fun DoctorWorkloadRow(name: String, dept: String, count: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray)
    ) {
        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Person, null, tint = TealPrimary)
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                Text(name, fontWeight = FontWeight.Medium)
                Text(dept, fontSize = 11.sp, color = Color.Gray)
            }
            Text("$count appts", fontWeight = FontWeight.Bold, color = TealPrimary)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemAnalyticsReportScreen(
    navController: NavController,
    period: String,
    viewModel: ReportViewModel
) {
    val report by viewModel.systemAnalytics
    val isLoading by viewModel.isLoading
    val scroll = rememberScrollState()
    val todayLabel = SimpleDateFormat("yyyy-MM-dd", LocalLocale.current.platformLocale).format(Date())

    LaunchedEffect(period) { viewModel.listenLatestSystemAnalytics(period) }

    Scaffold(
        topBar = {
            ReportTopBar(
                "${period.replaceFirstChar { it.uppercase() }} System Analytics"
            ) { navController.popBackStack() }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.generateSystemAnalytics(period, todayLabel) },
                containerColor = TealDark,
                icon = { Icon(Icons.Default.Analytics, null, tint = Color.White) },
                text = { Text("Refresh Analytics", color = Color.White) }
            )
        }
    ) { pad ->
        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(pad), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = TealPrimary)
            }
        } else if (report == null) {
            Box(Modifier.fillMaxSize().padding(pad), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Analytics, null, modifier = Modifier.size(60.dp), tint = Color.LightGray)
                    Spacer(Modifier.height(12.dp))
                    Text("No analytics data yet.", color = Color.Gray)
                }
            }
        } else {
            AnalyticsContent(report = report!! as SystemAnalyticsReport, scroll = scroll, pad = pad)
        }
    }
}

@Composable
private fun AnalyticsContent(
    report: SystemAnalyticsReport,
    scroll: ScrollState,
    pad: PaddingValues
) {
    Column(
        Modifier
            .padding(pad)
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ReportHeaderBanner("System Analytics", report.periodLabel, report.period)

        SectionHeader(1, "User Growth")
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StatChip("Total Users", "${report.totalUsers}", TealPrimary)
            StatChip("New This Period", "${report.newUsersThisPeriod}", OrangeAccent)
            StatChip("Growth Rate", "${String.format("%.1f", report.growthRate)}%", Color(0xFF1565C0))
        }

        if (report.userGrowth.isNotEmpty()) {
            Text("Users by Period", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            val chartData = report.userGrowth.map { it.label.take(6) to it.total }
            SimpleBarChart(chartData, Color(0xFF1565C0), Modifier.fillMaxWidth())
        }

        Divider()

        SectionHeader(2, "Appointment Patterns")
        if (report.peakDay.isNotBlank())
            Text("Peak Day: ${report.peakDay}  •  Peak Hour: ${report.peakHour}", fontSize = 13.sp, color = TealDark)
        Text(
            "Avg daily appointments: ${String.format("%.1f", report.avgDailyAppointments)}",
            fontSize = 13.sp, color = Color.Gray
        )

        if (report.appointmentPatterns.isNotEmpty()) {
            val chartData = report.appointmentPatterns.map { it.label.take(6) to it.count }
            SimpleBarChart(chartData, OrangeAccent, Modifier.fillMaxWidth())

            Text("Completion vs Cancellations", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            report.appointmentPatterns.forEach { p ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(p.label, fontSize = 12.sp)
                    Text("✓ ${p.completed}  ✗ ${p.cancelled}", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun ReportHeaderBanner(title: String, date: String, period: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(TealPrimary, RoundedCornerShape(16.dp))
            .padding(18.dp)
    ) {
        Column {
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Period: ${period.replaceFirstChar { it.uppercase() }}  •  $date",
                color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
        }
    }
}