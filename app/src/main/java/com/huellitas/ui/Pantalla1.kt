package com.huellitas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun Pantalla1(
    tipo: String,
    cambiarTipo: (String) -> Unit,
    onContinuar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(65.dp))

        CirculoInicial(
            letra = "H",
            modifier = Modifier.size(70.dp),
            amarillo = true
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.app_name),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(R.string.lema),
            fontSize = 12.sp
        )

        Spacer(Modifier.height(65.dp))

        Text(
            text = stringResource(R.string.como_usar),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(Modifier.height(15.dp))

        TarjetaSeleccion(
            letra = "D",
            titulo = stringResource(R.string.soy_dueno),
            descripcion = stringResource(R.string.dueno_desc),
            seleccionado = tipo == "Dueño",
            onClick = { cambiarTipo("Dueño") }
        )

        Spacer(Modifier.height(12.dp))

        TarjetaSeleccion(
            letra = "C",
            titulo = stringResource(R.string.soy_cuidador),
            descripcion = stringResource(R.string.cuidador_desc),
            seleccionado = tipo == "Cuidador",
            onClick = { cambiarTipo("Cuidador") }
        )

        Spacer(Modifier.height(45.dp))

        BotonPrincipal(
            texto = stringResource(R.string.continuar),
            onClick = onContinuar
        )
    }
}