package com.aiglepub.architectcoders.data

class MoviesRepository(private val movieService: MovieService) {

    suspend fun fetchPopularMovies(region: String): List<Movie> =
        movieService
            .fetchPopularMovies(region)
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
        poster = "https://image.tmdb.org/t/p/w185/$posterPath"
    )