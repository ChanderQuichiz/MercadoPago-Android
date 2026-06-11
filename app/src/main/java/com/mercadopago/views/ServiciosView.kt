package com.mercadopago.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
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
import com.mercadopago.components.ServicioCard
import com.mercadopago.models.ServicioModel


@Composable
fun ServiciosView(
    navController: NavController? = null
) {
    val servicios = listOf(
        ServicioModel(1, "Servicio de Vigilancia", "Descripción del servicio creado por el administrador para ser visualizado por los demás usuarios...", 60.00, "ACTIVO"),
        ServicioModel(2, "Energía Eléctrica", "Descripción del servicio...", 60.00, "ACTIVO"),
        ServicioModel(3, "Energía Eléctrica", "Descripción del servicio...", 60.00, "ACTIVO"),
        ServicioModel(4, "Energía Eléctrica", "Descripción del servicio...", 60.00, "ACTIVO"),
        ServicioModel(5, "Energía Eléctrica", "Descripción del servicio...", 60.00, "ACTIVO"),
        ServicioModel(6, "Energía Eléctrica", "Descripción del servicio...", 60.00, "ACTIVO"),
        ServicioModel(7, "Energía Eléctrica", "Descripción del servicio...", 60.00, "ACTIVO")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 26.dp, vertical = 42.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menú",
                modifier = Modifier.size(34.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "Servicios",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 4.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Gestión de servicios facturables",
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(42.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(230.dp)
                    .height(46.dp)
            ) {
                Text(
                    text = "Todas las zonas",
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Zonas"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF27D3BE),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(7.dp),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .width(120.dp)
                    .height(46.dp)
            ) {
                Text(
                    text = "Crear",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(62.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            items(servicios) { servicio ->
                ServicioCard(servicio = servicio)
            }
        }
    }
}