package com.huellitas.domain.repository

import com.huellitas.data.database.entity.MascotaEntity
import com.huellitas.data.database.entity.SolicitudEntity
import com.huellitas.data.database.entity.CuidadorEntity
import com.huellitas.data.database.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

interface HuellitasRepositoryInterface {


    val mascotas: Flow<List<MascotaEntity>>

    val solicitudes: Flow<List<SolicitudEntity>>

    val cuidadores: Flow<List<CuidadorEntity>>

    val usuarios: Flow<List<UsuarioEntity>>


    suspend fun insertarMascota(
        mascota: MascotaEntity
    )

    suspend fun actualizarMascota(
        mascota: MascotaEntity
    )

    suspend fun eliminarMascota(
        mascota: MascotaEntity
    )

    suspend fun insertarSolicitud(
        solicitud: SolicitudEntity
    )

    suspend fun actualizarSolicitud(
        solicitud: SolicitudEntity
    )

    suspend fun eliminarSolicitud(
        solicitud: SolicitudEntity
    )


    suspend fun insertarCuidador(
        cuidador: CuidadorEntity
    )

    suspend fun actualizarCuidador(
        cuidador: CuidadorEntity
    )

    suspend fun eliminarCuidador(
        cuidador: CuidadorEntity
    )


    suspend fun insertarUsuario(
        usuario: UsuarioEntity
    )

    suspend fun actualizarUsuario(
        usuario: UsuarioEntity
    )

    suspend fun eliminarUsuario(
        usuario: UsuarioEntity
    )
}