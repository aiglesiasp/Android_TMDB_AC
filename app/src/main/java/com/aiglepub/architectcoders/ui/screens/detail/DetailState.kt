package com.aiglepub.architectcoders.ui.screens.detail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
class DetailState(
    val scrollBehavior: TopAppBarScrollBehavior,
    val snackbarHostState: SnackbarHostState
) {

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
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): DetailState {
    //Si cambiar un valor de scroll o snack se vuelve a recargar DetailState, sino no se recargaria nunca
    return remember(scrollBehavior, snackbarHostState) { DetailState(scrollBehavior, snackbarHostState) }
}