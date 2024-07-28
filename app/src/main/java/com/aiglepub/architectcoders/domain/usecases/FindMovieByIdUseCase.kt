package com.aiglepub.architectcoders.domain.usecases

import com.aiglepub.architectcoders.domain.MoviesRepository
import com.aiglepub.architectcoders.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FindMovieByIdUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(id: Int): Flow<Movie> = moviesRepository.findMovieById(id)
}