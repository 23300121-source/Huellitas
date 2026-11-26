package com.huellitas.ui

import com.huellitas.data.database.entity.MascotaEntity
import com.huellitas.data.database.entity.SolicitudEntity
import com.huellitas.data.database.entity.CuidadorEntity
import com.huellitas.data.database.entity.UsuarioEntity

data class HuellitasUiState(

    val mascotas: List<MascotaEntity> = emptyList(),

    val solicitudes: List<SolicitudEntity> = emptyList(),

    val cuidadores: List<CuidadorEntity> = emptyList(),

    val usuarios: List<UsuarioEntity> = emptyList(),

    val mensaje: String = ""
)