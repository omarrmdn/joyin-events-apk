package com.example.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

var isDarkThemeActiveByOmar by mutableStateOf(true)

val JoyinBlack = Color(0xFF000000)

val JoyinWhite: Color
    get() = if (isDarkThemeActiveByOmar) Color(0xFF141414) else Color(0xFFFFFFFF)

val JoyinAccent = Color(0xFFF53132)

val JoyinCharcoal: Color
    get() = if (isDarkThemeActiveByOmar) Color(0xFFFFFFFF) else Color(0xFF141414)

val JoyinDarkGray = Color(0xFF161616)

val JoyinMediumGray: Color
    get() = if (isDarkThemeActiveByOmar) Color(0xFF2C2C2C) else Color(0xFFE5E5E5) // Dynamic background gray for input fields/containers

val JoyinLightGray: Color
    get() = if (isDarkThemeActiveByOmar) Color(0xFFB0B0B0) else Color(0xFF757575)   // High-contrast font gray

val JoyinCream = Color(0xFFFFF1EB) // Peach/cream tone for the images in mockup

val JoyinSelectedRedBg: Color
    get() = if (isDarkThemeActiveByOmar) Color(0xFF381517) else Color(0xFFFFEBEE) // Light soft pink/burgundy tinted highlight

val JoyinBubbleGray: Color
    get() = if (isDarkThemeActiveByOmar) Color(0xFF242424) else Color(0xFFF0F0F0) // Light bubble gray for conversational UI

val JoyinLightGrayBG: Color
    get() = if (isDarkThemeActiveByOmar) Color(0xFF1E1E1E) else Color(0xFFF5F5F5)

val JoyinBorderGray: Color
    get() = if (isDarkThemeActiveByOmar) Color(0xFF2D2D2D) else Color(0xFFEEEEEE)


