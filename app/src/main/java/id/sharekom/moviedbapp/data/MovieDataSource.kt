package id.sharekom.moviedbapp.data

import androidx.paging.PagingData
import id.sharekom.moviedbapp.data.model.detailmovie.DetailMovieResponse
import id.sharekom.moviedbapp.data.model.genres.Genre
import id.sharekom.moviedbapp.data.model.movie.MovieResult
import id.sharekom.moviedbapp.data.model.review.ReviewsResult
import id.sharekom.moviedbapp.data.model.video.VideoResponse
import kotlinx.coroutines.flow.Flow

interface MovieTvDataSource {
    // Movie Genres
    fun getMovieGenres() : Flow<PagingData<Genre>>

    // Movies
    fun getMoviesByGenre(genreId: Int) : Flow<PagingData<MovieResult>>

    // DetailMovie
    fun getDetailMovie(movieId: Int): Flow<DetailMovieResponse?>

    // Video
    fun getVideo(movieId: Int): Flow<VideoResponse?>

    // Reviews
    fun getReviews(movieId: Int): Flow<PagingData<ReviewsResult>>
}