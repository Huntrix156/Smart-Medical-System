package com.example.smartmedicalsystem.ui.theme.screens.screen

//package com.example.healthapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartmedicalsystem.models.OnboardingPage
import com.example.smartmedicalsystem.navigation.ROUTE_LOGIN
import kotlinx.coroutines.launch

@Composable
fun OnboardingSlider(navController: NavController,
    onFinish: () -> Unit = {},
//                     onSkipPressed: () -> Unit = {} // 1. Define the name here

) {

    val pages = listOf(
        OnboardingPage(
            "Connect with Doctors",
            "Book appointments, receive prescriptions, and get real-time medical guidance.",
            "⚕"
        ),
        OnboardingPage(
            "Track Your Health",
            "Monitor your medical records, vitals, and treatment progress in one place.",
            "❤"
        ),
        OnboardingPage(
            "Get Medicine Easily",
            "Order medicines, get refill alerts, and manage prescriptions effortlessly.",
            "💊"
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0F14))
            .statusBarsPadding() // 1. Prevents top overflow/overlap
    ) {

        // Skip Button
//        Text(
//            text = "Skip",
//            color = Color(0xFF8A9BA8),
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .padding(top = 50.dp, end = 20.dp)
//                .clickable(
//                    enabled = true,
//                    onClickLabel = "Skip onboarding", // For accessibility
//                    role = Role.Button,              // Helps screen readers
//                    onClick = {
////                        onSkipPressed() // Call a function or navigate
//                        // This line performs the actual redirection
//                        navController.navigate(ROUTE_LOGIN)
//                    }
//                )
//        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            // This pushes everything to the bottom but keeps them grouped
            verticalArrangement = Arrangement.Bottom
        ) {

            // Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->

                val item = pages[page]

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 20.dp),// Add vertical padding
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    // Icon Box
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(
                                Color(0xFF101C2C),
                                RoundedCornerShape(25.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.icon,
                            fontSize = 40.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = item.title,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = item.description,
                        color = Color(0xFF8A9BA8),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            // Dots Indicator
            Row(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pages.size) { index ->
                    Dot(isActive = pagerState.currentPage == index)
                    Spacer(modifier = Modifier.width(6.dp))
                }
            }

            // Button
            Button(
                onClick = {
                    if (pagerState.currentPage == pages.lastIndex) {
                        onFinish()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(55.dp)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A8CFF)
                )
            ) {
                Text(
                    text = if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Continue",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun Dot(isActive: Boolean) {
    Box(
        modifier = Modifier
            .height(8.dp)
            .width(if (isActive) 20.dp else 8.dp)
            .background(
                if (isActive) Color(0xFF4A8CFF) else Color(0xFF2C3E50),
                shape = RoundedCornerShape(50)
            )
    )
}