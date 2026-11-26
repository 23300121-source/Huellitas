package com.huellitas.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huellitas.R

@Composable
fun CirculoInicial(
    letra: String,
    modifier: Modifier = Modifier,
    amarillo: Boolean = false
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(
                if (amarillo) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.tertiaryContainer
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letra,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun BotonPrincipal(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(25.dp)
    ) {
        Text(
            text = texto,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun TarjetaSeleccion(
    letra: String,
    titulo: String,
    descripcion: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        border = if (seleccionado) {
            BorderStroke(
                2.dp,
                MaterialTheme.colorScheme.secondary
            )
        } else {
            null
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CirculoInicial(letra = letra)

            Column {
                Text(
                    text = titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    text = descripcion,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
fun BarraHuellitas(
    seleccion: Int,
    onInicio: () -> Unit,
    onReservas: () -> Unit = {},
    onMensajes: () -> Unit = {},
    onPerfil: () -> Unit = {}
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        NavigationBarItem(
            selected = seleccion == 0,
            onClick = onInicio,
            icon = { Text("⌂", fontSize = 20.sp) },
            label = { Text(stringResource(R.string.inicio)) }
        )

        NavigationBarItem(
            selected = seleccion == 1,
            onClick = onReservas,
            icon = { Text("▣", fontSize = 20.sp) },
            label = { Text(stringResource(R.string.reservas)) }
        )

        NavigationBarItem(
            selected = seleccion == 2,
            onClick = onMensajes,
            icon = { Text("✉", fontSize = 20.sp) },
            label = { Text(stringResource(R.string.mensajes)) }
        )

        NavigationBarItem(
            selected = seleccion == 3,
            onClick = onPerfil,
            icon = { Text("♙", fontSize = 20.sp) },
            label = { Text(stringResource(R.string.perfil)) }
        )
    }
}