package com.aiglepub.architectcoders.ui.screens.home

import app.cash.turbine.test
import com.aiglepub.architectcoders.Result
import com.aiglepub.architectcoders.domain.usecases.FetchMoviesUseCase
import com.aiglepub.architectcoders.helpers.CoroutinesTestRule
import com.aiglepub.architectcoders.helpers.generateListMovies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var fetchMoviesUseCase: FetchMoviesUseCase

    private lateinit var vm: HomeViewModel

    @Before
    fun setUp() {
        vm = HomeViewModel(fetchMoviesUseCase)
    }

    @Test
    fun `Movies are not requested if UI is not ready`() = runTest {
        vm.state.first()
        runCurrent()

        verify(fetchMoviesUseCase, times(0)).invoke()
    }

    @Test
    fun `Movies are request if UI is ready`() = runTest {
        val movies = generateListMovies(1,2,3)
        whenever(fetchMoviesUseCase()).thenReturn(flowOf(movies))

        vm.onUiReady()

        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(movies), awaitItem())
        }
    }

    @Test
    fun `Error is propagated when request fails`() = runTest {
        val error = RuntimeException("Boom!")
        whenever(fetchMoviesUseCase()).thenThrow(error)

        vm.onUiReady()

        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            val exceptionMessage = (awaitItem() as Result.Error).exception.message
            assertEquals("Boom!", exceptionMessage)
        }
    }
}