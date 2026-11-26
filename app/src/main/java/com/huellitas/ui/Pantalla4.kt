package com.huellitas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huellitas.R

@Composable
fun Pantalla4(
    onBuscar: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            BarraHuellitas(
                seleccion = 0,
                onInicio = {}
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
            Text(
                text = stringResource(R.string.hola_ana),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(R.string.tus_mascotas),
                fontSize = 13.sp
            )

            Spacer(Modifier.height(15.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MascotaCard(
                    letra = "M",
                    nombre = stringResource(R.string.max),
                    tipo = stringResource(R.string.perro),
                    modifier = Modifier.weight(1f)
                )

                MascotaCard(
                    letra = "L",
                    nombre = stringResource(R.string.luna),
                    tipo = stringResource(R.string.gata),
                    modifier = Modifier.weight(1f)
                )

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("+", fontSize = 28.sp)

                        Text(
                            text = stringResource(R.string.agregar),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = stringResource(R.string.necesitas_ayuda),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    Text(
                        text = stringResource(R.string.encuentra_cuidador),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    Spacer(Modifier.height(12.dp))

                    Button(onClick = onBuscar) {
                        Text(stringResource(R.string.buscar))
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.servicios),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    R.string.paseo,
                    R.string.guarderia,
                    R.string.hospedaje,
                    R.string.visita
                ).forEach { recurso ->
                    Card(
                        onClick = onBuscar,
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor =
                                MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CirculoInicial(
                                letra = "•",
                                modifier = Modifier.size(30.dp)
                            )

                            Spacer(Modifier.height(5.dp))

                            Text(
                                text = stringResource(recurso),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(22.dp))

            Text(
                text = stringResource(R.string.proxima_reserva),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CirculoInicial("M")

                    Column {
                        Text(
                            text = stringResource(R.string.paseo_marco),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = stringResource(R.string.reserva_detalle),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MascotaCard(
    letra: String,
    nombre: String,
    tipo: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CirculoInicial(
                letra = letra,
                modifier = Modifier.size(38.dp)
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = nombre,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = tipo,
                fontSize = 10.sp
            )
        }
    }
}