package com.mercadopago.views

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
import com.mercadopago.components.ReporteCard
import com.mercadopago.models.ReporteModel


@Composable
fun ReportesView(
    navController: NavController? = null
) {
    val reportes = List(5) {
        ReporteModel(
            codigoPuesto = "PUE-003",
            nombreSocio = "Carlos Guerra Ciro",
            hora = "10:16:15",
            monto = 9700.00
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 38.dp, vertical = 52.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menú",
                modifier = Modifier.size(34.dp)
            )

            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = "Reportes",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 5.sp
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Reportes e informes del sistema",
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(62.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "REPORTE CAJA DIARIO",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(
                            text = "dd/mm/aaaa",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Fecha",
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    modifier = Modifier
                        .width(170.dp)
                        .height(58.dp),
                    shape = RoundedCornerShape(9.dp),
                    singleLine = true
                )
            }

            Card(
                modifier = Modifier
                    .width(190.dp)
                    .height(94.dp),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.Black),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF27D3BE)
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "TOTAL",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "S/54260.00",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(58.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(34.dp)
        ) {
            items(reportes) { item ->
                ReporteCard(item = item)
            }
        }
    }
}