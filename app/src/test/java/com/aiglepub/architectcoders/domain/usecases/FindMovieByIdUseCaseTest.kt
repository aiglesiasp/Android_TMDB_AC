package com.aiglepub.architectcoders.domain.usecases

import com.aiglepub.architectcoders.helpers.generateSampleMovie
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindMovieByIdUseCaseTest {
    @Test
    fun `Invoke should return movie by ID from repository`() {
        //GIVEN
        val movieFlow = flowOf(generateSampleMovie(1))
        val useCase = FindMovieByIdUseCase(mock {
            on { findMovieById(1) } doReturn movieFlow
        })

        //WHEN
        val result = useCase(1)

        //THEN
        assertEquals(movieFlow, result)
    }
}