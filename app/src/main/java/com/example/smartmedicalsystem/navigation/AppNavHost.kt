package com.example.smartmedicalsystem.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartmedical.screens.AdminDashboard
import com.example.smartmedical.screens.DoctorDashboard
import com.example.smartmedical.screens.PatientDashboard
import com.example.smartmedicalsystem.models.Medicine
import com.example.smartmedicalsystem.ui.theme.screens.DashboardScreen
import com.example.smartmedicalsystem.ui.theme.screens.Inventory.InventoryScreen
import com.example.smartmedicalsystem.ui.theme.screens.LoginScreen
import com.example.smartmedicalsystem.ui.theme.screens.RegisterScreen
import com.example.smartmedicalsystem.ui.theme.screens.screen.AddMedicine.screen.AddMedicationScreen
import com.example.smartmedicalsystem.ui.theme.screens.screen.ForgotPasswordScreen
import com.example.smartmedicalsystem.ui.theme.screens.screen.MedicationScreen
import com.example.smartmedicalsystem.ui.theme.screens.screen.OnboardingSlider
import java.time.LocalDate


//object Routes {
//    const val LOGIN = "login"
//    const val PATIENT = "patient_dashboard"
//    const val DOCTOR = "doctor_dashboard"
//    const val ADMIN = "admin_dashboard"
//
//}

object Routes {
    const val PATIENT = "patient_dashboard"
    const val DOCTOR = "doctor_dashboard"
    const val ADMIN = "admin_dashboard"
}
sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
}


fun NavHostController.logout() {
    navigate(Screen.Login.route) {
        popUpTo(0)
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(),
               startDestination:String =  Screen.Onboarding.route) {


    val sampleMedicines = listOf(
        Medicine(
            name = "Paracetamol",
            quantity = 20,
            stock = 100,
            minStock = 10,
            // Wrap the string in LocalDate.parse()
            expiryDate = LocalDate.parse("2026-12-01")
        ),
        Medicine(
            name = "Amoxicillin",
            quantity = 15,
            stock = 50,
            minStock = 5,
            expiryDate = LocalDate.parse("2027-03-15")
        )
    )// ✅ List properly closed before NavHost
    NavHost(
        navController = navController,
        startDestination = startDestination

    ) {
        composable(ROUTE_REGISTER) { RegisterScreen(navController) }
//        composable(ROUTE_LOGIN) {
        composable(Screen.Login.route) {
            LoginScreen(navController, onRoleSelected = { role ->
                when (role) {
                    "Patient" -> navController.navigate(Routes.PATIENT)
                    "Doctor" -> navController.navigate(Routes.DOCTOR)
                    "Admin" -> navController.navigate(Routes.ADMIN)
                }
            })
        }
        composable(Routes.PATIENT) {
            PatientDashboard(
                navController,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.DOCTOR) {
            DoctorDashboard(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.ADMIN) {
            AdminDashboard(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(ROUTE_MAIN_DASHBOARD) { DashboardScreen(navController) }

        composable(ROUTE_FORGOT_PASSWORD) { ForgotPasswordScreen(navController) }


        composable(ROUTE_MEDICATION_SCREEN) {
            MedicationScreen(navController)
        }
        // Onboarding Screen
//        composable(Screen.Onboarding.route) {
//            OnboardingSlider(
//                onFinish = {
//                    navController.navigate(Screen.Login.route) {
//                        popUpTo(Screen.Onboarding.route) {
//                            inclusive = true
//                        }
//                    }
//                }
//            )
//        }
        composable(Screen.Onboarding.route) {
            OnboardingSlider(
                navController = navController,
                // This handles the "Skip" text click
//                onSkipPressed = {
//                    navController.navigate(Screen.Login.route) {
//                        popUpTo(Screen.Onboarding.route) { inclusive = true }
//                    }
//                },
                // This handles the "Get Started" button click on the last page
                onFinish = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(ROUTE_INVENTORY_SCREEN) {
            InventoryScreen(
                navController = navController,   // ✔️
                medicines = sampleMedicines      // ✔️
            )
        }
        composable(ROUTE_ADD_MEDICATION) { AddMedicationScreen(navController) }
    }

}


