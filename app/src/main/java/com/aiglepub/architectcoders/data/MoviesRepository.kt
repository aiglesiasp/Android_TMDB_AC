package com.aiglepub.architectcoders.data

import com.aiglepub.architectcoders.data.datasource.MoviesRemoteDataSource

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) {

    suspend fun fetchPopularMovies(): List<Movie> = moviesRemoteDataSource.fetchPopularMovies(regionRepository.findLastRegion())

    suspend fun findMovieById(id: Int): Movie = moviesRemoteDataSource.findMovieById(id)
}
