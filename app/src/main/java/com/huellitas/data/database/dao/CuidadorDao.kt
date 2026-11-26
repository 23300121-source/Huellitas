package com.huellitas.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.huellitas.data.database.entity.CuidadorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CuidadorDao {
    @Insert
    suspend fun insertar(cuidador: CuidadorEntity)

    @Query("SELECT * FROM cuidadores")
    fun obtenerCuidadores(): Flow<List<CuidadorEntity>>

    @Update
    suspend fun actualizar(cuidador: CuidadorEntity)
    @Delete
    suspend fun eliminar(cuidador: CuidadorEntity)
}