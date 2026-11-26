package com.huellitas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val ColoresHuellitas = lightColorScheme(
    primary = Azul,
    onPrimary = Blanco,
    secondary = Amarillo,
    onSecondary = Azul,
    background = Fondo,
    onBackground = Azul,
    surface = Blanco,
    onSurface = Azul,
    surfaceVariant = Tarjeta,
    onSurfaceVariant = Azul,
    secondaryContainer = Tarjeta,
    onSecondaryContainer = Azul
)

@Composable
fun HuellitasTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColoresHuellitas,
        content = content
    )
}