package com.mercadopago.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mercadopago.R

val ChangaFontFamily = FontFamily(
    Font(R.font.changa_medium, FontWeight.Normal),
    Font(R.font.changa_medium, FontWeight.Medium),
    Font(R.font.changa_medium, FontWeight.Bold)
)

val Typography = Typography(
    displayLarge = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Bold, fontSize = 57.sp),
    displayMedium = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Bold, fontSize = 45.sp),
    displaySmall = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Bold, fontSize = 36.sp),

    headlineLarge = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Bold, fontSize = 32.sp),
    headlineMedium = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Bold, fontSize = 28.sp),
    headlineSmall = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Bold, fontSize = 24.sp),

    titleLarge = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Medium, fontSize = 28.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Medium, fontSize = 16.sp),
    titleSmall = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp),

    bodyLarge = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp),
    bodyMedium = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp),
    bodySmall = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp),

    labelLarge = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp),
    labelMedium = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp),
    labelSmall = TextStyle(fontFamily = ChangaFontFamily, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp)
)