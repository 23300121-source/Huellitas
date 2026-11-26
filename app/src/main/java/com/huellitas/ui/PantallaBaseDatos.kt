package com.huellitas.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.huellitas.data.database.entity.*

@Composable
fun PantallaBaseDatos(
    onRegresar: () -> Unit,
    viewModel: HuellitasViewModel
) {
    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    val mascotas = uiState.mascotas
    val solicitudes = uiState.solicitudes
    val cuidadores = uiState.cuidadores
    val usuarios = uiState.usuarios
    val mensaje = uiState.mensaje

    var pestana by rememberSaveable {
        mutableIntStateOf(0)
    }

    val pestanas = listOf(
        "Mascotas",
        "Solicitudes",
        "Cuidadores",
        "Usuarios"
    )

    val stateHolder = rememberSaveableStateHolder()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        TextButton(
            onClick = onRegresar
        ) {
            Text("← Regresar a cuidadores")
        }

        Text(
            text = "Huellitas - Base de datos",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(12.dp))

        ScrollableTabRow(
            selectedTabIndex = pestana,
            edgePadding = 0.dp
        ) {

            pestanas.forEachIndexed { indice, titulo ->

                Tab(
                    selected = pestana == indice,
                    onClick = {
                        pestana = indice
                        viewModel.limpiarMensaje()
                    },
                    text = {
                        Text(titulo)
                    }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        if (mensaje.isNotEmpty()) {

            Text(
                text = mensaje,
                color = if (mensaje.startsWith("Error")) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
        }

        stateHolder.SaveableStateProvider(pestana) {

            when (pestana) {

                0 -> FormularioMascotas(
                    mascotas = mascotas,
                    viewModel = viewModel
                )

                1 -> FormularioSolicitudes(
                    mascotas = mascotas,
                    solicitudes = solicitudes,
                    viewModel = viewModel
                )

                2 -> FormularioCuidadores(
                    cuidadores = cuidadores,
                    viewModel = viewModel
                )

                3 -> FormularioUsuarios(
                    usuarios = usuarios,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun FormularioMascotas(
    mascotas: List<MascotaEntity>,
    viewModel: HuellitasViewModel
) {

    FormularioCrud(

        titulo = "Mascota",

        campos = listOf(
            "Nombre",
            "Especie",
            "Raza",
            "Edad"
        ),

        registros = mascotas.map {

            RegistroCrud(
                id = it.id,
                valores = listOf(
                    it.nombre,
                    it.especie,
                    it.raza,
                    it.edad.toString()
                )
            )
        },

        validar = { datos ->

            val edad = datos[3].toIntOrNull()

            if (edad == null || edad < 0) {
                "Ingresa una edad válida"
            } else {
                null
            }
        },

        guardar = { id, datos ->

            viewModel.guardarMascota(
                nombre = datos[0],
                especie = datos[1],
                raza = datos[2],
                edad = datos[3].toInt(),
                id = id
            )
        },

        eliminar = { id ->

            mascotas.find {
                it.id == id
            }?.let {

                viewModel.eliminarMascota(it)
            }
        }
    )
}

@Composable
fun FormularioSolicitudes(
    mascotas: List<MascotaEntity>,
    solicitudes: List<SolicitudEntity>,
    viewModel: HuellitasViewModel
) {

    FormularioCrud(

        titulo = "Solicitud",

        campos = listOf(
            "ID de la mascota",
            "Solicitante",
            "Teléfono",
            "Estado"
        ),

        valoresIniciales = listOf(
            "",
            "",
            "",
            "Pendiente"
        ),

        informacion = {

            Text("Mascotas disponibles:")

            if (mascotas.isEmpty()) {

                Text(
                    "Primero registra una mascota"
                )
            }

            mascotas.forEach {

                Text(
                    "ID ${it.id}: ${it.nombre}"
                )
            }
        },

        registros = solicitudes.map {

            RegistroCrud(
                id = it.id,
                valores = listOf(
                    it.mascotaId.toString(),
                    it.solicitante,
                    it.telefono,
                    it.estado
                )
            )
        },

        validar = { datos ->

            val mascotaId = datos[0].toIntOrNull()

            if (mascotaId == null) {

                "Ingresa un ID válido"

            } else if (
                mascotas.none {
                    it.id == mascotaId
                }
            ) {

                "La mascota no existe"

            } else {

                null
            }
        },

        guardar = { id, datos ->

            viewModel.guardarSolicitud(
                mascotaId = datos[0].toInt(),
                solicitante = datos[1],
                telefono = datos[2],
                estado = datos[3],
                id = id
            )
        },

        eliminar = { id ->

            solicitudes.find {
                it.id == id
            }?.let {

                viewModel.eliminarSolicitud(it)
            }
        }
    )
}

@Composable
fun FormularioCuidadores(
    cuidadores: List<CuidadorEntity>,
    viewModel: HuellitasViewModel
) {

    FormularioCrud(

        titulo = "Cuidador",

        campos = listOf(
            "Nombre",
            "Teléfono",
            "Servicio",
            "Precio",
            "Disponible (Sí o No)"
        ),

        valoresIniciales = listOf(
            "",
            "",
            "",
            "",
            "Sí"
        ),

        registros = cuidadores.map {

            RegistroCrud(
                id = it.id,
                valores = listOf(
                    it.nombre,
                    it.telefono,
                    it.servicio,
                    it.precio.toString(),

                    if (it.disponible) {
                        "Sí"
                    } else {
                        "No"
                    }
                )
            )
        },

        validar = { datos ->

            val precio = datos[3].toDoubleOrNull()

            if (
                precio == null ||
                !precio.isFinite() ||
                precio < 0
            ) {

                "Ingresa un precio válido"

            } else if (
                datos[4].lowercase() !in
                listOf("sí", "si", "no")
            ) {

                "Escribe Sí o No"

            } else {

                null
            }
        },

        guardar = { id, datos ->

            viewModel.guardarCuidador(
                nombre = datos[0],
                telefono = datos[1],
                servicio = datos[2],
                precio = datos[3].toDouble(),
                disponible = datos[4].lowercase()
                        in listOf("sí", "si"),
                id = id
            )
        },

        eliminar = { id ->

            cuidadores.find {
                it.id == id
            }?.let {

                viewModel.eliminarCuidador(it)
            }
        }
    )
}

@Composable
fun FormularioUsuarios(
    usuarios: List<UsuarioEntity>,
    viewModel: HuellitasViewModel
) {

    FormularioCrud(

        titulo = "Usuario",

        campos = listOf(
            "Nombre",
            "Correo",
            "Teléfono",
            "Rol"
        ),

        valoresIniciales = listOf(
            "",
            "",
            "",
            "Dueño"
        ),

        registros = usuarios.map {

            RegistroCrud(
                id = it.id,
                valores = listOf(
                    it.nombre,
                    it.correo,
                    it.telefono,
                    it.rol
                )
            )
        },

        validar = { datos ->

            if (
                !android.util.Patterns.EMAIL_ADDRESS
                    .matcher(datos[1])
                    .matches()
            ) {

                "Ingresa un correo válido"

            } else if (
                datos[3].lowercase() !in
                listOf("dueño", "cuidador")
            ) {

                "El rol debe ser Dueño o Cuidador"

            } else {

                null
            }
        },

        guardar = { id, datos ->

            viewModel.guardarUsuario(
                nombre = datos[0],
                correo = datos[1],
                telefono = datos[2],
                rol = datos[3],
                id = id
            )
        },

        eliminar = { id ->

            usuarios.find {
                it.id == id
            }?.let {

                viewModel.eliminarUsuario(it)
            }
        }
    )
}

data class RegistroCrud(
    val id: Int,
    val valores: List<String>
)

@Composable
fun FormularioCrud(
    titulo: String,
    campos: List<String>,
    registros: List<RegistroCrud>,
    valoresIniciales: List<String> =
        List(campos.size) { "" },
    informacion: @Composable () -> Unit = {},
    validar: (List<String>) -> String?,
    guardar: (Int, List<String>) -> Unit,
    eliminar: (Int) -> Unit
) {

    var id by rememberSaveable {
        mutableIntStateOf(0)
    }

    var valores by rememberSaveable {
        mutableStateOf(
            ArrayList(valoresIniciales)
        )
    }

    var mensaje by rememberSaveable {
        mutableStateOf("")
    }

    var confirmarEliminar by rememberSaveable {
        mutableIntStateOf(0)
    }

    fun limpiar() {

        id = 0
        valores = ArrayList(valoresIniciales)
        mensaje = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        Spacer(Modifier.height(8.dp))

        Text(
            text = if (id == 0) {

                "Registrar ${titulo.lowercase()}"

            } else {

                "Editar ${titulo.lowercase()}"
            },

            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        informacion()

        campos.forEachIndexed { indice, campo ->

            OutlinedTextField(

                value = valores[indice],

                onValueChange = { nuevoValor ->

                    valores = ArrayList(valores).apply {
                        set(indice, nuevoValor)
                    }

                    mensaje = ""
                },

                label = {
                    Text(campo)
                },

                singleLine = true,

                keyboardOptions = KeyboardOptions(

                    keyboardType = when (campo) {

                        "Edad",
                        "ID de la mascota" ->
                            KeyboardType.Number

                        "Precio" ->
                            KeyboardType.Decimal

                        "Teléfono" ->
                            KeyboardType.Phone

                        "Correo" ->
                            KeyboardType.Email

                        else ->
                            KeyboardType.Text
                    }
                ),

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(4.dp))
        }

        Spacer(Modifier.height(12.dp))


        Button(

            onClick = {

                val datos = valores.map {
                    it.trim()
                }

                if (datos.any { it.isEmpty() }) {

                    mensaje = "Completa todos los campos"

                } else {

                    val error = validar(datos)

                    if (error != null) {

                        mensaje = error

                    } else {

                        guardar(id, datos)

                        mensaje = "Operación enviada"
                    }
                }
            },

            modifier = Modifier.fillMaxWidth()

        ) {

            Text(
                if (id == 0) {

                    "Guardar ${titulo.lowercase()}"

                } else {

                    "Actualizar ${titulo.lowercase()}"
                }
            )
        }

        TextButton(
            onClick = {
                limpiar()
            }
        ) {

            Text(
                if (id == 0) {

                    "Limpiar campos"

                } else {

                    "Cancelar edición"
                }
            )
        }

        if (mensaje.isNotEmpty()) {

            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.primary
            )
        }

        HorizontalDivider(
            modifier = Modifier.padding(
                vertical = 12.dp
            )
        )

        Text(
            text = "Registros almacenados: ${registros.size}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        if (registros.isEmpty()) {

            Text("No hay registros")
        }

        registros.forEach { registro ->

            Card(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)

            ) {

                Column(
                    modifier = Modifier.padding(14.dp)
                ) {

                    Text(
                        text = "$titulo #${registro.id}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    registro.valores.forEachIndexed {
                            indice, valor ->

                        Text(
                            "${campos[indice]}: $valor"
                        )
                    }

                    Row {


                        TextButton(

                            onClick = {

                                id = registro.id

                                valores = ArrayList(
                                    registro.valores
                                )

                                mensaje = ""
                            }

                        ) {

                            Text("Editar")
                        }


                        TextButton(

                            onClick = {

                                confirmarEliminar =
                                    registro.id
                            }

                        ) {

                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }


    if (confirmarEliminar != 0) {

        AlertDialog(

            onDismissRequest = {

                confirmarEliminar = 0
            },

            title = {

                Text("Eliminar registro")
            },

            text = {

                Text(
                    if (titulo == "Mascota") {

                        "También se eliminarán las solicitudes de esta mascota."

                    } else {

                        "¿Deseas eliminar este registro?"
                    }
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        eliminar(confirmarEliminar)

                        if (id == confirmarEliminar) {

                            limpiar()
                        }

                        confirmarEliminar = 0
                    }

                ) {

                    Text("Eliminar")
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        confirmarEliminar = 0
                    }

                ) {

                    Text("Cancelar")
                }
            }
        )
    }
}
