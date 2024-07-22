package com.aiglepub.architectcoders.data.datasource.local

import com.aiglepub.architectcoders.domain.entities.Movie
import com.aiglepub.architectcoders.data.datasource.local.database.MoviesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesLocalDataSource(private var moviesDao: MoviesDao) {

    //GET ALL MOVIES
    val movies: Flow<List<Movie>> = moviesDao.getAllMovies().map { it.toDomainMovie() }

    //GET MOVIES BY ID
    fun getMovieById(id: Int): Flow<Movie?> = moviesDao.getMovieById(id).map { it?.toDomainMovie() }

    //GET MOVIES COUNT
    suspend fun isEmpty() = moviesDao.countMovies() == 0

    //INSERT MOVIES
    suspend fun insertMovies(movies: List<Movie>) = moviesDao.insertMovies(movies.toDbMovie())
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