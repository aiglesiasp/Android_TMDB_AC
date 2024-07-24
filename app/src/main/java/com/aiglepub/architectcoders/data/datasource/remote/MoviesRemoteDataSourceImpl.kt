package com.aiglepub.architectcoders.data.datasource.remote

import com.aiglepub.architectcoders.domain.entities.Movie
import com.aiglepub.architectcoders.data.datasource.remote.network.MovieService
import com.aiglepub.architectcoders.data.datasource.remote.network.RemoteMovie

interface MoviesRemoteDataSource {
    suspend fun fetchPopularMovies(region: String): List<Movie>
    suspend fun findMovieById(id: Int): Movie
}

class MoviesRemoteDataSourceImpl(
    private val movieService: MovieService,
) : MoviesRemoteDataSource {
    override suspend fun fetchPopularMovies(region: String): List<Movie> =
        movieService
            .fetchPopularMovies(region = region)
            .results
            .map { it.toDomainModel() }

    override suspend fun findMovieById(id: Int): Movie =
        movieService
            .fetchMovieById(id)
            .toDomainModel()
}

private fun RemoteMovie.toDomainModel(): Movie =
    Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        poster = "https://image.tmdb.org/t/p/w185/$posterPath",
        backdrop = "https://image.tmdb.org/t/p/w780/$backdropPath",
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        popularity = popularity,
        voteAverage = voteAverage,
        favorite = false

    )