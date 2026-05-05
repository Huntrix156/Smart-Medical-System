// ✅ FIXED: Package now matches this project — was wrongly set to com.example.nexora
package com.example.smartmedicalsystem.ui.theme.screens.Settings.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.smartmedicalsystem.navigation.ROUTE_PROFILE

@Composable
fun SettingsScreen(navController: NavController) {

    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkThemeEnabled by remember { mutableStateOf(false) }

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

        // ── Account ───────────────────────────────────────────────
        Text("Account", fontWeight = FontWeight.Bold, color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ FIXED: Profile navigation now uses ROUTE_PROFILE constant
        SettingItem(
            title = "Profile Settings",
            subtitle = "Edit your personal information",
            icon = Icons.Default.Person,
            onClick = {
                navController.navigate(ROUTE_PROFILE) {
                    launchSingleTop = true
                }
            }
        )

        HorizontalDivider()

        // ── Preferences ───────────────────────────────────────────
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

        HorizontalDivider()

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

        HorizontalDivider()

        // ── Security ──────────────────────────────────────────────
        Text("Security", fontWeight = FontWeight.Bold, color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))

        SettingItem(
            title = "Privacy & Security",
            subtitle = "Manage passwords and permissions",
            icon = Icons.Default.Lock,
            onClick = { /* navigate to security screen */ }
        )

        HorizontalDivider()

        Spacer(modifier = Modifier.height(30.dp))

        // ── Logout ────────────────────────────────────────────────
        Button(
            onClick = {
                navController.navigate("login") {
                    popUpTo(0) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout", color = Color.White)
        }
    }
}

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
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {
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
        trailing?.invoke()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}