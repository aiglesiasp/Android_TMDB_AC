package com.aiglepub.architectcoders.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aiglepub.architectcoders.ui.screens.detail.DetailScreen
import com.aiglepub.architectcoders.ui.screens.home.HomeScreen
import com.aiglepub.architectcoders.ui.data.movies
import com.aiglepub.architectcoders.ui.screens.detail.DetailViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home" ) {
        composable("home") {
            HomeScreen(onClick = {movie ->
                navController.navigate("detail/${movie.id}")
            })
        }

        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType})
        ) {backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            requireNotNull(movieId)
            DetailScreen(
                vm = viewModel { DetailViewModel(movieId) } ,
                onBack = { navController.popBackStack()}
            )
        }
    }
}