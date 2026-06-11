package com.mercadopago.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mercadopago.R

val ChangaMedium = FontFamily(
    Font(R.font.changa_medium)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = ChangaMedium,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(fontFamily = ChangaMedium),
    bodySmall = TextStyle(fontFamily = ChangaMedium),
    titleLarge = TextStyle(fontFamily = ChangaMedium),
    titleMedium = TextStyle(fontFamily = ChangaMedium),
    titleSmall = TextStyle(fontFamily = ChangaMedium),
    labelLarge = TextStyle(fontFamily = ChangaMedium),
    labelMedium = TextStyle(fontFamily = ChangaMedium),
    labelSmall = TextStyle(fontFamily = ChangaMedium),
    headlineLarge = TextStyle(fontFamily = ChangaMedium),
    headlineMedium = TextStyle(fontFamily = ChangaMedium),
    headlineSmall = TextStyle(fontFamily = ChangaMedium),
    displayLarge = TextStyle(fontFamily = ChangaMedium),
    displayMedium = TextStyle(fontFamily = ChangaMedium),
    displaySmall = TextStyle(fontFamily = ChangaMedium)
)