//package com.example.smartmedicalsystem.navigation
//
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.NavType
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.navArgument
//
//// ✅ All imports now point to the correct package — no more com.example.nexora or com.example.smartmedical
//import com.example.smartmedicalsystem.models.medication.Medicine
//import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.AdminDashboard
//import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.DoctorDashboard
//import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.PatientDashboard
//import com.example.smartmedicalsystem.ui.theme.screens.LoginScreen
//import com.example.smartmedicalsystem.ui.theme.screens.RegisterScreen
//import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.DashboardScreen
//import com.example.smartmedicalsystem.ui.theme.screens.Inventory.InventoryScreen
//import com.example.smartmedicalsystem.ui.theme.screens.Profile.screen.ProfileScreen
//import com.example.smartmedicalsystem.ui.theme.screens.Settings.screen.SettingsScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.service.Reminder.screen.ReminderScreen
//import com.example.smartmedicalsystem.ui.theme.screens.UpcomingAppointmentsScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.AddMedicine.screen.AddMedicationScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.ForgotPasswordScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen.MedicationScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.Onboarding.screen.OnboardingSlider
//import java.time.LocalDate
//import com.example.nexora.ui.theme.screens.medicine.screen.AddMedicineScreen
//import com.example.nexora.ui.theme.screens.medicine.screen.MedicineListScreen
//import com.example.nexora.ui.theme.screens.medicine.screen.UpdateMedicineScreen
//import com.example.smartmedicalsystem.ui.theme.screens.screens.EmergencySOSScreen
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
//    NavHost(
//        navController = navController,
//        startDestination = startDestination
//    ) {
//
//        // ── Onboarding ────────────────────────────────────────────
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
//        // ── Auth ──────────────────────────────────────────────────
//        composable(ROUTE_REGISTER) {
//            RegisterScreen(navController)
//        }
//
//        // ✅ Login receives onRoleSelected and navigates based on role
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
//        composable(ROUTE_UPCOMING_APPOINTMENT) {
//            UpcomingAppointmentsScreen(navController)
//        }
//
//
//        composable(ROUTE_FORGOT_PASSWORD) {
//            ForgotPasswordScreen(navController)
//        }
//
//        // ── Role Dashboards ───────────────────────────────────────
//        // ✅ PatientDashboard now receives username + viewModel correctly
//        composable("patient_dashboard/{username}") { backStackEntry ->
//            val username = java.net.URLDecoder.decode(
//                backStackEntry.arguments?.getString("username") ?: "", "UTF-8"
//            )
//            PatientDashboard(
//                navController = navController,
//                username = username,
//                viewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
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
//                viewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
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
//                viewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
//                onLogout = {
//                    navController.navigate(ROUTE_LOGIN) {
//                        popUpTo(0)
//                        launchSingleTop = true
//                    }
//                }
//            )
//        }
//
//        // ── General Dashboard ─────────────────────────────────────
//        composable(ROUTE_MAIN_DASHBOARD) {
//            DashboardScreen(navController)
//        }
//
//        // ── Settings & Profile ────────────────────────────────────
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
//        // ── Reminder ──────────────────────────────────────────────
//        composable(ROUTE_REMINDER) {
//            ReminderScreen(navController)
//        }
//
//        // ── Emergency ─────────────────────────────────────────────
//
//
//
////                    composable(ROUTE_EMERGENCY_SOS) {
//
////                        val viewModel: SOSViewModel = viewModel()
////
////                        EmergencySOSScreen(
////                            viewModel = viewModel,
////                            onCallContact = { number ->
////                                val intent = android.content.Intent(
////                                    android.content.Intent.ACTION_DIAL
////                                ).apply {
////                                    data = android.net.Uri.parse("tel:$number")
////                                }
////                                navController.context.startActivity(intent)
////                            }
////                        )
////                    }
//        composable(ROUTE_EMERGENCY_SOS) {
//            EmergencySOSScreen(context = navController.context)
//        }
//        // ── Medication ────────────────────────────────────────────
//        composable(ROUTE_MEDICATION_SCREEN) {
//            MedicationScreen(navController)
//        }
//
//        composable(ROUTE_ADD_MEDICATION) {
//            AddMedicationScreen(navController)
//        }
//
//        composable(ROUTE_MEDICINE_LIST) {
//            MedicineListScreen(navController)
//        }
//
//        composable(ROUTE_ADD_MEDICINE) {
//            AddMedicineScreen(navController)
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
//        // ── Inventory ─────────────────────────────────────────────
//        composable(ROUTE_INVENTORY_SCREEN) {
//            InventoryScreen(
//                navController = navController,
//                medicines = sampleMedicines
//            )
//        }
//
//        // ── Upcoming Appointments ─────────────────────────────────
//        composable(ROUTE_UPCOMING_APPOINTMENT) {
//            UpcomingAppointmentsScreen(navController)
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

