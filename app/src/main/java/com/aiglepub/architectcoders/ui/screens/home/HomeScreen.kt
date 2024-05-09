package com.aiglepub.architectcoders.ui.screens.home

import android.Manifest
import android.content.Context
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
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
import com.aiglepub.architectcoders.ui.common.getRegion
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onClick: (Movie) -> Unit,
               vm: HomeViewModel = viewModel()
) {
    val ctx: Context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    PermissionRequestEffect(permission = Manifest.permission.ACCESS_COARSE_LOCATION) {granted ->
        coroutineScope.launch {
            val region = if(granted) ctx.getRegion() else "US"
            vm.onUiReady(region)
        }

    }

    
    ScreenAppTheme {
        var scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { paddingValues ->

            val state = vm.state
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
    MovieItem(movie = Movie(1, "Titulo", "Poster"), onClick = {})
}
