package com.aiglepub.architectcoders.data

import com.aiglepub.architectcoders.data.datasource.local.MoviesLocalDataSource
import com.aiglepub.architectcoders.data.datasource.remote.MoviesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) {

    val movies: Flow<List<Movie>> = moviesLocalDataSource.movies.transform { localMovies ->
        val movies = localMovies.takeIf { it.isNotEmpty() }
            ?: moviesRemoteDataSource.fetchPopularMovies(regionRepository.findLastRegion()).also {
                moviesLocalDataSource.insertMovies(it)
            }
        emit(movies)
    }

    /*
    suspend fun fetchPopularMovies(): List<Movie> {
        if (moviesLocalDataSource.isEmpty()) {
            val region = regionRepository.findLastRegion()
            val movies = moviesRemoteDataSource.fetchPopularMovies(region)
            moviesLocalDataSource.insertMovies(movies)
        }
        return moviesLocalDataSource.getAllMovies()
    }
     */

    fun findMovieById(id: Int): Flow<Movie?> = moviesLocalDataSource.getMovieById(id).transform { localMovie ->
        val movie = localMovie.takeIf { it != null }
            ?: moviesRemoteDataSource.findMovieById(id).also {
                moviesLocalDataSource.insertMovies(listOf(it))
            }
        emit(movie)
    }

    /*
    suspend fun findMovieById(id: Int): Flow<Movie>
    {
        if(moviesLocalDataSource.getMovieById(id) == null) {
            val movie = moviesRemoteDataSource.findMovieById(id)
            moviesLocalDataSource.insertMovies(listOf(movie))
        }
        return checkNotNull(moviesLocalDataSource.getMovieById(id))
    }
     */
}
