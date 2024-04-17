package com.aiglepub.architectcoders.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiglepub.architectcoders.ui.data.Movie
import com.aiglepub.architectcoders.ui.data.MoviesRepository
import com.aiglepub.architectcoders.ui.data.movies
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    //Creo Variable de Estado
    var state by mutableStateOf(UiState())
        private set

    private val repository = MoviesRepository()

    fun onUiReady(region: String) {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(loading = false, movies = repository.fetchPopularMovies(region))
        }
    }

    //Modelo de data class de estados
    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )

}