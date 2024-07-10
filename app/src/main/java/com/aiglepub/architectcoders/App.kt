package com.aiglepub.architectcoders

import android.app.Application
import androidx.room.Room
import com.aiglepub.architectcoders.data.datasource.local.database.MoviesDatabase

class App: Application() {

    lateinit var db: MoviesDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, MoviesDatabase::class.java, "movies-db")
            .build()
    }
}