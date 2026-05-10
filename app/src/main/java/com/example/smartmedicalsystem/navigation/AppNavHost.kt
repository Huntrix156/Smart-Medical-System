//package com.example.smartmedicalsystem.navigation
//
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.runtime.Composable
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.NavType
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.navArgument
//
//import com.example.smartmedicalsystem.data.DashboardStatsViewModel
//import com.example.smartmedicalsystem.models.medication.Medicine
//import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.AdminDashboard
//import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.DoctorDashboard
//import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.PatientDashboard
//import com.example.smartmedicalsystem.ui.theme.screens.LoginScreen
//import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.DashboardScreen
//import com.example.smartmedicalsystem.ui.theme.screens.Inventory.InventoryScreen
//import com.example.smartmedicalsystem.ui.theme.screens.Profile.screen.ProfileScreen
//import com.example.smartmedicalsystem.ui.theme.screens.Settings.screen.SettingsScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.service.Reminder.screen.ReminderScreen
//import com.example.smartmedicalsystem.ui.theme.screens.Appointments.UpcomingAppointmentsScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.ForgotPasswordScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen.MedicationScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.Onboarding.screen.OnboardingSlider
//import java.time.LocalDate
//import com.example.smartmedicalsystem.ui.theme.Admin.AdminAddDoctorScreen
//import com.example.smartmedicalsystem.ui.theme.screens.Appointments.AdminAppointmentsScreen
//import com.example.smartmedicalsystem.ui.theme.screens.Appointments.DoctorAppointmentsScreen
//import com.example.smartmedicalsystem.ui.theme.screens.GenerateReport.screen.GenerateReportScreen
//import com.example.smartmedicalsystem.ui.theme.screens.RegisterScreen
//import com.example.smartmedicalsystem.ui.theme.screens.medicine.screen.MedicineListScreen
//import com.example.smartmedicalsystem.ui.theme.screens.medicine.screen.UpdateMedicineScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.EmergencySOSScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen.AddMedicineScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen.WritePrescriptionScreen
//
//
//sealed class Screen(val route: String) {
//    object Onboarding : Screen("onboarding")
//    object Login : Screen("login")
//}
//
//fun NavHostController.logout() {
//    navigate(ROUTE_LOGIN) {
//        popUpTo(0)
//    }
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun AppNavHost(
//    navController: NavHostController = rememberNavController(),
//    startDestination: String = Screen.Onboarding.route
//) {
//
//    val sampleMedicines = listOf(
//        Medicine(
//            name = "Paracetamol",
//            quantity = 20,
//            stock = 100,
//            minStock = 10,
//            expiryDate = LocalDate.parse("2026-12-01")
//        ),
//        Medicine(
//            name = "Amoxicillin",
//            quantity = 15,
//            stock = 50,
//            minStock = 5,
//            expiryDate = LocalDate.parse("2027-03-15")
//        )
//    )
//
//
//    val statsViewModel: DashboardStatsViewModel = viewModel()
//
//    NavHost(
//        navController = navController,
//        startDestination = startDestination
//    ) {
//
//        composable(Screen.Onboarding.route) {
//            OnboardingSlider(
//                navController = navController,
//                onFinish = {
//                    navController.navigate(ROUTE_LOGIN) {
//                        popUpTo(Screen.Onboarding.route) { inclusive = true }
//                    }
//                }
//            )
//        }
//
//
//        composable(ROUTE_REGISTER) {
//            RegisterScreen(navController)
//        }
//
//        composable(ROUTE_LOGIN) {
//            LoginScreen(
//                navController = navController,
//                onRoleSelected = { role, username ->
//                    val encodedUsername = java.net.URLEncoder.encode(username, "UTF-8")
//                    val destination = when (role) {
//                        "Patient" -> "patient_dashboard/$encodedUsername"
//                        "Doctor"  -> "doctor_dashboard/$encodedUsername"
//                        "Admin"   -> "admin_dashboard/$encodedUsername"
//                        else      -> "patient_dashboard/$encodedUsername"
//                    }
//                    navController.navigate(destination) {
//                        popUpTo(ROUTE_LOGIN) { inclusive = true }
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//
//        composable(ROUTE_FORGOT_PASSWORD) {
//            ForgotPasswordScreen(navController)
//        }
//
//
//        composable("patient_dashboard/{username}") { backStackEntry ->
//            val username = java.net.URLDecoder.decode(
//                backStackEntry.arguments?.getString("username") ?: "", "UTF-8"
//            )
//            PatientDashboard(
//                navController = navController,
//                username = username,
//                viewModel = viewModel(),
//                statsViewModel = statsViewModel,
//                onLogout = {
//                    navController.navigate(ROUTE_LOGIN) {
//                        popUpTo(0)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//
//        composable("doctor_dashboard/{username}") { backStackEntry ->
//            val username = java.net.URLDecoder.decode(
//                backStackEntry.arguments?.getString("username") ?: "", "UTF-8"
//            )
//            DoctorDashboard(
//                navController = navController,
//                username = username,
//                viewModel = viewModel(),
//                statsViewModel = statsViewModel,
//                onLogout = {
//                    navController.navigate(ROUTE_LOGIN) {
//                        popUpTo(0)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//
//        composable("admin_dashboard/{username}") { backStackEntry ->
//            val username = java.net.URLDecoder.decode(
//                backStackEntry.arguments?.getString("username") ?: "", "UTF-8"
//            )
//            AdminDashboard(
//                navController = navController,
//                username = username,
//                viewModel = viewModel(),
//                statsViewModel = statsViewModel,
//                onLogout = {
//                    navController.navigate(ROUTE_LOGIN) {
//                        popUpTo(0)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//
//        composable(ROUTE_MAIN_DASHBOARD) {
//            DashboardScreen(navController)
//        }
//
//        composable(ROUTE_SETTINGS) {
//            SettingsScreen(navController)
//        }
//
//        composable(ROUTE_PROFILE) {
//            ProfileScreen(
//                navController = navController,
//                userId = "userId"  // TODO: pass real Firebase userId from auth
//            )
//        }
//
//        composable(ROUTE_REMINDER) {
//            ReminderScreen(navController)
//        }
//
//        composable(ROUTE_EMERGENCY_SOS) {
//            EmergencySOSScreen(context = navController.context)
//        }
//
//        composable(ROUTE_MEDICATION_SCREEN) {
//            MedicationScreen(navController= navController,onLogout = {})
//        }
//
//
//
//        composable(ROUTE_MEDICINE_LIST) {
//            MedicineListScreen(navController)
//        }
//        composable(ROUTE_GENERATE_REPORT) {
//            GenerateReportScreen(navController
////                , onLogout = {}
//            )
//        }
//
//        composable(ROUTE_ADD_MEDICINE) {
//            AddMedicineScreen(navController)
//        }
//        composable(ROUTE_ADMIN_ADD_DOCTOR) {
//            AdminAddDoctorScreen(navController)
//        }
//        composable(ROUTE_WRITE_PRESCRIPTION) {
//            WritePrescriptionScreen(navController)
//        }
//
//        composable(
//            route = ROUTE_UPDATE_MEDICATION,
//            arguments = listOf(navArgument("medicineId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val medicineId = backStackEntry.arguments?.getString("medicineId")!!
//            UpdateMedicineScreen(
//                navController = navController,
//                medicineId = medicineId
//            )
//        }
//
//        composable(ROUTE_INVENTORY_SCREEN) {
//            InventoryScreen(
//                navController = navController,
//                medicines = sampleMedicines
//            )
//        }
//
//        composable(ROUTE_UPCOMING_APPOINTMENT) {
//            UpcomingAppointmentsScreen(navController)
//        }
//        composable(ROUTE_ADMIN_APPOINTMENTS) {
//            AdminAppointmentsScreen(navController)
//        }
//        composable(ROUTE_DOCTOR_APPOINTMENTS) {
//            DoctorAppointmentsScreen(navController)
//        }
//    }
//}

package com.example.smartmedicalsystem.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.smartmedicalsystem.data.DashboardStatsViewModel
import com.example.smartmedicalsystem.models.medication.Medicine
import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.AdminDashboard
import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.DoctorDashboard
import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.PatientDashboard
import com.example.smartmedicalsystem.ui.theme.screens.LoginScreen
import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.DashboardScreen
import com.example.smartmedicalsystem.ui.theme.screens.Inventory.InventoryScreen
import com.example.smartmedicalsystem.ui.theme.screens.Profile.screen.ProfileScreen
import com.example.smartmedicalsystem.ui.theme.screens.Settings.screen.SettingsScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.service.Reminder.screen.ReminderScreen
import com.example.smartmedicalsystem.ui.theme.screens.Appointments.UpcomingAppointmentsScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.ForgotPasswordScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen.MedicationScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.Onboarding.screen.OnboardingSlider
import java.time.LocalDate
import com.example.smartmedicalsystem.ui.theme.Admin.AdminAddDoctorScreen
import com.example.smartmedicalsystem.ui.theme.screens.Appointments.AdminAppointmentsScreen
import com.example.smartmedicalsystem.ui.theme.screens.Appointments.DoctorAppointmentsScreen
import com.example.smartmedicalsystem.ui.theme.screens.GenerateReport.screen.GenerateReportScreen
import com.example.smartmedicalsystem.ui.theme.screens.RegisterScreen
import com.example.smartmedicalsystem.ui.theme.screens.medicine.screen.MedicineListScreen
import com.example.smartmedicalsystem.ui.theme.screens.medicine.screen.UpdateMedicineScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.EmergencySOSScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen.AddMedicineScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen.WritePrescriptionScreen
import com.google.firebase.auth.FirebaseAuth

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
}

