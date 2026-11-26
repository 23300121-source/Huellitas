package com.huellitas.data.database.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.huellitas.data.database.entity.MascotaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MascotaDao {

    @Insert
    suspend fun insertar(mascota: MascotaEntity)

    @Query("SELECT * FROM mascotas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<MascotaEntity>>

    @Update
    suspend fun actualizar(mascota: MascotaEntity)

    @Delete
    suspend fun eliminar(mascota: MascotaEntity)
}
