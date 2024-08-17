package com.aiglepub.architectcoders.fakes

import com.aiglepub.architectcoders.data.local.MoviesLocalDataSource
import com.aiglepub.architectcoders.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map

class FakeLocalDataSource : MoviesLocalDataSource {

    val inMemoryMovies = MutableStateFlow<List<Movie>>(emptyList())

    override val movies: Flow<List<Movie>> = inMemoryMovies

    override fun getMovieById(id: Int): Flow<Movie?> =
        inMemoryMovies.map { it.firstOrNull() { movie -> movie.id == id } }

    override suspend fun isEmpty(): Boolean {
        return (inMemoryMovies.count() == 0)
    }

    override suspend fun insertMovies(movies: List<Movie>) {
        inMemoryMovies.value = movies
    }
}