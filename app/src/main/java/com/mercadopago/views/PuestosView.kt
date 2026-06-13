package com.mercadopago.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.mercadopago.R
import com.mercadopago.models.PuestoCardModel
import com.mercadopago.models.PuestoFilterDto
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.PuestoViewModel

@Composable
fun PuestosView(
navController: NavController,
puestosViewModel: PuestoViewModel = PuestoViewModel()
){
        val puestosState = puestosViewModel.puestos.collectAsStateWithLifecycle()


  DetailedDrawer(  navController = navController) {
     padding ->

        Column(
            modifier = Modifier.padding(padding)

        ) {
            //tabs DE ESTADO - INICIO
            var state by remember { mutableIntStateOf(0) }
            val titles = listOf("DISPONIBLE", "OCUPADO")

            val selectedoption = titles[state]


            Column {
                SecondaryTabRow(selectedTabIndex = state) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = state == index,
                            onClick = { state = index

                                      puestosViewModel.searchPuesto(PuestoFilterDto("", "", titles[index], com.mercadopago.models.Paginator(0, 10)))


                                      },
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
            Spacer(modifier = Modifier.height(20.dp))
            //TABS DE ESTADO FIN
            //ROWS PUESTOS

            when(val state = puestosState.value){

            is UIState.Loading -> {

                Spacer(modifier =   Modifier.height(140.dp))
Column(
    modifier = Modifier.fillMaxWidth()
    , horizontalAlignment = Alignment.CenterHorizontally
) {
    CircularProgressIndicator(color = Color(0XFF35C0AB))

}


            }

                is UIState.Error ->{

                    Spacer(modifier =   Modifier.height(140.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth()
                        , horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(state.message)

                        Button(
                            onClick = {
                                puestosViewModel.searchPuesto(
                                    PuestoFilterDto(
                                        "",
                                        "",
                                        selectedoption,
                                        com.mercadopago.models.Paginator(0, 10)
                                    )
                                )

                            }
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
                is UIState.Success -> {
                    val puestos = state.data
                    Column(
                        modifier = Modifier.padding(10.dp,0.dp)
                    ) {
                        puestos.forEach { puesto ->
                            Row (
                                modifier = Modifier
                                    .shadow(1.dp)
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .height(80.dp)
                                , horizontalArrangement = Arrangement.Center

                            ){

                                Row(
                                    modifier = Modifier.fillMaxWidth(0.5f)
                                    , verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        //image
                                        Image(painter = painterResource(id = R.drawable.puesto)
                                            , contentDescription = "",
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Text(puesto.codigo,
                                            fontFamily = FontFamily(Font(R.font.changa_medium)))
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(puesto.descripcion,
                                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                                            fontSize = 16.sp
                                        )
                                        Row {
                                            Text(puesto.zona,
                                                modifier= Modifier

                                                    .background(Color(0XFFEDEDED)
                                                        ,
                                                        RoundedCornerShape(5.dp)
                                                    )
                                                    .padding(5.dp,2.5.dp
                                                    )
                                                ,

                                                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                                                fontSize = 8.sp,


                                                )
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Text("${puesto.areaM2}m²",
                                                modifier= Modifier

                                                    .background(Color(0XFFEDEDED)
                                                        ,
                                                        RoundedCornerShape(5.dp)
                                                    )
                                                    .padding(5.dp,2.5.dp
                                                    )
                                                ,

                                                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                                                fontSize = 8.sp,
                                            )
                                            Spacer(modifier = Modifier.width(5.dp))
                                            Text("${puesto.servicios.size} servicios",
                                                modifier= Modifier

                                                    .background(Color(0XFFEDEDED)
                                                        ,
                                                        RoundedCornerShape(5.dp)
                                                    )
                                                    .padding(5.dp,2.5.dp
                                                    )
                                                ,

                                                fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                                                fontSize = 8.sp,
                                            )
                                        }
                                    }

                                }

                                Spacer(modifier = Modifier.fillMaxWidth(0.37f))


                                Row (
                                    modifier = Modifier.fillMaxHeight(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "S/${puesto.total}",
                                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                                        fontSize = 18.sp
                                    )
                                    Button(onClick = {},
                                        colors = ButtonDefaults.buttonColors(Color.Transparent)) {
                                        Text(">",
                                            color = Color.Black,
                                            fontFamily = FontFamily(Font(R.font.inclusivesans_variablefont_wght)),
                                            fontSize = 16.sp
                                        )
                                    }
                                }



                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                    }
                }

            }






        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            // Contenido principal

            FloatingActionButton(
                onClick = {
                navController.navigate("crear-puesto")
                },
                containerColor = Color.White,
                shape = CircleShape
                ,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp,100.dp)
                    .size(50.dp)

            ) {
                Image(painterResource(id =R.drawable.pluspuesto), contentDescription = "",
                    modifier = Modifier.size(50.dp))
            }
        }
    }
}
