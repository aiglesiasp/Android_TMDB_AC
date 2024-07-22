package com.aiglepub.architectcoders.domain.usecases

import com.aiglepub.architectcoders.domain.entities.Movie
import com.aiglepub.architectcoders.data.MoviesRepository
import kotlinx.coroutines.flow.Flow


class FindMovieByIdUseCase (
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<Movie> = moviesRepository.findMovieById(id)
}