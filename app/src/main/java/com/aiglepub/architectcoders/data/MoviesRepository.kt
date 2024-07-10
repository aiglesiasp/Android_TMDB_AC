package com.aiglepub.architectcoders.data

import com.aiglepub.architectcoders.data.datasource.local.MoviesLocalDataSource
import com.aiglepub.architectcoders.data.datasource.remote.MoviesRemoteDataSource

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) {

    suspend fun fetchPopularMovies(): List<Movie> {
        if (moviesLocalDataSource.isEmpty()) {
            val region = regionRepository.findLastRegion()
            val movies = moviesRemoteDataSource.fetchPopularMovies(region)
            moviesLocalDataSource.insertMovies(movies)
        }
        return moviesLocalDataSource.getAllMovies()
    }

    suspend fun findMovieById(id: Int): Movie
    {
        if(moviesLocalDataSource.getMovieById(id) == null) {
            val movie = moviesRemoteDataSource.findMovieById(id)
            moviesLocalDataSource.insertMovies(listOf(movie))
        }
        return checkNotNull(moviesLocalDataSource.getMovieById(id))
    }
}
