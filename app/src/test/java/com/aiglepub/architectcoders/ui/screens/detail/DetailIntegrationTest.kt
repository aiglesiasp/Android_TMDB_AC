package com.aiglepub.architectcoders.ui.screens.detail

import app.cash.turbine.test
import com.aiglepub.architectcoders.Result
import com.aiglepub.architectcoders.domain.usecases.FindMovieByIdUseCase
import com.aiglepub.architectcoders.domain.usecases.ToggleFavoriteUseCase
import com.aiglepub.architectcoders.helpers.CoroutinesTestRule
import com.aiglepub.architectcoders.helpers.buildMoviesRepositoryWith
import com.aiglepub.architectcoders.helpers.generateListMovies
import com.aiglepub.architectcoders.helpers.generateSampleMovie
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailIntegrationTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var vm: DetailViewModel

    @Before
    fun setUp() {
        val moviesRepository = buildMoviesRepositoryWith(localData = generateListMovies(1,2,3))
        val useCaseFind = FindMovieByIdUseCase(moviesRepository)
        val useCaseToggle = ToggleFavoriteUseCase(moviesRepository)

        vm = DetailViewModel(2, useCaseFind, useCaseToggle)
    }

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(generateSampleMovie(2)), awaitItem())
        }
    }

    @Test
    fun `Favorite is updated in local data source`() = runTest {
        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(generateSampleMovie(2)), awaitItem())

            vm.onAction(DetailAction.FavoriteClick)
            runCurrent()

            assertEquals(Result.Success(generateSampleMovie(2).copy(favorite = false)), awaitItem())
        }
    }
}