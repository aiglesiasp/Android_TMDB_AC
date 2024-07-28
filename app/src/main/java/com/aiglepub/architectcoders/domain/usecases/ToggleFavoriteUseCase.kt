package com.aiglepub.architectcoders.domain.usecases

import com.aiglepub.architectcoders.domain.MoviesRepository
import com.aiglepub.architectcoders.domain.entities.Movie
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie) {
        moviesRepository.toggleFavorite(movie)
    }
}