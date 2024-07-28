package com.aiglepub.architectcoders.domain

import com.aiglepub.architectcoders.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    val movies: Flow<List<Movie>>
    fun findMovieById(id: Int): Flow<Movie>
    suspend fun toggleFavorite(movie: Movie)
}