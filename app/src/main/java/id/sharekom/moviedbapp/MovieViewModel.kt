package id.sharekom.moviedbapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.sharekom.moviedbapp.data.MovieDataSource
import id.sharekom.moviedbapp.data.model.detailmovie.DetailMovieResponse
import id.sharekom.moviedbapp.data.model.genres.Genre
import id.sharekom.moviedbapp.data.model.movie.MovieResult
import id.sharekom.moviedbapp.data.model.review.ReviewsResult
import id.sharekom.moviedbapp.data.model.video.VideoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MovieViewModel(private val movieDataSource: MovieDataSource) : ViewModel() {
    // Movie Genres
    fun getMovieGenres(): Flow<PagingData<Genre>> = movieDataSource.getMovieGenres().cachedIn(viewModelScope)

    // Movies By Genres
    fun getMoviesByGenre(genreId: Int): Flow<PagingData<MovieResult>> = movieDataSource.getMoviesByGenre(genreId).cachedIn(viewModelScope)

    // Reviews
    fun getReviews(movieId: Int): Flow<PagingData<ReviewsResult>> = movieDataSource.getReviews(movieId).cachedIn(viewModelScope)

    // Detail Movie
    fun getDetailMovie(movieId: Int) : MutableState<DetailMovieResponse?> {
        val detailMovie: MutableState<DetailMovieResponse?> = mutableStateOf(null)

        viewModelScope.launch {
            movieDataSource.getDetailMovie(movieId)
                .collect{ response ->
                    detailMovie.value = response
                }
        }

        return detailMovie
    }

    // Videos
    fun getVideos(movieId: Int): MutableState<VideoResponse?> {
        val video: MutableState<VideoResponse?> = mutableStateOf(null)

        viewModelScope.launch {
            movieDataSource.getVideo(movieId)
                .collect{ response ->
                    video.value = response
                }
        }

        return video
    }
}