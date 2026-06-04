package com.mercadopago.views

import com.mercadopago.components.OutlinedSelect
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.CreatePuestoModel
import com.mercadopago.models.ServicioModel

@Composable
fun CrearPuestoView(
    navController: NavController
){
    var puesto by remember { mutableStateOf(CreatePuestoModel()) }
    //informacion de puesto
    Column(modifier = Modifier.fillMaxSize()
        .padding(30.dp,0.dp)) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "INFORMACION DE PUESTO",
            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)
            ),
            modifier = Modifier.padding(0.dp,10.dp)
        )
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            OutlinedSelect(
                listOf("Disponible", "Ocupado"),
                "Estado",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(0.dp,0.dp,8.dp,0.dp)
            )
            OutlinedSelect(
                listOf("Zona A", "Zona B", "Zona C"),
                "Zona",
                modifier = Modifier
                    .padding(8.dp,0.dp,0.dp,0.dp)

            )
        }

        OutlinedTextField(
            value = puesto.descripcion, onValueChange = {
                puesto = puesto.copy(descripcion = it)
            },
            placeholder = {
                Text("Descripcion")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp,15.dp)
        )
        Row {
            OutlinedTextField(
                value = puesto.areaM2.toString(),
                onValueChange = {
                    puesto = puesto.copy(areaM2 = it.toDouble())
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            )
            Spacer(modifier =
                    Modifier.
                width(15.dp)
            )
            OutlinedTextField(
                value = puesto.precioBaseMensual.toString(),
                onValueChange = {
                    puesto = puesto.copy(precioBaseMensual = it.toDouble())
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        //informacion de puesto - fin

        //Servicios asignados - inicio
        var servicios: List<ServicioModel> = listOf(
            ServicioModel(1,"Agua","Servicio de agua",20.00,"ACTIVO"),
            ServicioModel(1,"Agua","Servicio de agua",20.00,"ACTIVO")
        )
        var serviciosIds: List<Number> = listOf(1,4)
        Column {
            Text("SERVICIO ASIGNADOS",
                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                modifier = Modifier.padding(0.dp,10.dp)
                )

            servicios.forEach { servicio ->
                var servicioRegistrado:Number? = serviciosIds.find { item ->
                    item == servicio.id
                }
                Row(
                   verticalAlignment = Alignment.CenterVertically,
                    modifier =
                    Modifier
                        .background(Color(0XFFFCFAFA))
                        .fillMaxWidth()
                        .border(1.dp, Color(0XFF888787),RoundedCornerShape(5.dp))
                        .height(40.dp)

                )
                {

                    if (servicioRegistrado != null){
                        Checkbox(
                            checked = true,
                            onCheckedChange = {  }
                        )
                    }
                    else {
                        Checkbox(
                            checked = false,
                            onCheckedChange = { }
                        )
                    }
                    Text(servicio.descripcion,
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                        ,
                        color = Color(0XFF777777)
                    )
                    Text("$/${servicio.precioMensual} x mes   ",
                        fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght))
                    , textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0XFF777777)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

        }

        //Servicios asignados - fin
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color(0XFF355CC0)),
                shape = RoundedCornerShape(7.dp)
            ) {
                Text("Crear",
                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                 navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(Color(0XFFD9D9D9)),
                shape = RoundedCornerShape(7.dp),

            ) {
                Text("Cancelar",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)))
            }
        }
    }
}