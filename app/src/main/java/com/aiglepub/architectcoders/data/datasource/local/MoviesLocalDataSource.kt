package com.aiglepub.architectcoders.data.datasource.local

import com.aiglepub.architectcoders.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {
    val movies: Flow<List<Movie>>
    fun getMovieById(id: Int): Flow<Movie?>
    suspend fun isEmpty(): Boolean
    suspend fun insertMovies(movies: List<Movie>)
}

