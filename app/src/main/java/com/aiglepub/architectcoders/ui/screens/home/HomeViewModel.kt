package com.aiglepub.architectcoders.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.data.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(repository: MoviesRepository): ViewModel() {

    //Variable de Estado Antigua
    //var state by mutableStateOf(UiState())
    //    private set

    // Variable de Estado con Flows
    //private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    //val state: StateFlow<UiState> = _state.asStateFlow()

    //State Flow para hacer esperar el de peticion de peliculas, ya que puede ser que no hagamos aceptado la region
    // y entonces hace la peticion de peliculas antes de tenerlas
    private val uiReadyState = MutableStateFlow(false)

    //DE Flow a StateFlow
    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<UiState> = uiReadyState
        .filter { it }
        .flatMapLatest { repository.movies }
        .map { UiState(movies = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )


    fun onUiReady() {
        /*viewModelScope.launch {
            _state.value = UiState(loading = true)
            repository.movies.collect { listMovies ->
                _state.value = UiState(loading = false, movies = listMovies)
            }
            //_state.value = UiState(loading = false, movies = repository.fetchPopularMovies())
        }*/
        uiReadyState.value = true
    }

    //Modelo de data class de estados
    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )
}