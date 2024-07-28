package com.aiglepub.architectcoders.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aiglepub.architectcoders.Result
import com.aiglepub.architectcoders.domain.entities.Movie
import com.aiglepub.architectcoders.domain.usecases.FetchMoviesUseCase
import com.aiglepub.architectcoders.stateAsResultIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fetchMoviesUseCase: FetchMoviesUseCase): ViewModel() {

    private val uiReadyState = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<Result<List<Movie>>> = uiReadyState
        .filter { it }
        .flatMapLatest { fetchMoviesUseCase() }
        .stateAsResultIn(viewModelScope)

    fun onUiReady() {
        uiReadyState.value = true
    }
}