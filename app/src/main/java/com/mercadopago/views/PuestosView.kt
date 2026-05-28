package com.mercadopago.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mercadopago.models.PuestoCardModel

@Composable
fun PuestosView(navController: NavController){
    DetailedDrawer(  navController = navController) {
        padding ->

        Column(
            modifier = Modifier.padding(padding)
        ) {
            //tabs DE ESTADO - INICIO
            var state by remember { mutableIntStateOf(0) }
            val titles = listOf("DISPONIBLE", "OCUPADO")
            Column {
                SecondaryTabRow(selectedTabIndex = state) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = state == index,
                            onClick = { state = index },
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                        )
                    }
                }

            }
            //TABS DE ESTADO FIN
            //ROWS PUESTOS
            var puestos: List<PuestoCardModel> by remember { mutableStateOf(listOf(
                PuestoCardModel(

                    0,"PUE001","Puesto de comida",
                    zona = "Zona A",
                    areaM2 = 23,
                    precioBaseMensual = 342.23,
                    "Disponible",
                    listOf(1,2,4),

                )
            )) }

            puestos.forEach { puesto ->
                Row {

                    Column {
                        //image
                        Text(puesto.codigo)
                    }
                    Column {
                        Text(puesto.descripcion)
                        Row {
                            Text(puesto.zona)
                            Text(puesto.areaM2.toString())
                            Text("${puesto.servicios.size} servicios")
                        }
                    }
                    Text("S/${puesto.precioBaseMensual}")
                    Button(onClick = {}) {
                        Text(">")
                    }
                }

            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            // Contenido principal
            Text("Hola")

            FloatingActionButton(
                onClick = {
                    navController.navigate("crear-puesto")
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp,100.dp)
            ) {
                Text("+")
            }
        }
    }
}