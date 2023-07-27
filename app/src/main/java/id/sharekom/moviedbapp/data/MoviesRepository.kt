package id.sharekom.moviedbapp.data

import androidx.paging.PagingData
import id.sharekom.moviedbapp.data.model.detailmovie.DetailMovieResponse
import id.sharekom.moviedbapp.data.model.genres.Genre
import id.sharekom.moviedbapp.data.model.movie.MovieResult
import id.sharekom.moviedbapp.data.model.review.ReviewsResult
import id.sharekom.moviedbapp.data.model.video.VideoResponse
import id.sharekom.moviedbapp.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class MoviesRepository constructor(private val remoteDataSource: RemoteDataSource):
    MovieDataSource {
    // Movie Genres
    override fun getMovieGenres(): Flow<PagingData<Genre>> = remoteDataSource.getMovieGenres()

    // Movies
    override fun getMoviesByGenre(genreId: Int): Flow<PagingData<MovieResult>> = remoteDataSource.getMoviesByGenre(genreId)

    // DetailMovie
    override fun getDetailMovie(movieId: Int): Flow<DetailMovieResponse?> = remoteDataSource.getDetailMovie(movieId).flowOn(Dispatchers.IO)

    // Video
    override fun getVideo(movieId: Int): Flow<VideoResponse?> = remoteDataSource.getVideos(movieId).flowOn(Dispatchers.IO)

    // Reviews
    override fun getReviews(movieId: Int): Flow<PagingData<ReviewsResult>> = remoteDataSource.getReviews(movieId)
}