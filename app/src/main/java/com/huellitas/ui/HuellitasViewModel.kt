
package com.huellitas.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huellitas.data.database.entity.*
import com.huellitas.domain.repository.HuellitasRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HuellitasViewModel @Inject constructor(
    private val repository: HuellitasRepositoryInterface
) : ViewModel() {

    private val _mensaje = MutableStateFlow("")

    // Estado de la interfaz - UDF y Observer

    val uiState: StateFlow<HuellitasUiState> = combine(
        repository.mascotas,
        repository.solicitudes,
        repository.cuidadores,
        repository.usuarios,
        _mensaje
    ) { mascotas, solicitudes, cuidadores, usuarios, mensaje ->

        HuellitasUiState(
            mascotas = mascotas,
            solicitudes = solicitudes,
            cuidadores = cuidadores,
            usuarios = usuarios,
            mensaje = mensaje
        )

    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HuellitasUiState()
    )

    // Ejecutar operaciones de la base de datos

    private fun ejecutar(operacion: suspend () -> Unit) {

        viewModelScope.launch {

            try {
                operacion()

                _mensaje.value =
                    "Operación realizada correctamente"

            } catch (e: CancellationException) {
                throw e

            } catch (e: Exception) {

                _mensaje.value =
                    "Error: ${e.message}"
            }
        }
    }

    fun limpiarMensaje() {
        _mensaje.value = ""
    }

    // =====================================
    // VALIDACIONES
    // =====================================

    private fun telefonoValido(telefono: String): Boolean {

        return telefono.trim()
            .matches(Regex("[0-9]{10}"))
    }

    private fun correoValido(correo: String): Boolean {

        return android.util.Patterns.EMAIL_ADDRESS
            .matcher(correo.trim())
            .matches()
    }

    // =====================================
    // CRUD MASCOTAS
    // =====================================

    fun guardarMascota(
        nombre: String,
        especie: String,
        raza: String,
        edad: Int,
        id: Int? = null
    ) {

        if (
            nombre.isBlank() ||
            especie.isBlank() ||
            raza.isBlank()
        ) {
            _mensaje.value = "Completa todos los campos"
            return
        }

        if (edad < 0) {
            _mensaje.value = "Ingresa una edad válida"
            return
        }

        val mascota = MascotaEntity(
            id = id ?: 0,
            nombre = nombre.trim(),
            especie = especie.trim(),
            raza = raza.trim(),
            edad = edad
        )

        ejecutar {

            if (id == null || id == 0) {

                repository.insertarMascota(mascota)

            } else {

                repository.actualizarMascota(mascota)
            }
        }
    }

    fun actualizarMascota(mascota: MascotaEntity) {

        guardarMascota(
            nombre = mascota.nombre,
            especie = mascota.especie,
            raza = mascota.raza,
            edad = mascota.edad,
            id = mascota.id
        )
    }

    fun eliminarMascota(mascota: MascotaEntity) {

        ejecutar {
            repository.eliminarMascota(mascota)
        }
    }

    // =====================================
    // CRUD SOLICITUDES
    // =====================================

    fun guardarSolicitud(
        mascotaId: Int,
        solicitante: String,
        telefono: String,
        estado: String,
        id: Int? = null
    ) {

        if (
            solicitante.isBlank() ||
            telefono.isBlank() ||
            estado.isBlank()
        ) {
            _mensaje.value = "Completa todos los campos"
            return
        }

        if (mascotaId <= 0) {
            _mensaje.value = "Ingresa un ID válido"
            return
        }

        if (!telefonoValido(telefono)) {

            _mensaje.value =
                "Ingresa un teléfono válido de 10 dígitos"

            return
        }

        val solicitud = SolicitudEntity(
            id = id ?: 0,
            mascotaId = mascotaId,
            solicitante = solicitante.trim(),
            telefono = telefono.trim(),
            estado = estado.trim()
        )

        ejecutar {

            val mascotas = repository.mascotas.first()

            if (mascotas.none { it.id == mascotaId }) {

                throw IllegalArgumentException(
                    "La mascota no existe"
                )
            }

            if (id == null || id == 0) {

                repository.insertarSolicitud(solicitud)

            } else {

                repository.actualizarSolicitud(solicitud)
            }
        }
    }

    fun actualizarSolicitud(solicitud: SolicitudEntity) {

        guardarSolicitud(
            mascotaId = solicitud.mascotaId,
            solicitante = solicitud.solicitante,
            telefono = solicitud.telefono,
            estado = solicitud.estado,
            id = solicitud.id
        )
    }

    fun eliminarSolicitud(solicitud: SolicitudEntity) {

        ejecutar {
            repository.eliminarSolicitud(solicitud)
        }
    }

    // =====================================
    // CRUD CUIDADORES
    // =====================================

    fun guardarCuidador(
        nombre: String,
        telefono: String,
        servicio: String,
        precio: Double,
        disponible: Boolean,
        id: Int? = null
    ) {

        if (
            nombre.isBlank() ||
            telefono.isBlank() ||
            servicio.isBlank()
        ) {
            _mensaje.value = "Completa todos los campos"
            return
        }

        if (!telefonoValido(telefono)) {

            _mensaje.value =
                "Ingresa un teléfono válido de 10 dígitos"

            return
        }

        if (!precio.isFinite() || precio < 0) {

            _mensaje.value = "Ingresa un precio válido"

            return
        }

        val cuidador = CuidadorEntity(
            id = id ?: 0,
            nombre = nombre.trim(),
            telefono = telefono.trim(),
            servicio = servicio.trim(),
            precio = precio,
            disponible = disponible
        )

        ejecutar {

            if (id == null || id == 0) {

                repository.insertarCuidador(cuidador)

            } else {

                repository.actualizarCuidador(cuidador)
            }
        }
    }

    fun actualizarCuidador(cuidador: CuidadorEntity) {

        guardarCuidador(
            nombre = cuidador.nombre,
            telefono = cuidador.telefono,
            servicio = cuidador.servicio,
            precio = cuidador.precio,
            disponible = cuidador.disponible,
            id = cuidador.id
        )
    }

    fun eliminarCuidador(cuidador: CuidadorEntity) {

        ejecutar {
            repository.eliminarCuidador(cuidador)
        }
    }

    // =====================================
    // CRUD USUARIOS
    // =====================================

    fun guardarUsuario(
        nombre: String,
        correo: String,
        telefono: String,
        rol: String,
        id: Int? = null
    ) {

        if (
            nombre.isBlank() ||
            correo.isBlank() ||
            telefono.isBlank() ||
            rol.isBlank()
        ) {
            _mensaje.value = "Completa todos los campos"
            return
        }

        if (!correoValido(correo)) {

            _mensaje.value = "Ingresa un correo válido"

            return
        }

        if (!telefonoValido(telefono)) {

            _mensaje.value =
                "Ingresa un teléfono válido de 10 dígitos"

            return
        }

        val usuario = UsuarioEntity(
            id = id ?: 0,
            nombre = nombre.trim(),
            correo = correo.trim(),
            telefono = telefono.trim(),
            rol = rol.trim()
        )

        ejecutar {

            if (id == null || id == 0) {

                repository.insertarUsuario(usuario)

            } else {

                repository.actualizarUsuario(usuario)
            }
        }
    }

    fun actualizarUsuario(usuario: UsuarioEntity) {

        guardarUsuario(
            nombre = usuario.nombre,
            correo = usuario.correo,
            telefono = usuario.telefono,
            rol = usuario.rol,
            id = usuario.id
        )
    }

    fun eliminarUsuario(usuario: UsuarioEntity) {

        ejecutar {
            repository.eliminarUsuario(usuario)
        }
    }
}
