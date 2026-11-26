package com.huellitas.data

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import com.huellitas.ui.Pantalla1
import com.huellitas.ui.Pantalla2
import com.huellitas.ui.Pantalla3
import com.huellitas.ui.Pantalla4
import com.huellitas.ui.Pantalla5
import com.huellitas.ui.PantallaBaseDatos
import com.huellitas.ui.HuellitasViewModel

@Composable
fun AppScreens() {

    var pantalla by rememberSaveable {
        mutableIntStateOf(1)
    }

    var tipo by rememberSaveable {
        mutableStateOf("Dueño")
    }

    // Inyección de dependencias con Hilt
    val huellitasViewModel: HuellitasViewModel = hiltViewModel()

    when (pantalla) {

        1 -> Pantalla1(
            tipo = tipo,
            cambiarTipo = { tipo = it },
            onContinuar = { pantalla = 2 }
        )

        2 -> Pantalla2(
            onIngresar = { pantalla = 4 },
            onCrearCuenta = { pantalla = 3 }
        )

        3 -> Pantalla3(
            tipo = tipo,
            cambiarTipo = { tipo = it },
            onRegresar = { pantalla = 2 },
            onRegistrar = { pantalla = 4 }
        )

        4 -> Pantalla4(
            onBuscar = { pantalla = 5 }
        )

        5 -> Pantalla5(
            onRegresar = { pantalla = 4 },
            onAbrirBaseDatos = { pantalla = 6 }
        )

        6 -> PantallaBaseDatos(
            onRegresar = { pantalla = 5 },
            viewModel = huellitasViewModel
        )
    }
}