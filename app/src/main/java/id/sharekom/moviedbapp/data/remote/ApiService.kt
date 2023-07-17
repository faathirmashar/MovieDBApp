package id.sharekom.moviedbapp.data.remote

import id.sharekom.moviedbapp.BuildConfig
import id.sharekom.moviedbapp.data.model.detailmovie.DetailMovieResponse
import id.sharekom.moviedbapp.data.model.genres.GenreResponse
import id.sharekom.moviedbapp.data.model.movie.MovieResponse
import id.sharekom.moviedbapp.data.model.review.ReviewResponse
import id.sharekom.moviedbapp.data.model.video.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Movie Genres
    @GET("genre/movie/list${BuildConfig.API_KEY}")
    suspend fun getMovieGenres(): Response<GenreResponse>

    // Movies
    @GET("discover/movie${BuildConfig.API_KEY}")
    suspend fun getMoviesByGenre(@Query("with_genres") genreId: Int, @Query("page") page: Int?): Response<MovieResponse>

    // Detail Movie
    @GET("movie/{movie_id}${BuildConfig.API_KEY}")
    suspend fun getDetailMovie(@Path("movie_id") movieId: Int): Response<DetailMovieResponse>

    // Videos
    @GET("movie/{movie_id}/videos${BuildConfig.API_KEY}")
    suspend fun getVideos(@Path("movie_id") movieId: Int): Response<VideoResponse>

    // Reviews
    @GET("movie/{movie_id}/reviews${BuildConfig.API_KEY}")
    suspend fun getReviews(@Path("movie_id") movieId: Int): Response<ReviewResponse>
}