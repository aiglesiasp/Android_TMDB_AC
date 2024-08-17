package com.aiglepub.architectcoders.ui.screens.home

import app.cash.turbine.test
import com.aiglepub.architectcoders.Result
import com.aiglepub.architectcoders.domain.entities.Movie
import com.aiglepub.architectcoders.domain.usecases.FetchMoviesUseCase
import com.aiglepub.architectcoders.helpers.CoroutinesTestRule
import com.aiglepub.architectcoders.helpers.buildMoviesRepositoryWith
import com.aiglepub.architectcoders.helpers.buildMoviesViewModelWith
import com.aiglepub.architectcoders.helpers.generateListMovies
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeIntegrationTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `Data is loaded from server when local data source is empty`() = runTest {
        val remoteData = generateListMovies(1, 2, 3)
        val vm = buildMoviesViewModelWith(remoteData = remoteData)

        vm.onUiReady()

        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(emptyList<Movie>()), awaitItem())
            assertEquals(Result.Success(remoteData), awaitItem())
        }
    }

    @Test
    fun `Data us loaded from local source when available`() = runTest {
        val localData = generateListMovies(1, 2, 3)
        val vm = buildMoviesViewModelWith(localData = localData)

        vm.onUiReady()

        vm.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(localData), awaitItem())
        }
    }
}