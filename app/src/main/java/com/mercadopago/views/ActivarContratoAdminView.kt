package com.mercadopago.views

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.FindReplace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.mercadopago.R
import com.mercadopago.network.UIState
import com.mercadopago.viewmodels.ContratoAdminViewModel
import java.io.File
import java.io.FileOutputStream
import androidx.compose.foundation.isSystemInDarkTheme

private val FORMATOS_VALIDOS = setOf("image/jpeg", "image/jpg", "image/png")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivarContratoAdminView(
    navController: NavController,
    codigoSolicitud: String,
    contratoViewModel: ContratoAdminViewModel
) {
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()
    val labelColor = if (isDarkTheme) Color.White else Color.Black

    var numeroMeses by remember { mutableStateOf(6f) }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    var mimeType by remember { mutableStateOf<String?>(null) }

    var errorMeses by remember { mutableStateOf<String?>(null) }
    var errorImagen by remember { mutableStateOf<String?>(null) }

    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val activarState by contratoViewModel.activarState.collectAsState()

    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val tipo = context.contentResolver.getType(uri)
            imagenUri = uri
            mimeType = tipo
            scale = 1f
            offsetX = 0f
            offsetY = 0f

            errorImagen = if (tipo == null || tipo !in FORMATOS_VALIDOS) {
                "Formato no valido (.png, .jpg, .jpeg)"
            } else {
                null
            }
        }
    }

    LaunchedEffect(activarState) {
        when (val state = activarState) {
            is UIState.Success -> {
                navController.navigate("contrato-admin-exitoso") {
                    popUpTo("contratos")
                }
                contratoViewModel.resetActivarState()
            }
            is UIState.Error -> {
                errorImagen = state.message
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Activar Contrato",
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Light
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            HorizontalDivider()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Ingrese el plazo de meses",
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = labelColor
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "${numeroMeses.toInt()} meses",
                    fontFamily = FontFamily(Font(R.font.changa_medium)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color(0xFF35C0AB)
                )

                Slider(
                    value = numeroMeses,
                    onValueChange = {
                        numeroMeses = it
                        errorMeses = null
                    },
                    valueRange = 1f..24f,
                    steps = 22,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF35C0AB),
                        activeTrackColor = Color(0xFF35C0AB),
                        inactiveTrackColor = Color(0xFFE0E0E0)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("1 mes", color = Color.Gray, fontSize = 12.sp)
                    Text("24 meses", color = Color.Gray, fontSize = 12.sp)
                }

                if (errorMeses != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = errorMeses ?: "",
                        color = Color.Red,
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        pickerLauncher.launch("image/*")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35C0AB))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (imagenUri == null) "Subir imagen" else "Cambiar Imagen",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontSize = 16.sp
                        )
                        Icon(
                            imageVector = if (imagenUri == null) Icons.Default.FileUpload else Icons.Default.FindReplace,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                if (errorImagen != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = errorImagen ?: "",
                        color = Color.Red,
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontSize = 12.sp
                    )
                }

                if (imagenUri != null) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "VISTA PREVIA (puedes hacer zoom)",
                        fontFamily = FontFamily(Font(R.font.changa_medium)),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .background(Color(0xFFF0F0F0), RoundedCornerShape(12.dp))
                            .clip(RoundedCornerShape(12.dp))
                            .pointerInput(imagenUri) {
                                detectTransformGestures { _, pan, zoom, _ ->
                                    scale = (scale * zoom).coerceIn(1f, 5f)
                                    offsetX += pan.x
                                    offsetY += pan.y
                                }
                            }
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = imagenUri),
                            contentDescription = "Vista previa del contrato",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer(
                                    scaleX = scale,
                                    scaleY = scale,
                                    translationX = offsetX,
                                    translationY = offsetY
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        errorMeses = null
                        if (errorImagen != "Formato no valido (.png, .jpg, .jpeg)") errorImagen = null

                        var valido = true

                        if (numeroMeses < 1f) {
                            errorMeses = "Obligatorio"
                            valido = false
                        }

                        if (imagenUri == null) {
                            errorImagen = "Obligatorio"
                            valido = false
                        } else if (mimeType == null || mimeType !in FORMATOS_VALIDOS) {
                            errorImagen = "Formato no valido (.png, .jpg, .jpeg)"
                            valido = false
                        }

                        if (valido && imagenUri != null) {
                            val extension = when (mimeType) {
                                "image/png" -> "png"
                                else -> "jpg"
                            }
                            val archivoTemporal = File(context.cacheDir, "contrato_${System.currentTimeMillis()}.$extension")
                            context.contentResolver.openInputStream(imagenUri!!)?.use { input ->
                                FileOutputStream(archivoTemporal).use { output ->
                                    input.copyTo(output)
                                }
                            }
                            contratoViewModel.activarContrato(
                                codigoSolicitud,
                                numeroMeses.toInt(),
                                archivoTemporal,
                                mimeType ?: "image/jpeg"
                            )
                        }
                    },
                    enabled = activarState !is UIState.Loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35C0AB))
                ) {
                    if (activarState is UIState.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp))
                    } else {
                        Text(
                            "ACTIVAR",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.changa_medium)),
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}