package com.aiglepub.architectcoders.fakes

import com.aiglepub.architectcoders.data.remote.MoviesRemoteDataSource
import com.aiglepub.architectcoders.domain.entities.Movie
import com.aiglepub.architectcoders.helpers.generateListMovies

class FakeRemoteDataSource : MoviesRemoteDataSource {

    var movies = generateListMovies(1,2,3,4)

    override suspend fun fetchPopularMovies(region: String): List<Movie> = movies

    override suspend fun findMovieById(id: Int): Movie = movies.first { it.id == id }
}