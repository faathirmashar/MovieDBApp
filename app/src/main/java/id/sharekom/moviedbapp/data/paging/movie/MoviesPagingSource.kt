package id.sharekom.moviedbapp.data.paging.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.sharekom.moviedbapp.data.model.movie.MovieResult
import id.sharekom.moviedbapp.data.remote.ApiService

class MoviesPagingSource (private val apiService: ApiService, private val genreId: Int): PagingSource<Int, MovieResult>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getMoviesByGenre(genreId, currentPage)
            val data = response.body()?.results ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = null, // Only paging forward.
                nextKey = currentPage.plus(1)
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResult>): Int? {
        return state.anchorPosition
    }
}