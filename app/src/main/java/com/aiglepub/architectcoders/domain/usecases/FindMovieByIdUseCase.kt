package com.aiglepub.architectcoders.domain.usecases

import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.data.MoviesRepository
import kotlinx.coroutines.flow.Flow


class FindMovieByIdUseCase (
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<Movie> = moviesRepository.findMovieById(id)
}