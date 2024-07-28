package com.aiglepub.architectcoders

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application()

/* {

    lateinit var db: MoviesDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, MoviesDatabase::class.java, "movies-db")
            .build()
    }
}*/