// ✅ All imports point to the correct package
import com.example.smartmedicalsystem.data.DashboardStatsViewModel
import com.example.smartmedicalsystem.models.medication.Medicine
import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.AdminDashboard
import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.DoctorDashboard
import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.PatientDashboard
import com.example.smartmedicalsystem.ui.theme.screens.LoginScreen
import com.example.smartmedicalsystem.ui.theme.screens.RegisterScreen
import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.DashboardScreen
import com.example.smartmedicalsystem.ui.theme.screens.Inventory.InventoryScreen
import com.example.smartmedicalsystem.ui.theme.screens.Profile.screen.ProfileScreen
import com.example.smartmedicalsystem.ui.theme.screens.Settings.screen.SettingsScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.service.Reminder.screen.ReminderScreen
import com.example.smartmedicalsystem.ui.theme.screens.UpcomingAppointmentsScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.AddMedicine.screen.AddMedicationScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.ForgotPasswordScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen.MedicationScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.Onboarding.screen.OnboardingSlider
import java.time.LocalDate
import com.example.nexora.ui.theme.screens.medicine.screen.AddMedicineScreen
import com.example.nexora.ui.theme.screens.medicine.screen.MedicineListScreen
import com.example.nexora.ui.theme.screens.medicine.screen.UpdateMedicineScreen
import com.example.smartmedicalsystem.ui.theme.Admin.AdminAddDoctorScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.EmergencySOSScreen
import com.example.smartmedicalsystem.ui.theme.screens.screens.Medicine.screen.WritePrescriptionScreen


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

    // ✅ NEW: Single shared DashboardStatsViewModel scoped to the NavHost.
    // Because viewModel() is called here (outside individual composable lambdas),
    // the same instance is shared across all three role dashboards — Firebase
    // listeners are attached only once and survive navigation back-and-forth.
    val statsViewModel: DashboardStatsViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // ── Onboarding ────────────────────────────────────────────
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

        // ── Auth ──────────────────────────────────────────────────
        composable(ROUTE_REGISTER) {
            RegisterScreen(navController)
        }

        // ✅ Login receives onRoleSelected and navigates based on role
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

        // ── Role Dashboards ───────────────────────────────────────

        // ✅ PatientDashboard now receives statsViewModel for live counts
        composable("patient_dashboard/{username}") { backStackEntry ->
            val username = java.net.URLDecoder.decode(
                backStackEntry.arguments?.getString("username") ?: "", "UTF-8"
            )
            PatientDashboard(
                navController = navController,
                username = username,
                viewModel = viewModel(),
                statsViewModel = statsViewModel,  // ✅ live counts
                onLogout = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }

        // ✅ DoctorDashboard now receives statsViewModel for live pending count
        composable("doctor_dashboard/{username}") { backStackEntry ->
            val username = java.net.URLDecoder.decode(
                backStackEntry.arguments?.getString("username") ?: "", "UTF-8"
            )
            DoctorDashboard(
                navController = navController,
                username = username,
                viewModel = viewModel(),
                statsViewModel = statsViewModel,  // ✅ live counts
                onLogout = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }

        // ✅ AdminDashboard now receives statsViewModel for live doctor count
        composable("admin_dashboard/{username}") { backStackEntry ->
            val username = java.net.URLDecoder.decode(
                backStackEntry.arguments?.getString("username") ?: "", "UTF-8"
            )
            AdminDashboard(
                navController = navController,
                username = username,
                viewModel = viewModel(),
                statsViewModel = statsViewModel,  // ✅ live counts
                onLogout = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }

        // ── General Dashboard ─────────────────────────────────────
        composable(ROUTE_MAIN_DASHBOARD) {
            DashboardScreen(navController)
        }

        // ── Settings & Profile ────────────────────────────────────
        composable(ROUTE_SETTINGS) {
            SettingsScreen(navController)
        }

        composable(ROUTE_PROFILE) {
            ProfileScreen(
                navController = navController,
                userId = "userId"  // TODO: pass real Firebase userId from auth
            )
        }

        // ── Reminder ──────────────────────────────────────────────
        composable(ROUTE_REMINDER) {
            ReminderScreen(navController)
        }

        // ── Emergency ─────────────────────────────────────────────
        composable(ROUTE_EMERGENCY_SOS) {
            EmergencySOSScreen(context = navController.context)
        }

        // ── Medication ────────────────────────────────────────────
        composable(ROUTE_MEDICATION_SCREEN) {
            MedicationScreen(navController)
        }

        composable(ROUTE_ADD_MEDICATION) {
            AddMedicationScreen(navController)
        }

        composable(ROUTE_MEDICINE_LIST) {
            MedicineListScreen(navController)
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
                medicineId = medicineId
            )
        }

        // ── Inventory ─────────────────────────────────────────────
        composable(ROUTE_INVENTORY_SCREEN) {
            InventoryScreen(
                navController = navController,
                medicines = sampleMedicines
            )
        }

        // ── Upcoming Appointments ─────────────────────────────────
        composable(ROUTE_UPCOMING_APPOINTMENT) {
            UpcomingAppointmentsScreen(navController)
        }
    }
}