fun NavHostController.logout() {
    navigate(ROUTE_LOGIN) {
        popUpTo(0)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Onboarding.route
) {

    val sampleMedicines = listOf(
        Medicine(
            name = "Paracetamol",
            quantity = 20,
            stock = 100,
            minStock = 10,
            expiryDate = LocalDate.parse("2026-12-01")
        ),
        Medicine(
            name = "Amoxicillin",
            quantity = 15,
            stock = 50,
            minStock = 5,
            expiryDate = LocalDate.parse("2027-03-15")
        )
    )

    val statsViewModel: DashboardStatsViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Onboarding.route) {
            OnboardingSlider(
                navController = navController,
                onFinish = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(ROUTE_REGISTER) {
            RegisterScreen(navController)
        }

        composable(ROUTE_LOGIN) {
            LoginScreen(
                navController = navController,
                onRoleSelected = { role, username ->
                    val encodedUsername = java.net.URLEncoder.encode(username, "UTF-8")
                    val destination = when (role) {
                        "Patient" -> "patient_dashboard/$encodedUsername"
                        "Doctor"  -> "doctor_dashboard/$encodedUsername"
                        "Admin"   -> "admin_dashboard/$encodedUsername"
                        else      -> "patient_dashboard/$encodedUsername"
                    }
                    navController.navigate(destination) {
                        popUpTo(ROUTE_LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(ROUTE_FORGOT_PASSWORD) {
            ForgotPasswordScreen(navController)
        }

        composable("patient_dashboard/{username}") { backStackEntry ->
            val username = java.net.URLDecoder.decode(
                backStackEntry.arguments?.getString("username") ?: "", "UTF-8"
            )
            PatientDashboard(
                navController = navController,
                username = username,
                viewModel = viewModel(),
                statsViewModel = statsViewModel,
                onLogout = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("doctor_dashboard/{username}") { backStackEntry ->
            val username = java.net.URLDecoder.decode(
                backStackEntry.arguments?.getString("username") ?: "", "UTF-8"
            )
            DoctorDashboard(
                navController = navController,
                username = username,
                viewModel = viewModel(),
                statsViewModel = statsViewModel,
                onLogout = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("admin_dashboard/{username}") { backStackEntry ->
            val username = java.net.URLDecoder.decode(
                backStackEntry.arguments?.getString("username") ?: "", "UTF-8"
            )
            AdminDashboard(
                navController = navController,
                username = username,
                viewModel = viewModel(),
                statsViewModel = statsViewModel,
                onLogout = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(ROUTE_MAIN_DASHBOARD) {
            DashboardScreen(navController)
        }

        composable(ROUTE_SETTINGS) {
            SettingsScreen(navController)
        }

        // ── Profile — uses the real logged-in user's Firebase UID ──────────
        composable(ROUTE_PROFILE) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            ProfileScreen(
                navController = navController,
                userId        = uid
            )
        }

        composable(ROUTE_REMINDER) {
            ReminderScreen(navController)
        }

        composable(ROUTE_EMERGENCY_SOS) {
            EmergencySOSScreen(context = navController.context)
        }

        composable(ROUTE_MEDICATION_SCREEN) {
            MedicationScreen(navController = navController, onLogout = {})
        }

        composable(ROUTE_MEDICINE_LIST) {
            MedicineListScreen(navController)
        }

        composable(ROUTE_GENERATE_REPORT) {
            GenerateReportScreen(navController)
        }

        composable(ROUTE_ADD_MEDICINE) {
            AddMedicineScreen(navController)
        }

        composable(ROUTE_ADMIN_ADD_DOCTOR) {
            AdminAddDoctorScreen(navController)
        }

        composable(ROUTE_WRITE_PRESCRIPTION) {
            WritePrescriptionScreen(navController)
        }

        composable(
            route = ROUTE_UPDATE_MEDICATION,
            arguments = listOf(navArgument("medicineId") { type = NavType.StringType })
        ) { backStackEntry ->
            val medicineId = backStackEntry.arguments?.getString("medicineId")!!
            UpdateMedicineScreen(
                navController = navController,
                medicineId    = medicineId
            )
        }

        composable(ROUTE_INVENTORY_SCREEN) {
            InventoryScreen(
                navController = navController,
                medicines     = sampleMedicines
            )
        }

        // Upcoming / Book appointment (patient)
        composable(ROUTE_UPCOMING_APPOINTMENT) {
            UpcomingAppointmentsScreen(navController)
        }

        // Admin manage appointments
        composable(ROUTE_ADMIN_APPOINTMENTS) {
            AdminAppointmentsScreen(navController)
        }

        // Doctor view appointments
        composable(ROUTE_DOCTOR_APPOINTMENTS) {
            DoctorAppointmentsScreen(navController)
        }
    }
}