package com.huellitas.ui

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.huellitas.R

@Composable
fun Pantalla2(
    onIngresar: () -> Unit,
    onCrearCuenta: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Spacer(Modifier.height(35.dp))

        Text(
            text = stringResource(R.string.bienvenido),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(R.string.inicia_para_continuar),
            fontSize = 12.sp
        )

        Spacer(Modifier.height(25.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                error = false
            },
            label = {
                Text(stringResource(R.string.correo))
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                error = false
            },
            label = {
                Text(stringResource(R.string.contrasena))
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        TextButton(
            onClick = {},
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                stringResource(R.string.olvidaste),
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

        Spacer(Modifier.height(12.dp))

        BotonPrincipal(
            texto = stringResource(R.string.iniciar_sesion),
            onClick = {
                if (correo.contains("@") &&
                    contrasena.isNotBlank()
                ) {
                    onIngresar()
                } else {
                    error = true
                }
            }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onCrearCuenta,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(stringResource(R.string.crear_cuenta))
        }
    }
}