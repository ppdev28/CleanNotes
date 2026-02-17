package com.example.cleannotes.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = SoftSage,
    secondary = MediumGray,
    tertiary = WarmGray,
    background = NearBlackBG,
    surface = NearBlackBG,
    onPrimary = NearBlackBG,
    onSecondary = OffWhiteText,
    onTertiary = OffWhiteText,
    onBackground = OffWhiteText,
    onSurface = OffWhiteText
)

private val LightColorScheme = lightColorScheme(
    primary = EarthyGreen,
    secondary = StoneGray,
    tertiary = MutedClay,
    background = OffWhiteBG,
    surface = OffWhiteBG,
    onPrimary = Color.White,
    onSecondary = DarkCharcoalText,
    onTertiary = DarkCharcoalText,
    onBackground = DarkCharcoalText,
    onSurface = DarkCharcoalText
)

@Composable
fun CleanNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled to enforce the custom theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            @Suppress("DEPRECATION")
            window.statusBarColor = Color.Transparent.toArgb()

            WindowCompat.setDecorFitsSystemWindows(window, false)

            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}