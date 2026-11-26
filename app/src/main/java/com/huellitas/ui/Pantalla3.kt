package com.huellitas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huellitas.R

@Composable
fun Pantalla3(
    tipo: String,
    cambiarTipo: (String) -> Unit,
    onRegresar: () -> Unit,
    onRegistrar: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var acepta by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        TextButton(onClick = onRegresar) {
            Text(stringResource(R.string.regresar))
        }

        Text(
            text = stringResource(R.string.crear_cuenta),
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(R.string.cuentanos),
            fontSize = 12.sp
        )

        Spacer(Modifier.height(15.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = tipo == "Dueño",
                onClick = { cambiarTipo("Dueño") },
                label = {
                    Text(stringResource(R.string.soy_dueno))
                }
            )

            FilterChip(
                selected = tipo == "Cuidador",
                onClick = { cambiarTipo("Cuidador") },
                label = {
                    Text(stringResource(R.string.soy_cuidador))
                }
            )
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = {
                Text(stringResource(R.string.nombre_completo))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = {
                Text(stringResource(R.string.correo))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = {
                Text(stringResource(R.string.telefono))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = {
                Text(stringResource(R.string.contrasena))
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = acepta,
                onCheckedChange = { acepta = it }
            )

            Text(
                text = stringResource(R.string.acepto),
                fontSize = 12.sp
            )
        }

        if (error) {
            Text(
                text = stringResource(R.string.completa_datos),
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }

        Spacer(Modifier.height(15.dp))

        BotonPrincipal(
            texto = stringResource(R.string.crear_cuenta),
            onClick = {
                if (
                    nombre.isNotBlank() &&
                    correo.contains("@") &&
                    telefono.isNotBlank() &&
                    contrasena.length >= 8 &&
                    acepta
                ) {
                    onRegistrar()
                } else {
                    error = true
                }
            }
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = stringResource(R.string.ya_tienes_cuenta),
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            fontSize = 12.sp
        )

        OutlinedButton(
            onClick = onRegresar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.iniciar_sesion))
        }
    }
}