package com.example.smartmedicalsystem.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Color.Black,

    secondary = PurpleGrey80,
    onSecondary = Color.Black,

    tertiary = Pink80,
    onTertiary = Color.Black

)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1E88E5),
    onPrimary = Color.White,

    secondary = Color(0xFF26A69A),
    onSecondary = Color.White,

    tertiary = Color(0xFFFFB300),
    onTertiary = Color.Black,

    background = Color(0xFFF7FAFC),
    onBackground = Color(0xFF1A1A1A),

    surface = Color.White,
    onSurface = Color(0xFF1A1A1A),

    error = Color(0xFFE53935),
    onError = Color.White
)
@Composable
fun SmartMedicalSystemTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // ← IMPORTANT CHANGE
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}