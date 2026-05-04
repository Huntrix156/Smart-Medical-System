package com.example.smartmedicalsystem.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dosetracker.ui.theme.screens.changepassword.screen.ChangePassword
import com.example.nexora.ui.theme.screens.Reminder.screen.ReminderScreen
import com.example.nexora.ui.theme.screens.Settings.screen.SettingsScreen
import com.example.nexora.ui.theme.screens.medicine.screen.AddMedicineScreen
import com.example.nexora.ui.theme.screens.medicine.screen.MedicineListScreen
import com.example.nexora.ui.theme.screens.medicine.screen.UpdateMedicineScreen
import com.example.smartmedical.screens.AdminDashboard
import com.example.smartmedical.screens.DoctorDashboard
import com.example.smartmedical.screens.PatientDashboard
import com.example.smartmedicalsystem.model.MedicationReminderComponent
import com.example.smartmedicalsystem.models.medication.Medicine
import com.example.smartmedicalsystem.ui.theme.Admin.AdminAddDoctorScreen
import com.example.smartmedicalsystem.ui.theme.screens.Dashboard.screen.DashboardScreen
import com.example.smartmedicalsystem.ui.theme.screens.Inventory.InventoryScreen
import com.example.smartmedicalsystem.ui.theme.screens.LoginScreen
import com.example.smartmedicalsystem.ui.theme.screens.RegisterScreen
import com.example.smartmedicalsystem.ui.theme.screens.UpcomingAppointmentsScreen
import com.example.smartmedicalsystem.ui.theme.screens.screen.AddMedicine.screen.AddMedicationScreen
import com.example.smartmedicalsystem.ui.theme.screens.screen.ForgotPasswordScreen
import com.example.smartmedicalsystem.ui.theme.screens.screen.Medicine.screen.MedicationScreen
import com.example.smartmedicalsystem.ui.theme.screens.screen.Onboarding.screen.OnboardingSlider
import java.time.LocalDate




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
        composable(Screen.Login.route) {
            LoginScreen(navController)
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
        composable(ROUTE_MEDICATION_REMINDER_COMPONENT) {
            MedicationReminderComponent() // Ensure this name matches your UI file
        }
        composable(ROUTE_UPCOMING_APPOINTMENT) {
            UpcomingAppointmentsScreen(navController)
        }

        //============New=================//
        composable (ROUTE_MEDICINE_LIST) { MedicineListScreen(navController) }

        composable (ROUTE_ADD_MEDICINE) { AddMedicineScreen(navController) }

        composable(ROUTE_ADMIN_ADD_DOCTOR) {
            AdminAddDoctorScreen(navController)
        }
        composable(ROUTE_SETTINGS) {
            SettingsScreen(navController = navController)
        }

        composable (ROUTE_REMINDER) { ReminderScreen(navController = navController) }

        composable (ROUTE_CHANGE_PASSWORD ){ ChangePassword(navController)}

        composable(ROUTE_UPDATE_MEDICATION,
            arguments = listOf(
                navArgument("medicineId"){
                    type = NavType.StringType }
            )){
                backStackEntry ->
            val medicineId = backStackEntry.arguments?.getString("medicineId")!!
            UpdateMedicineScreen(
                navController = navController,
                medicineId  = medicineId
            )
        }



    }


}


