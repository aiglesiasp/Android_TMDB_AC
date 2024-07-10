package com.aiglepub.architectcoders.data.datasource.local.database

import androidx.room.Database
import com.aiglepub.architectcoders.data.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesDatabase {
    abstract fun moviesDao(): MoviesDao
}