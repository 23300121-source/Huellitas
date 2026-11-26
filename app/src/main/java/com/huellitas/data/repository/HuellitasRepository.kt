package com.huellitas.data.repository

import com.huellitas.data.database.AppDatabase
import com.huellitas.data.database.entity.*
import com.huellitas.domain.repository.HuellitasRepositoryInterface
import kotlinx.coroutines.flow.Flow

class HuellitasRepository(
    database: AppDatabase
) : HuellitasRepositoryInterface {

    private val mascotaDao = database.mascotaDao()

    private val solicitudDao = database.solicitudDao()

    private val cuidadorDao = database.cuidadorDao()

    private val usuarioDao = database.usuarioDao()


    override val mascotas: Flow<List<MascotaEntity>> =
        mascotaDao.obtenerTodas()

    override val solicitudes: Flow<List<SolicitudEntity>> =
        solicitudDao.obtenerTodas()

    override val cuidadores: Flow<List<CuidadorEntity>> =
        cuidadorDao.obtenerCuidadores()

    override val usuarios: Flow<List<UsuarioEntity>> =
        usuarioDao.obtenerUsuarios()

    override suspend fun insertarMascota(
        mascota: MascotaEntity
    ) {
        mascotaDao.insertar(mascota)
    }

    override suspend fun actualizarMascota(
        mascota: MascotaEntity
    ) {
        mascotaDao.actualizar(mascota)
    }

    override suspend fun eliminarMascota(
        mascota: MascotaEntity
    ) {
        mascotaDao.eliminar(mascota)
    }

    override suspend fun insertarSolicitud(
        solicitud: SolicitudEntity
    ) {
        solicitudDao.insertar(solicitud)
    }

    override suspend fun actualizarSolicitud(
        solicitud: SolicitudEntity
    ) {
        solicitudDao.actualizar(solicitud)
    }

    override suspend fun eliminarSolicitud(
        solicitud: SolicitudEntity
    ) {
        solicitudDao.eliminar(solicitud)
    }

    override suspend fun insertarCuidador(
        cuidador: CuidadorEntity
    ) {
        cuidadorDao.insertar(cuidador)
    }

    override suspend fun actualizarCuidador(
        cuidador: CuidadorEntity
    ) {
        cuidadorDao.actualizar(cuidador)
    }

    override suspend fun eliminarCuidador(
        cuidador: CuidadorEntity
    ) {
        cuidadorDao.eliminar(cuidador)
    }

    override suspend fun insertarUsuario(
        usuario: UsuarioEntity
    ) {
        usuarioDao.insertar(usuario)
    }

    override suspend fun actualizarUsuario(
        usuario: UsuarioEntity
    ) {
        usuarioDao.actualizar(usuario)
    }

    override suspend fun eliminarUsuario(
        usuario: UsuarioEntity
    ) {
        usuarioDao.eliminar(usuario)
    }
}