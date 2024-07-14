package com.aiglepub.architectcoders.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MoviesRepository
): ViewModel() {

    //Variable de Estado Antigua
    //var state by mutableStateOf(UiState())
    //    private set

    // Variable de Estado con Flows
    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    
    fun onUiReady() {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            repository.movies.collect { listMovies ->
                _state.value = UiState(loading = false, movies = listMovies)
            }
            //_state.value = UiState(loading = false, movies = repository.fetchPopularMovies())
        }
    }

    //Modelo de data class de estados
    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}