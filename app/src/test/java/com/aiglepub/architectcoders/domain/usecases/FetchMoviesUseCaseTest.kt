package com.aiglepub.architectcoders.domain.usecases

import com.aiglepub.architectcoders.helpers.generateListMovies
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FetchMoviesUseCaseTest {

    @Test
    fun `Invoke should return movies from repository`() {
        //GIVEN
        val moviesFlow = flowOf(generateListMovies(1,2,3))
        val useCase = FetchMoviesUseCase(mock {
            on { movies } doReturn moviesFlow
        })

        //WHEN
        val result = useCase()

        //THEN
        assertEquals(moviesFlow, result)
    }
}