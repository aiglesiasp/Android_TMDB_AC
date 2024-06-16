package com.aiglepub.architectcoders.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.ui.ScreenAppTheme
import com.aiglepub.architectcoders.ui.common.LoadingProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(vm: DetailViewModel, onBack: () -> Unit) {

    val state by vm.state.collectAsState()
    val lifecycle = LocalLifecycleOwner.current
    val detailState = rememberDetailState()


    detailState.ShowMessageEffect(message = state.message) {
        vm.onAction(DetailAction.MessageShown)
    }
    /// ESCUCHAR EVENTOS
    /*
    LaunchedEffect(vm, lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            vm.events.collect { event ->
                when (event) {
                    is DetailViewModel.UiEvent.ShowMessage -> {
                        //Eliminar lo que hubiera en el SnackBar
                        snackbarHostState.currentSnackbarData?.dismiss()
                        //Show Message
                        snackbarHostState.showSnackbar(event.message)
                    }
                }
            }
        }
    }*/

    ScreenAppTheme {
        Scaffold(
            topBar = {
                DetailTopBar(
                    title = state.movie?.title ?: "",
                    scrollBehavior = detailState.scrollBehavior,
                    onBack = onBack
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { vm.onAction(DetailAction.FavoriteClick) }
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
            },
            snackbarHost = { SnackbarHost(hostState = detailState.snackbarHostState) },
            modifier = Modifier.nestedScroll(detailState.scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { paddingValues ->

            if(state.loading) {
                LoadingProgressIndicator(modifier = Modifier.padding(paddingValues))
            }
            state.movie?.let { movie ->
                MovieDetail(
                    movie = movie,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun MovieDetail(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = movie.backdrop,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 12f)
        )
        Text(
            text = movie.overview,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = buildAnnotatedString {
                Property("Original Title", movie.originalTitle)
                Property("Original Language", movie.originalLanguage)
                Property("Release Date", movie.releaseDate)
                Property("Popularity", movie.popularity.toString())
                Property("Vote Average", movie.voteAverage.toString(), end = true)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp)
        )
    }
}

@Composable
private fun AnnotatedString.Builder.Property(name: String, value: String, end: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if (!end) {
            append("\n")
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailTopBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Arrow back"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}