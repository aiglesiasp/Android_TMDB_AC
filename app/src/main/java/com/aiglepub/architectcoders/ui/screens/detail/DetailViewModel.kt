package com.aiglepub.architectcoders.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed interface DetailAction {
    data object FavoriteClick: DetailAction
    data object MessageShown: DetailAction
}


class DetailViewModel(
    private val id: Int,
    private val repository: MoviesRepository
): ViewModel() {
    //var state by mutableStateOf(UiState())
    //    private set

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    data class UiState(
        val loading: Boolean = false,
        val movie: Movie? = null,
        val message: String? = null
    )

    /// EJEMPLO CON EVENTOS
    /*
    sealed interface UiEvent {
        data class ShowMessage(val message: String): UiEvent
    }

    private val _events: Channel<UiEvent> = Channel()
    val events: Flow<UiEvent> = _events.receiveAsFlow()
    */

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            repository.findMovieById(id).collect { movie ->
                _state.value = UiState(loading = false, movie = movie)
            }
            //_state.value = UiState(loading = false, movie = repository.findMovieById(id))
        }
    }

    ///Creando interface para las acciones
    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.FavoriteClick -> onFavoriteClick()
            is DetailAction.MessageShown -> onMessageShown()
        }
    }

    private fun onFavoriteClick() {
       // _events.trySend(UiEvent.ShowMessage("Favorite clicked"))
        _state.update { it.copy(message = "Favorite clicked") }
    }

    private fun onMessageShown() {
        _state.update { it.copy(message = null) }
    }
}