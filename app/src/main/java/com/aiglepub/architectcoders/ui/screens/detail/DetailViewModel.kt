package com.aiglepub.architectcoders.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiglepub.architectcoders.Result
import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.data.MoviesRepository
import com.aiglepub.architectcoders.ifSuccess
import com.aiglepub.architectcoders.stateAsResultIn
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed interface DetailAction {
    data object FavoriteClick: DetailAction
    //data object MessageShown: DetailAction
}


class DetailViewModel(
    id: Int,
    private val repository: MoviesRepository
): ViewModel() {

    //TRANSFORMANDO STATE
    val state: StateFlow<Result<Movie>> = repository.findMovieById(id)
        .stateAsResultIn(viewModelScope)

    ///Creando interface para las acciones
    fun onAction(action: DetailAction) {
        when (action) {
            is DetailAction.FavoriteClick -> onFavoriteClick()
        }
    }

    private fun onFavoriteClick() {
        state.value.ifSuccess { movie ->
            viewModelScope.launch {
                repository.toggleFavorite(movie)
            }
        }
    }
}