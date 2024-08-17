package com.aiglepub.architectcoders.data

import com.aiglepub.architectcoders.data.local.MoviesLocalDataSource
import com.aiglepub.architectcoders.data.remote.MoviesRemoteDataSource
import com.aiglepub.architectcoders.domain.MoviesRepository
import com.aiglepub.architectcoders.domain.RegionRepository
import com.aiglepub.architectcoders.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val regionRepository: RegionRepository,
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) : MoviesRepository {

    override val movies: Flow<List<Movie>>
        get() = moviesLocalDataSource.movies.onEach { localMovies->
        if (localMovies.isEmpty()) {
            val region = regionRepository.findLastRegion()
            val remoteMovies = moviesRemoteDataSource.fetchPopularMovies(region)
            moviesLocalDataSource.insertMovies(remoteMovies)
        }
    }

    override fun findMovieById(id: Int): Flow<Movie> = moviesLocalDataSource.getMovieById(id).onEach { localMovie ->
        if (localMovie == null) {
            val remoteMovie = moviesRemoteDataSource.findMovieById(id)
            moviesLocalDataSource.insertMovies(listOf(remoteMovie))
        }
    }.filterNotNull()


    override suspend fun toggleFavorite(movie: Movie) {
        moviesLocalDataSource.insertMovies(listOf(movie.copy(favorite = !movie.favorite)))
    }
}
