package com.practice.daily_task.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White


private val LightColors = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = White,
    secondary = PurpleLight,
    background = GrayLight,
    onBackground = Black,
    surface = White,
    onSurface = Black
)

private val DarkColors = darkColorScheme(
    primary = PurpleDark,
    onPrimary = White,
    secondary = PurpleLight,
    background = GrayDark,
    onBackground = White,
    surface = GrayDark,
    onSurface = White
)

@Composable
fun Daily_TaskTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}



