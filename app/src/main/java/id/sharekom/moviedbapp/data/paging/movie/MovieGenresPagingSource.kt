package id.sharekom.moviedbapp.data.paging.movie

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.sharekom.moviedbapp.data.model.genres.Genre
import id.sharekom.moviedbapp.data.remote.ApiService

class MovieGenresPagingSource(private val apiService: ApiService): PagingSource<Int, Genre>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Genre> {
        return try {
            val currentPage = params.key
            val response = apiService.getMovieGenres()
            val data = response.body()?.genres ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = null, // Only paging forward.
                nextKey = currentPage?.plus(1)
            )
        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Genre>): Int? {
        return state.anchorPosition
    }
}