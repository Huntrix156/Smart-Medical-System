package com.example.smartmedicalsystem.ui.theme.screens.GenerateReport.screen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartmedicalsystem.navigation.*

private val HospitalTeal = Color(0xFF00604E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateReportScreen(navController: NavController
//                         ,onLogout: () -> Unit
) {




    Scaffold(
        topBar = {
            TopAppBar(

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },

                title = {
                    Text(
                        "Report Management",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = HospitalTeal
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Reports Hub",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                GenerateReportMenuCard("Admin Analytics,", "Create new entry", Modifier.weight(1f)) {
                    navController.navigate(ROUTE_ADMIN_ANALYTICS_REPORT)
                }
                GenerateReportMenuCard("Admin Hospital Reports", "Set alerts", Modifier.weight(1f)) {
                    navController.navigate(ROUTE_ADMIN_HOSPITAL_REPORT)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                GenerateReportMenuCard("Department Trending Diseases", "View stock", Modifier.weight(1f)) {
                    navController.navigate(ROUTE_DEPT_TRENDING_DISEASES)
                }
                GenerateReportMenuCard("Trending Diseases", "Search meds", Modifier.weight(1f)) {
                    navController.navigate(ROUTE_REPORT_TRENDING_DISEASE)
                }
            }
        }
    }
}

@Composable
fun GenerateReportMenuCard(title: String, subtitle: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier.height(140.dp),
        colors = CardDefaults.cardColors(containerColor = HospitalTeal),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
        }
    }
}


