package com.aiglepub.architectcoders.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.aiglepub.architectcoders.ui.screens.detail.DetailScreen
import com.aiglepub.architectcoders.ui.screens.home.HomeScreen


///METODO DE NAVEGACION CON NAVIGATION COMPOSE
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home ) {
        composable<Home>{
            HomeScreen(onClick = { movie -> navController.navigate(Detail(movie.id)) })
        }

        composable<Detail>(
        ) {backStackEntry ->
            val detail = backStackEntry.toRoute<Detail>()
            DetailScreen(onBack = { navController.popBackStack(route = Home, inclusive = false)})
        }
    }
}

/// METODO ANTIGUO DE NAVEGACION
/*
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home" ) {
        composable("home") {
            HomeScreen(
                vm = viewModel { HomeViewModel() },
                onClick = {movie ->
                    navController.navigate("detail/${movie.id}")
                }
            )
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
 */




/// METODO DE NAVEGACION CON SEALED CLASS
/*
sealed class NavScreen(val route: String) {
    data object Home : NavScreen("home")
    data object Detail : NavScreen("detail/{${NavArgs.MovieId.key}}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
}

enum class NavArgs(val key: String) {
    MovieId("movieId")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.Home.route ) {
        composable(NavScreen.Home.route) {
            HomeScreen(
                vm = viewModel { HomeViewModel() },
                onClick = {movie ->
                    navController.navigate(NavScreen.Detail.createRoute(movie.id))
                }
            )
        }

        composable(
            route = NavScreen.Detail.route,
            arguments = listOf(navArgument(NavArgs.MovieId.key) { type = NavType.IntType})
        ) {backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt(NavArgs.MovieId.key)
            requireNotNull(movieId)
            DetailScreen(
                vm = viewModel { DetailViewModel(movieId) } ,
                onBack = { navController.popBackStack()}
            )
        }
    }
}
*/

