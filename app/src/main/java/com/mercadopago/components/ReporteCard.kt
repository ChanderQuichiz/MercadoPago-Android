package com.mercadopago.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mercadopago.models.ReporteModel


@Composable
fun ReporteCard(
    item: ReporteModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(102.dp),
        shape = RoundedCornerShape(9.dp),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Store,
                    contentDescription = "Puesto",
                    tint = Color.Gray,
                    modifier = Modifier.size(38.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.codigoPuesto,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.width(18.dp))

            Column(
                modifier = Modifier.width(145.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.nombreSocio,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.5.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.hora,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

//            Box(
//                modifier = Modifier
//                    .size(8.dp)
//                    .background(
//                        color = Color(0xFFE2E600),
//                        shape = CircleShape
//                    )
//            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "S/${"%.2f".format(item.monto)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Detalle",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}