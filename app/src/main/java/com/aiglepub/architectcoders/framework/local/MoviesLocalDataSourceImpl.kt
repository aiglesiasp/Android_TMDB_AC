package com.aiglepub.architectcoders.framework.local

import com.aiglepub.architectcoders.data.local.MoviesLocalDataSource
import com.aiglepub.architectcoders.domain.entities.Movie
import com.aiglepub.architectcoders.framework.local.database.MovieDb
import com.aiglepub.architectcoders.framework.local.database.MoviesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesLocalDataSourceImpl @Inject constructor(
    private var moviesDao: MoviesDao
) : MoviesLocalDataSource {

    //GET ALL MOVIES
    override val movies: Flow<List<Movie>> = moviesDao.getAllMovies().map { it.toDomainMovie() }

    //GET MOVIES BY ID
    override fun getMovieById(id: Int): Flow<Movie?> = moviesDao.getMovieById(id).map { it?.toDomainMovie() }

    //GET MOVIES COUNT
    override suspend fun isEmpty() = moviesDao.countMovies() == 0

    //INSERT MOVIES
    override suspend fun insertMovies(movies: List<Movie>) = moviesDao.insertMovies(movies.toDbMovie())
}

private fun MovieDb.toDomainMovie(): Movie {
    return Movie(
        id,
        title,
        overview,
        releaseDate,
        poster,
        backdrop,
        originalTitle,
        originalLanguage,
        popularity,
        voteAverage,
        favorite
    )
}

private fun List<MovieDb>.toDomainMovie(): List<Movie> {
    return map { it.toDomainMovie() }
}

private fun Movie.toDbMovie(): MovieDb {
    return MovieDb(
        id,
        title,
        overview,
        releaseDate,
        poster,
        backdrop,
        originalTitle,
        originalLanguage,
        popularity,
        voteAverage,
        favorite
    )
}

private fun List<Movie>.toDbMovie(): List<MovieDb> {
    return map { it.toDbMovie() }
}