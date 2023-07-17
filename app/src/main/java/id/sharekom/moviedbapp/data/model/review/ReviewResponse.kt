package id.sharekom.moviedbapp.data.model.review

data class ReviewResponse(
    val id: Int? = null,
    val page: Int? = null,
    val results: List<ReviewsResult>? = null,
    val total_pages: Int? = null,
    val total_results: Int? = null
)