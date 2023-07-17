package id.sharekom.moviedbapp.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.sharekom.moviedbapp.data.model.detailmovie.DetailMovieResponse
import id.sharekom.moviedbapp.data.model.genres.Genre
import id.sharekom.moviedbapp.data.model.movie.MovieResult
import id.sharekom.moviedbapp.data.model.review.ReviewsResult
import id.sharekom.moviedbapp.data.model.video.VideoResponse
import id.sharekom.moviedbapp.data.paging.movie.MovieGenresPagingSource
import id.sharekom.moviedbapp.data.paging.movie.MoviesPagingSource
import id.sharekom.moviedbapp.data.paging.movie.ReviewsPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource (private val apiService: ApiService) {
    fun getMovieGenres(): Flow<PagingData<Genre>> {
        val resultData = Pager(
            PagingConfig(pageSize = 1)
        ) {
            MovieGenresPagingSource(apiService)
        }.flow

        return resultData
    }

    fun getMoviesByGenre(genreId: Int): Flow<PagingData<MovieResult>> {
        val resultData = Pager(
            PagingConfig(pageSize = 1)
        ) {
            MoviesPagingSource(apiService, genreId = genreId)
        }.flow

        return resultData
    }

    fun getDetailMovie(movieId: Int): Flow<DetailMovieResponse?> = flow {
        emit(apiService.getDetailMovie(movieId).body())
    }

    fun getVideos(movieId: Int): Flow<VideoResponse?> = flow {
        emit(apiService.getVideos(movieId).body())
    }

    fun getReviews(movieId: Int): Flow<PagingData<ReviewsResult>> {
        val resultData = Pager(
            PagingConfig(pageSize = 1)
        ){
            ReviewsPagingSource(apiService, movieId)
        }.flow

        return resultData
    }
}