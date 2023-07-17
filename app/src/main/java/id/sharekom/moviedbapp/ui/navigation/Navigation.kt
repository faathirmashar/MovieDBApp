package id.sharekom.moviedbapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.sharekom.moviedbapp.ui.screen.DetailMovieScreen
import id.sharekom.moviedbapp.ui.screen.GenresScreen
import id.sharekom.moviedbapp.ui.screen.MoviesScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.GenresScreen.route) {
        composable(route = Screen.GenresScreen.route) {
            GenresScreen(navController = navController)
        }
        composable(
            route = Screen.MoviesScreen.route + "/{genre_id}",
            arguments = listOf(
                navArgument("genre_id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { entry ->
            entry.arguments?.getInt("genre_id")?.let { MoviesScreen(navController = navController, genre_id = it) }
        }
        composable(
            route = Screen.DetailMovieScreen.route + "/{movie_id}",
            arguments = listOf(
                navArgument("movie_id") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { entry ->
            entry.arguments?.getInt("movie_id")?.let { DetailMovieScreen(movieId = it) }
        }
    }
}