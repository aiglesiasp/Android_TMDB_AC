package com.aiglepub.architectcoders.data.datasource.local

import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.data.datasource.local.database.MoviesDao
import kotlinx.coroutines.flow.Flow

class MoviesLocalDataSource(private var moviesDao: MoviesDao) {

    //GET ALL MOVIES
    val movies = moviesDao.getAllMovies()

    //GET MOVIES BY ID
    fun getMovieById(id: Int): Flow<Movie?> = moviesDao.getMovieById(id)

    //GET MOVIES COUNT
    suspend fun isEmpty() = moviesDao.countMovies() == 0

    //INSERT MOVIES
    suspend fun insertMovies(movies: List<Movie>) = moviesDao.insertMovies(movies)
}