package com.aiglepub.architectcoders.data

class MoviesRepository(
    private val movieService: MovieService,
    private val regionRepository: RegionRepository
) {
    suspend fun fetchPopularMovies(): List<Movie> =
        movieService
            .fetchPopularMovies(regionRepository.findLastRegion())
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
        voteAverage = voteAverage
    )