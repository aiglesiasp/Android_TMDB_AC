package com.aiglepub.architectcoders.di.local.database

import android.content.Context
import androidx.room.Room
import com.aiglepub.architectcoders.framework.local.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(context, MoviesDatabase::class.java, "movies-db").build()
    }

    @Provides
    fun provideMoviesDao(database: MoviesDatabase) = database.moviesDao()

}