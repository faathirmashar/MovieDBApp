package id.sharekom.moviedbapp.data.model.movie

data class MovieResponse(
    val page: Int? = null,
    val results: List<MovieResult>? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
)