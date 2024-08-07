package com.aiglepub.architectcoders.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiglepub.architectcoders.Result
import com.aiglepub.architectcoders.domain.entities.Movie
import com.aiglepub.architectcoders.domain.usecases.FindMovieByIdUseCase
import com.aiglepub.architectcoders.domain.usecases.ToggleFavoriteUseCase
import com.aiglepub.architectcoders.ifSuccess
import com.aiglepub.architectcoders.stateAsResultIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


sealed interface DetailAction {
    data object FavoriteClick: DetailAction
    //data object MessageShown: DetailAction
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    @Named("movieId") id: Int,
    findMovieByIdUseCase: FindMovieByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
): ViewModel() {

    //TRANSFORMANDO STATE
    val state: StateFlow<Result<Movie>> = findMovieByIdUseCase(id)
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
                toggleFavoriteUseCase(movie)
            }
        }
    }
}