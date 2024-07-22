package com.aiglepub.architectcoders.domain.usecases

import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.data.MoviesRepository

class ToggleFavoriteUseCase(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movie: Movie) {
        moviesRepository.toggleFavorite(movie)
    }
}