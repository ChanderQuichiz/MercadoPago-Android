package com.mercadopago.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.mercadopago.components.HeaderComponent
import com.mercadopago.models.PuestoCardModel

@Composable
@Preview(showBackground = true)
fun PuestosView(){
    Column() {
        HeaderComponent()
        //tabs DE ESTADO - INICIO
        var state by remember { mutableStateOf(0) }
        val titles = listOf("Tab 1", "Tab 2", "Tab 3 with lots of text")
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
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Secondary tab ${state + 1} selected",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
        //TABS DE ESTADO FIN
        //ROWS PUESTOS
        var puestos: List<PuestoCardModel> by remember { mutableStateOf(listOf()) }

        puestos.forEach {
            puesto ->
            Row() {

                Column() {
                    //image
                    Text(puesto.codigo)
                }
                Column() {
                    Text(puesto.descripcion)
                    Row() {
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
}