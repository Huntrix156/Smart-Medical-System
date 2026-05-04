//package com.example.nexora.ui.theme.screens.Settings.screen
//
//import android.app.Person
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.DarkMode
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material.icons.filled.Notifications
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Divider
//import androidx.compose.material3.Switch
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import com.example.nexora.ui.theme.screens.component.SettingItem
//import okhttp3.internal.http2.Settings
//
//@Composable
//fun SettingsScreen(navController: NavController){
//
//        var notificationsEnabled by remember { mutableStateOf(true) }
//        var darkThemeEnabled by remember { mutableStateOf(false) }
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//
//            Text(
//                text = "Settings",
//                fontSize = 26.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            // 🔹 Profile Section
//            Text("Account", fontWeight = FontWeight.Bold, color = Color.Gray)
//            Spacer(modifier = Modifier.height(8.dp))
//
//            SettingItem(
//                title = "Profile Settings",
//                subtitle = "Edit your personal information",
//                icon = Icons.Default.Person,
//                onClick = { navController.navigate("profile") }
//            )
//
//            Divider()
//
//            // 🔹 Notifications
//            Text("Preferences", fontWeight = FontWeight.Bold, color = Color.Gray)
//            Spacer(modifier = Modifier.height(8.dp))
//
//            SettingItem(
//                title = "Notifications",
//                subtitle = "Enable reminders and alerts",
//                icon = Icons.Default.Notifications,
//                trailing = {
//                    Switch(
//                        checked = notificationsEnabled,
//                        onCheckedChange = { notificationsEnabled = it }
//                    )
//                }
//            )
//
//            Divider()
//
//            // 🔹 Theme Toggle
//            SettingItem(
//                title = "Dark Mode",
//                subtitle = "Switch between light and dark theme",
//                icon = Icons.Default.DarkMode,
//                trailing = {
//                    Switch(
//                        checked = darkThemeEnabled,
//                        onCheckedChange = { darkThemeEnabled = it }
//                    )
//                }
//            )
//
//            Divider()
//
//            // 🔹 Privacy & Security
//            Text("Security", fontWeight = FontWeight.Bold, color = Color.Gray)
//            Spacer(modifier = Modifier.height(8.dp))
//
//            SettingItem(
//                title = "Privacy & Security",
//                subtitle = "Manage passwords and permissions",
//                icon = Icons.Default.Lock,
//                onClick = { navController.navigate("security") }
//            )
//
//            Divider()
//
//            Spacer(modifier = Modifier.height(30.dp))
//
//            // 🔹 Logout Button
//            Button(
//                onClick = {

//                    navController.navigate("login") {
//                        popUpTo("dashboard") { inclusive = true }
//                    }
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Logout", color = Color.White)
//            }
//        }
//    }
//

//

//}
//
//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun SettingsScreenPreview(){
//    SettingsScreen( navController=rememberNavController())
//}



package com.example.nexora.ui.theme.screens.Settings.screen

// ✅ FIX 1: Removed wrong imports (android.app.Person, okhttp3.internal.http2.Settings)
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smartmedicalsystem.navigation.ROUTE_ADMIN_ADD_DOCTOR
import com.example.smartmedicalsystem.navigation.ROUTE_MAIN_DASHBOARD


@Composable
fun SettingsScreen(navController: NavController) {

    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkThemeEnabled by remember { mutableStateOf(false)}

        // After ✅ — reads from global theme state
//    var darkThemeEnabled by remember { mutableStateOf(AppThemeState.isDarkTheme) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Settings",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Account Section
        Text("Account", fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))

        SettingItem(
            title = "Profile Settings",
            subtitle = "Edit your personal information",
            icon = Icons.Default.Person,
            onClick = { navController.navigate("profile") {
                navController.navigate(ROUTE_ADMIN_ADD_DOCTOR) {
                    popUpTo(ROUTE_MAIN_DASHBOARD)
                    launchSingleTop = true
                }
            }}
        )

        Divider()

        // 🔹 Notifications
        Text("Preferences", fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))

        SettingItem(
            title = "Notifications",
            subtitle = "Enable reminders and alerts",
            icon = Icons.Default.Notifications,
            trailing = {
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
            }
        )

        Divider()

        // 🔹 Dark Mode
        SettingItem(
            title = "Dark Mode",
            subtitle = "Switch between light and dark theme",
            icon = Icons.Default.DarkMode,
            trailing = {
                Switch(
                    checked = darkThemeEnabled,
                    onCheckedChange = { darkThemeEnabled = it }
                )
            }
        )

        Divider()

        // 🔹 Security
        Text("Security", fontWeight = FontWeight.Bold, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))

        SettingItem(
            title = "Privacy & Security",
            subtitle = "Manage passwords and permissions",
            icon = Icons.Default.Lock,
            onClick = { navController.navigate("security") }
        )

        Divider()

        Spacer(modifier = Modifier.height(30.dp))

        // 🔹 Logout Button
        Button(
            onClick = {
                navController.navigate("login") {
                    popUpTo("dashboard") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout", color = Color.White)
        }
    }
}

// ✅ FIX 2: Removed two broken SettingItem functions
// ✅ FIX 3: Replaced with one correct SettingItem composable
// ✅ FIX 4: onClick and trailing are both optional (nullable)
// ✅ FIX 5: icon type is ImageVector (not android.app.Person)
// ✅ FIX 6: () -> navigate is not valid — changed to () -> Unit
@Composable
fun SettingItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: (() -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ✅ FIX 7: Added actual Icon composable — was missing before
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF1565C0)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(text = title, fontWeight = FontWeight.Medium)
            Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
        }

        // ✅ FIX 8: trailing widget (Switch etc.) renders only if provided
        trailing?.invoke()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}