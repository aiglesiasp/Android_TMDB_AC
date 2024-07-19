package com.aiglepub.architectcoders.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed interface DetailAction {
    data object FavoriteClick: DetailAction
    //data object MessageShown: DetailAction
}


class DetailViewModel(
    id: Int,
    private val repository: MoviesRepository
): ViewModel() {
    //STATE ANTIGUO
    //var state by mutableStateOf(UiState())
    //    private set

    //STATE NUEVO
    //private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    //val state: StateFlow<UiState> = _state.asStateFlow()

    //TRANSFORMANDO STATE
    val state: StateFlow<UiState> = repository.findMovieById(id)
        .map { movie -> UiState(movie = movie) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(loading = true)
        )


    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null,
        //val message: String? = null
    )

    /// EJEMPLO CON EVENTOS
    /*
    sealed interface UiEvent {
        data class ShowMessage(val message: String): UiEvent
    }

    private val _events: Channel<UiEvent> = Channel()
    val events: Flow<UiEvent> = _events.receiveAsFlow()
    */

   /* init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            repository.findMovieById(id).collect { movie ->
                _state.value = UiState(loading = false, movie = movie)
            }
            //_state.value = UiState(loading = false, movie = repository.findMovieById(id))
        }
    }*/

    ///Creando interface para las acciones
    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.FavoriteClick -> onFavoriteClick()
           // is DetailAction.MessageShown -> onMessageShown()
        }
    }

    private fun onFavoriteClick() {
        //_events.trySend(UiEvent.ShowMessage("Favorite clicked"))
        //_state.update { it.copy(message = "Favorite clicked") }

        state.value.movie?.let { movie ->
            viewModelScope.launch {
                repository.toggleFavorite(movie)
            }
        }
    }

    /*private fun onMessageShown() {
        _state.update { it.copy(message = null) }
    }*/
}