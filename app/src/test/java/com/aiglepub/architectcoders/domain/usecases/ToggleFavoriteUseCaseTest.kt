package com.aiglepub.architectcoders.domain.usecases

import com.aiglepub.architectcoders.domain.MoviesRepository
import com.aiglepub.architectcoders.helpers.generateSampleMovie
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ToggleFavoriteUseCaseTest {
    @Test
    fun `Invoke should change movie favorite from repository`(): Unit = runBlocking {
        //GIVEN
        val movie = generateSampleMovie(1)
        val repository = mock<MoviesRepository>()
        val useCase = ToggleFavoriteUseCase(repository)

        //WHEN
        useCase(movie)

        //THEN
        verify(repository).toggleFavorite(movie)
    }
}