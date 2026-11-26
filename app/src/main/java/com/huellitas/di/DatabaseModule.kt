package com.huellitas.di

import android.content.Context
import com.huellitas.data.database.AppDatabase
import com.huellitas.data.repository.HuellitasRepository
import com.huellitas.domain.repository.HuellitasRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun proporcionarBaseDatos(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.obtenerBaseDatos(context)
    }

    @Provides
    @Singleton
    fun proporcionarRepositorio(
        database: AppDatabase
    ): HuellitasRepositoryInterface {
        return HuellitasRepository(database)
    }
}