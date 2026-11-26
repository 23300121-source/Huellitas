package com.huellitas.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cuidadores")
data class CuidadorEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val telefono: String,
    val servicio: String,
    val precio: Double,
    val disponible: Boolean = true
)