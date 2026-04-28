package com.example.smartmedicalsystem.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartmedical.screens.AdminDashboard
import com.example.smartmedical.screens.DoctorDashboard
import com.example.smartmedical.screens.PatientDashboard
import com.example.smartmedicalsystem.ui.theme.screens.DashboardScreen
import com.example.smartmedicalsystem.ui.theme.screens.LoginScreen
import com.example.smartmedicalsystem.ui.theme.screens.RegisterScreen
import com.example.smartmedicalsystem.ui.theme.screens.screen.ForgotPasswordScreen


object Routes {
    const val LOGIN = "login"
    const val PATIENT = "patient_dashboard"
    const val DOCTOR = "doctor_dashboard"
    const val ADMIN = "admin_dashboard"
}
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(),
               startDestination:String = ROUTE_LOGIN){
    NavHost(navController = navController,
        startDestination = startDestination) {
        composable(ROUTE_REGISTER) { RegisterScreen(navController) }
        composable(ROUTE_LOGIN) { LoginScreen(navController,  onRoleSelected = { role ->
            when (role) {

                "Patient" -> navController.navigate(Routes.PATIENT)
                "Doctor"  -> navController.navigate(Routes.DOCTOR)
                "Admin"   -> navController.navigate(Routes.ADMIN)
            }
        }
        )
        }
        composable(Routes.PATIENT) {
            PatientDashboard(onLogout = { navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }})
        }
        composable(Routes.DOCTOR) {
            DoctorDashboard(onLogout = { navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }})
        }
        composable(Routes.ADMIN) {
            AdminDashboard(onLogout = { navController.navigate(Routes.LOGIN) {
                popUpTo(Routes.LOGIN) { inclusive = true }
            }})
        }

        composable(ROUTE_MAIN_DASHBOARD) { DashboardScreen(navController) }

        composable(ROUTE_FORGOT_PASSWORD) { ForgotPasswordScreen(navController) }
    }
}


