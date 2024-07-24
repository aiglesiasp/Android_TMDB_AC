package com.aiglepub.architectcoders.data.remote

import com.aiglepub.architectcoders.domain.entities.Movie

interface MoviesRemoteDataSource {
    suspend fun fetchPopularMovies(region: String): List<Movie>
    suspend fun findMovieById(id: Int): Movie
}

