package com.aiglepub.architectcoders.domain.usecases

import com.aiglepub.architectcoders.domain.MoviesRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ToggleFavoriteUseCaseTest {
    @Test
    fun `Invoke should change movie favorite from repository`() = runBlocking {
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