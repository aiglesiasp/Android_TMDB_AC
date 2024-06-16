package com.aiglepub.architectcoders.ui.screens.home

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.aiglepub.architectcoders.R
import com.aiglepub.architectcoders.data.Movie
import com.aiglepub.architectcoders.ui.ScreenAppTheme
import com.aiglepub.architectcoders.ui.common.LoadingProgressIndicator
import com.aiglepub.architectcoders.ui.common.PermissionRequestEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onClick: (Movie) -> Unit,
               vm: HomeViewModel
) {
    val homeState = rememberHomeState()
    ///Comprobar la region del telefono
    PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) {
        vm.onUiReady()
    }
    
    ScreenAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    scrollBehavior = homeState.scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(homeState.scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { paddingValues ->
            val state by vm.state.collectAsState()

            if(state.loading) {
               LoadingProgressIndicator(modifier = Modifier.padding(paddingValues))
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 4.dp),
                contentPadding = paddingValues
            ) {
                items(state.movies) { movie ->
                    MovieItem(
                        movie = movie,
                        onClick = { onClick(movie) }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = movie.poster,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .clip(MaterialTheme.shapes.medium)
        )
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun MovieItem_Preview() {
    MovieItem(
        movie = Movie(
            id = 1,
            title = "Titulo",
            overview = "",
            releaseDate = "",
            poster = "",
            backdrop = "",
            originalTitle = "",
            originalLanguage = "",
            voteAverage = 0.0,
            popularity = 0.0,
            ),
        onClick = {})
}
