package com.huellitas.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

import com.huellitas.data.database.dao.MascotaDao
import com.huellitas.data.database.dao.SolicitudDao
import com.huellitas.data.database.dao.CuidadorDao
import com.huellitas.data.database.dao.UsuarioDao

import com.huellitas.data.database.entity.MascotaEntity
import com.huellitas.data.database.entity.SolicitudEntity
import com.huellitas.data.database.entity.CuidadorEntity
import com.huellitas.data.database.entity.UsuarioEntity

@Database(
    entities = [
        MascotaEntity::class,
        SolicitudEntity::class,
        CuidadorEntity::class,
        UsuarioEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mascotaDao(): MascotaDao

    abstract fun solicitudDao(): SolicitudDao

    abstract fun cuidadorDao(): CuidadorDao

    abstract fun usuarioDao(): UsuarioDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Agregar tabla cuidadores
        private val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS cuidadores (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        nombre TEXT NOT NULL,
                        telefono TEXT NOT NULL,
                        servicio TEXT NOT NULL,
                        precio REAL NOT NULL,
                        disponible INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        // Agregar tabla usuarios
        private val MIGRATION_2_3 = object : Migration(2, 3) {

            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS usuarios (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        nombre TEXT NOT NULL,
                        correo TEXT NOT NULL,
                        telefono TEXT NOT NULL,
                        rol TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        // Patrón Singleton
        fun obtenerBaseDatos(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "huellitas_db"
                )
                    .addMigrations(
                        MIGRATION_1_2,
                        MIGRATION_2_3
                    )
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}