
package com.huellitas.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huellitas.R

data class Cuidador(
    val inicial: String,
    @StringRes val nombre: Int,
    val servicio: String,
    val distancia: String,
    val precio: Int,
    val calificacion: String
)

@Composable
fun Pantalla5(
    onRegresar: () -> Unit,
    onAbrirBaseDatos: () -> Unit
) {

    var servicio by remember {
        mutableStateOf("Todos")
    }

    val cuidadores = listOf(
        Cuidador(
            "M", R.string.marco, "Paseo", "2 km", 120, "4.9"
        ),
        Cuidador(
            "S", R.string.sofia, "Guardería", "3 km", 250, "4.8"
        ),
        Cuidador(
            "I", R.string.ivan, "Guardería", "1 km", 180, "5.0"
        ),
        Cuidador(
            "P", R.string.paola, "Paseo", "4 km", 110, "4.7"
        )
    )

    val filtrados = cuidadores.filter {
        servicio == "Todos" || it.servicio == servicio
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            BarraHuellitas(
                seleccion = 0,
                onInicio = onRegresar
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(18.dp)
        ) {

            // BOTÓN REGRESAR
            TextButton(onClick = onRegresar) {
                Text(stringResource(R.string.regresar))
            }

            // TÍTULO
            Text(
                text = stringResource(R.string.cuidadores),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(R.string.paseo_cerca),
                fontSize = 12.sp
            )

            Spacer(Modifier.height(15.dp))

            // BOTÓN PARA ABRIR ROOM
            Button(
                onClick = onAbrirBaseDatos,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Administrar mascotas",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(15.dp))

            // FILTROS DE SERVICIOS
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                listOf(
                    "Todos" to R.string.servicios,
                    "Paseo" to R.string.paseo,
                    "Guardería" to R.string.guarderia,
                    "Hospedaje" to R.string.hospedaje
                ).forEach { (valor, recurso) ->

                    FilterChip(
                        selected = servicio == valor,
                        onClick = { servicio = valor },
                        label = {
                            Text(
                                text = stringResource(recurso),
                                fontSize = 11.sp
                            )
                        }
                    )
                }
            }

            Spacer(Modifier.height(15.dp))

            // VALIDAR RESULTADOS
            if (filtrados.isEmpty()) {
                Text(stringResource(R.string.sin_resultados))
            }

            // LISTADO DE CUIDADORES
            filtrados.forEach { cuidador ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {

                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        CirculoInicial(
                            letra = cuidador.inicial,
                            modifier = Modifier.size(48.dp)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = stringResource(cuidador.nombre),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )

                            val nombreServicio = when (
                                cuidador.servicio
                            ) {
                                "Paseo" ->
                                    stringResource(R.string.paseo)

                                "Guardería" ->
                                    stringResource(R.string.guarderia)

                                else ->
                                    stringResource(R.string.hospedaje)
                            }

                            Text(
                                text = stringResource(
                                    R.string.detalle_cuidador,
                                    nombreServicio,
                                    cuidador.distancia
                                ),
                                fontSize = 11.sp
                            )

                            Text(
                                text = stringResource(
                                    R.string.calificacion,
                                    cuidador.calificacion
                                ),
                                fontSize = 12.sp,
                                color =
                                    MaterialTheme.colorScheme.primary
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {

                            Text(
                                text = stringResource(
                                    R.string.precio,
                                    cuidador.precio
                                ),
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )

                            Text(
                                text = stringResource(R.string.por_dia),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
