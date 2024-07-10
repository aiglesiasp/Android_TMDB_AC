package com.aiglepub.architectcoders.data.datasource.local

import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.data.datasource.local.database.MoviesDao

class MoviesLocalDataSource(private var moviesDao: MoviesDao) {

    //GET ALL MOVIES
    suspend fun getAllMovies(): List<Movie> = moviesDao.getAllMovies()

    //GET MOVIES BY ID
    suspend fun getMovieById(id: Int): Movie? = moviesDao.getMovieById(id)

    //GET MOVIES COUNT
    suspend fun isEmpty() = moviesDao.countMovies() == 0

    //INSERT MOVIES
    suspend fun insertMovies(movies: List<Movie>) = moviesDao.insertMovies(movies)
}