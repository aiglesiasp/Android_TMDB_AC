package com.aiglepub.architectcoders.ui.screens.detail

import app.cash.turbine.test
import com.aiglepub.architectcoders.Result
import com.aiglepub.architectcoders.domain.usecases.FindMovieByIdUseCase
import com.aiglepub.architectcoders.domain.usecases.ToggleFavoriteUseCase
import com.aiglepub.architectcoders.helpers.CoroutinesTestRule
import com.aiglepub.architectcoders.helpers.generateSampleMovie
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var findMovieByIdUseCase: FindMovieByIdUseCase

    @Mock
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    private lateinit var vm: DetailViewModel

    private val movie = generateSampleMovie(2)

    @Before
    fun setUp() {
        whenever(findMovieByIdUseCase(2)).thenReturn(flowOf(movie))
        vm = DetailViewModel(2, findMovieByIdUseCase, toggleFavoriteUseCase)
    }

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(movie), awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Favorite action calls the corresponding use case`() = runTest(coroutinesTestRule.testDispatcher) {
        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(movie), awaitItem())

            vm.onAction(DetailAction.FavoriteClick)
            runCurrent()

            verify(toggleFavoriteUseCase).invoke(any())
        }
    }
}