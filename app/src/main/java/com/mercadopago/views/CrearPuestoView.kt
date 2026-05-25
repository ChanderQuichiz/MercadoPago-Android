package com.mercadopago.views

import OutlinedSelect
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.mercadopago.components.HeaderComponent
import com.mercadopago.models.CreatePuestoModel
import com.mercadopago.models.ServicioModel

@Preview(showBackground = true)
@Composable
fun CrearPuestoView(){
    var puesto by remember { mutableStateOf(CreatePuestoModel()) }
    //informacion de puesto
    Column() {
        HeaderComponent()
        Text(text = "INFORMACION DE PUESTO")
        Row() {
            OutlinedSelect(
                listOf<String>("Disponible", "Ocupado"),
                "Estado"
            )
            OutlinedSelect(
                listOf("Zona A", "Zona B", "Zona C"),
                "Zona"
            )
        }

        OutlinedTextField(
            value = puesto.descripcion, onValueChange = {
                puesto.descripcion = it
            }
        )
        Row() {
            OutlinedTextField(
                value = puesto.areaM2.toString(),
                onValueChange = {
                    puesto.areaM2 = puesto.areaM2.toDouble()
                }
            )

            OutlinedTextField(
                value = puesto.precioBaseMensual.toString(),
                onValueChange = {
                    puesto.areaM2 = puesto.areaM2.toDouble()
                }
            )
        }



        //informacion de puesto - fin

        //Servicios asignados - inicio
        var servicios: List<ServicioModel> = listOf<ServicioModel>()
        Column() {
            servicios.forEach { servicio ->
                Row() {
                    Text(servicio.descripcion)
                    Text("$/${servicio.precioMensual.toString()} x mes")
                }

            }

        }

        //Servicios asignados - fin
        Row() {
            Button(
                onClick = {}
            ) {
                Text("Crear")
            }
            Button(
                onClick = {}
            ) {
                Text("Cancelar")
            }
        }
    }
}