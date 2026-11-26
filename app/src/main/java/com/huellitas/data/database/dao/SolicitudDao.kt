package com.huellitas.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.huellitas.data.database.entity.SolicitudEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {

    @Insert
    suspend fun insertar(solicitud: SolicitudEntity)

    @Query("SELECT * FROM solicitudes ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<SolicitudEntity>>

    @Query("SELECT * FROM solicitudes WHERE mascotaId = :idMascota")
    fun obtenerPorMascota(
        idMascota: Int
    ): Flow<List<SolicitudEntity>>

    @Update
    suspend fun actualizar(solicitud: SolicitudEntity)

    @Delete
    suspend fun eliminar(solicitud: SolicitudEntity)
}