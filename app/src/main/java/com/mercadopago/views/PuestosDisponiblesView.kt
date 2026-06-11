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
import com.mercadopago.components.PuestoCard
import com.mercadopago.models.PuestoCardModel
import com.mercadopago.models.ServicioModel


@Composable
fun PuestosDisponiblesView(
    navController: NavController? = null
) {
    val puestos = listOf(
        PuestoCardModel(
            id = 1,
            codigo = "PUE-002",
            descripcion = "DESCRIPCIÓN DEL PUESTO",
            zona = "Zona A",
            areaM2 = 10,
            precioBaseMensual = 1120.00,
            estado = "DISPONIBLE",
            servicios = listOf(
                ServicioModel(1, "AGUA", "", 0, "ACTIVO"),
                ServicioModel(2, "LUZ", "", 0, "ACTIVO"),
                ServicioModel(3, "LIMPIEZA", "", 0, "ACTIVO")
            )
        ),
        PuestoCardModel(
            id = 2,
            codigo = "PUE-002",
            descripcion = "DESC. COMIDA",
            zona = "Zona A",
            areaM2 = 10,
            precioBaseMensual = 1120.00,
            estado = "DISPONIBLE",
            servicios = listOf(
                ServicioModel(1, "AGUA", "", 0, "ACTIVO"),
                ServicioModel(2, "LUZ", "", 0, "ACTIVO"),
                ServicioModel(3, "LIMPIEZA", "", 0, "ACTIVO")
            )
        ),
        PuestoCardModel(
            id = 3,
            codigo = "PUE-002",
            descripcion = "DESC. COMIDA",
            zona = "Zona A",
            areaM2 = 10,
            precioBaseMensual = 1120.00,
            estado = "DISPONIBLE",
            servicios = listOf(
                ServicioModel(1, "AGUA", "", 0, "ACTIVO"),
                ServicioModel(2, "LUZ", "", 0, "ACTIVO"),
                ServicioModel(3, "LIMPIEZA", "", 0, "ACTIVO")
            )
        ),
        PuestoCardModel(
            id = 4,
            codigo = "PUE-002",
            descripcion = "DESC. COMIDA",
            zona = "Zona A",
            areaM2 = 10,
            precioBaseMensual = 1120.00,
            estado = "DISPONIBLE",
            servicios = listOf(
                ServicioModel(1, "AGUA", "", 0, "ACTIVO"),
                ServicioModel(2, "LUZ", "", 0, "ACTIVO"),
                ServicioModel(3, "LIMPIEZA", "", 0, "ACTIVO")
            )
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 34.dp, vertical = 52.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menú",
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(28.dp))

            Text(
                text = "Puestos Disponibles",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.weight(1f))

        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Explora los puestos disponibles y enviá una solicitud",
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 60.dp)
        )

        Spacer(modifier = Modifier.height(56.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { },
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color.Gray),
                modifier = Modifier
                    .width(245.dp)
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Todas las zonas",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.width(20.dp))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Zonas"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { },
                shape = RoundedCornerShape(7.dp),
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier
                    .width(125.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF27D3BE),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Crear",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(70.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(38.dp)
        ) {
            items(puestos) { puesto ->
                PuestoCard(puesto = puesto)
            }
        }
    }
}