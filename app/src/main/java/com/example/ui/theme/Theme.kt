package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val ArtisticFlairColorScheme =
  lightColorScheme(
    primary = JoyinAccent,
    onPrimary = JoyinWhite,
    secondary = JoyinCharcoal,
    onSecondary = JoyinWhite,
    tertiary = JoyinLightGray,
    background = JoyinWhite,
    onBackground = JoyinCharcoal,
    surface = JoyinWhite,
    onSurface = JoyinCharcoal,
    surfaceVariant = JoyinLightGrayBG,
    onSurfaceVariant = JoyinCharcoal,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = false, // Shift to default light theme for Artistic Flair
  dynamicColor: Boolean = false, // Disable system dynamic color to preserve customized Joyin visuals
  content: @Composable () -> Unit,
) {
  val colorScheme = ArtisticFlairColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography) {
    androidx.compose.material3.ProvideTextStyle(
      value = androidx.compose.ui.text.TextStyle(fontFamily = GraphikFontFamily)
    ) {
      content()
    }
  }
}
