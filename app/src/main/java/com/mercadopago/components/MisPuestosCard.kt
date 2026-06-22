package com.mercadopago.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mercadopago.R
import com.mercadopago.models.MisPuestoDto
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MisPuestoCard(puesto: MisPuestoDto) {

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp)
            .padding(10.dp)
            .background(Color.Transparent, RoundedCornerShape(8.dp))
            .animateContentSize()
            .padding(10.dp)
    ) {

        // Parte principal de la tarjeta
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.puesto),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )

                    Text(
                        text = puesto.codigoPuesto,
                        fontFamily = FontFamily(Font(R.font.changa_medium))
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = puesto.nombrePuesto,
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row {

                        Text(
                            text = puesto.zonaPuesto,
                            modifier = Modifier
                                .background(
                                    Color(0xFFEDEDED),
                                    RoundedCornerShape(5.dp)
                                )
                                .padding(horizontal = 5.dp, vertical = 2.dp),
                            fontFamily = FontFamily(
                                Font(R.font.inclusivesans_variablefont_wght)
                            ),
                            fontSize = 8.sp
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "${puesto.areaM2}m²",
                            modifier = Modifier
                                .background(
                                    Color(0xFFEDEDED),
                                    RoundedCornerShape(5.dp)
                                )
                                .padding(horizontal = 5.dp, vertical = 2.dp),
                            fontFamily = FontFamily(
                                Font(R.font.inclusivesans_variablefont_wght)
                            ),
                            fontSize = 8.sp
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "${puesto.servicios.size} servicios",
                            modifier = Modifier
                                .background(
                                    Color(0xFFEDEDED),
                                    RoundedCornerShape(5.dp)
                                )
                                .padding(horizontal = 5.dp, vertical = 2.dp),
                            fontFamily = FontFamily(
                                Font(R.font.inclusivesans_variablefont_wght)
                            ),
                            fontSize = 8.sp
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "S/ ${puesto.montoMensual}",
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontSize = 18.sp
                )

                IconButton(
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Icon(
                        imageVector = if (expanded)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
        }

        // Contenido expandible
        if (expanded) {

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(Color(0XFFEBEBEB))
                    .padding(16.dp
                    ,8.dp)

            ) {

                Text(
                    text = "CONTRATO",
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    color = Color(0XFFBABABA),
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

              Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
              ) {
                  Text("Codigo", color = Color(0XFF868686), fontFamily =  FontFamily(Font(R.font.changa_medium)))
                  Text(puesto.codigoContrato, color = Color(0XFF868686), fontFamily =  FontFamily(Font(R.font.changa_medium)))
              }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Inicio", color = Color(0XFF868686), fontFamily =  FontFamily(Font(R.font.changa_medium)))
                    Text(puesto.fechaInicioContrato.toString(), color = Color(0XFF868686), fontFamily =  FontFamily(Font(R.font.changa_medium)))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Fin", color = Color(0XFF868686), fontFamily =  FontFamily(Font(R.font.changa_medium)) )
                    Text(puesto.fechaFinContrato.toString(), color = Color(0XFF868686), fontFamily =  FontFamily(Font(R.font.changa_medium)))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text("Estado", color = Color(0XFF868686), fontFamily =  FontFamily(Font(R.font.changa_medium)))
                    var fechaCast = LocalDate.parse(puesto.fechaFinContrato)

                    Text(
                        if(
    fechaCast.isBefore(LocalDate.now())


                        ){
                     "ACTIVO"
                    }
                        else{
                            "VENCIDO"
                        }


                        , color = Color(0XFF35C0AB),
                        fontFamily = FontFamily(Font(R.font.changa_medium)))
                }
            }
        }
    }
}