package com.mercadopago.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BoxInfo(text: String) {
    Box(
        modifier = Modifier
            .width(108.dp)
            .height(52.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Gray,
            letterSpacing = 2.sp
        )
    }
}

@Composable
fun ServicioChip(text: String) {
    Box(
        modifier = Modifier
            .height(52.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(9.dp)
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace,
            color = Color.Gray,
            letterSpacing = 2.sp
        )
    }
}