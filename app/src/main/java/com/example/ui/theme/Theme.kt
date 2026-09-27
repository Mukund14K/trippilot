package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BrandForest,
    onPrimary = Color(0xFFFBF9F5),
    primaryContainer = SurfaceSand,
    onPrimaryContainer = BrandForestDark,
    secondary = AccentTerracotta,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF7EBE5),
    onSecondaryContainer = Color(0xFF8A3B1C),
    tertiary = AccentAmber,
    onTertiary = Color(0xFF2E2400),
    background = CanvasBackground,
    onBackground = TextPrimary,
    surface = SurfaceWarm,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceSand,
    onSurfaceVariant = TextMuted,
    outline = HairpinBorder,
    outlineVariant = BorderSubtle
)

private val DarkColorScheme = darkColorScheme(
    primary = BrandForestLight,
    onPrimary = Color.White,
    primaryContainer = BrandForestDark,
    onPrimaryContainer = Color(0xFFD8F3DC),
    secondary = AccentCoral,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF432014),
    onSecondaryContainer = Color(0xFFFFDBCF),
    background = Color(0xFF141714),
    onBackground = Color(0xFFF0EFEA),
    surface = Color(0xFF1B1E1B),
    onSurface = Color(0xFFF0EFEA),
    surfaceVariant = Color(0xFF242824),
    onSurfaceVariant = Color(0xFFB5BBB5),
    outline = Color(0xFF383E38),
    outlineVariant = Color(0xFF2C322C)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // We emphasize the warm editorial ivory palette for authentic travel magazine tactile feel
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
