package id.sharekom.moviedbapp.data.paging.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.sharekom.moviedbapp.data.model.review.ReviewsResult
import id.sharekom.moviedbapp.data.remote.ApiService

class ReviewsPagingSource(private val apiService: ApiService, private val movieId: Int): PagingSource<Int, ReviewsResult>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewsResult> {
        return try {
            val currentPage = params.key
            val response = apiService.getReviews(movieId)
            val data = response.body()?.results ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = null, // Only paging forward.
                nextKey = currentPage?.plus(1)
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ReviewsResult>): Int? {
        return state.anchorPosition
    }
}