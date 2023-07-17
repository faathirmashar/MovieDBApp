package id.sharekom.moviedbapp.ui.navigation

sealed class Screen (val route: String) {
    object GenresScreen : Screen("genres_screen")
    object MoviesScreen : Screen("movies_screen")
    object DetailMovieScreen: Screen("detail_movie_screen")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}