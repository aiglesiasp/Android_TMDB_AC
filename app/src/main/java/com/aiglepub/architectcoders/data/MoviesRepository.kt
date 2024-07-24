package com.aiglepub.architectcoders.data

import com.aiglepub.architectcoders.data.datasource.local.MoviesLocalDataSourceImpl
import com.aiglepub.architectcoders.data.datasource.remote.MoviesRemoteDataSourceImpl
import com.aiglepub.architectcoders.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val moviesRemoteDataSource: MoviesRemoteDataSourceImpl,
    private val moviesLocalDataSource: MoviesLocalDataSourceImpl
) {

    val movies: Flow<List<Movie>> = moviesLocalDataSource.movies.onEach { localMovies->
        if (localMovies.isEmpty()) {
            val region = regionRepository.findLastRegion()
            val remoteMovies = moviesRemoteDataSource.fetchPopularMovies(region)
            moviesLocalDataSource.insertMovies(remoteMovies)
        }
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

    fun findMovieById(id: Int): Flow<Movie> = moviesLocalDataSource.getMovieById(id).onEach { localMovie ->
        if (localMovie == null) {
            val remoteMovie = moviesRemoteDataSource.findMovieById(id)
            moviesLocalDataSource.insertMovies(listOf(remoteMovie))
        }
    }.filterNotNull()

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

    suspend fun toggleFavorite(movie: Movie) {
        moviesLocalDataSource.insertMovies(listOf(movie.copy(favorite = !movie.favorite)))
    }
}
