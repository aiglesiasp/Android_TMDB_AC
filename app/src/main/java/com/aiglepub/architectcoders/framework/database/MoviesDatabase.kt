package com.aiglepub.architectcoders.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieDb::class], version = 1, exportSchema = false)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}