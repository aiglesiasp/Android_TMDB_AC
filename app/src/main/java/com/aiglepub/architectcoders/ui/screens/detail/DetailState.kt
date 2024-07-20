package com.aiglepub.architectcoders.ui.screens.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.aiglepub.architectcoders.Result
import com.aiglepub.architectcoders.data.Movie

@OptIn(ExperimentalMaterial3Api::class)
class DetailState(
    private val state: Result<Movie>,
    val scrollBehavior: TopAppBarScrollBehavior,
    val snackbarHostState: SnackbarHostState
) {

    val movie: Movie?
        get() = (state as? Result.Success)?.data

    val topBarTitle: String
        get() = movie?.title ?: ""

    @Composable
    fun ShowMessageEffect(message: String?, onMessageShown: () -> Unit) {
        LaunchedEffect(message) {
            message?.let {
                snackbarHostState.currentSnackbarData?.dismiss()
                snackbarHostState.showSnackbar(it)
                onMessageShown()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberDetailState(
    state: Result<Movie>,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): DetailState {
    //Si cambiar un valor de scroll o snack se vuelve a recargar DetailState, sino no se recargaria nunca
    return remember(state, scrollBehavior, snackbarHostState) { DetailState(state, scrollBehavior, snackbarHostState) }
}