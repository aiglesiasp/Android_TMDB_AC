package com.aiglepub.architectcoders.data.datasource.remote

import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.data.datasource.remote.network.MovieService
import com.aiglepub.architectcoders.data.datasource.remote.network.RemoteMovie

class MoviesRemoteDataSource(
    private val movieService: MovieService,
) {
    suspend fun fetchPopularMovies(region: String): List<Movie> =
        movieService
            .fetchPopularMovies(region = region)
            .results
            .map { it.toDomainModel() }

    suspend fun findMovieById(id: Int): Movie =
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