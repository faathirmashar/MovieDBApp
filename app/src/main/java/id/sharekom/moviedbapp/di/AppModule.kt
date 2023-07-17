package id.sharekom.moviedbapp.di

import id.sharekom.moviedbapp.BuildConfig
import id.sharekom.moviedbapp.MovieViewModel
import id.sharekom.moviedbapp.data.MovieTvDataSource
import id.sharekom.moviedbapp.data.MoviesTvRepository
import id.sharekom.moviedbapp.data.remote.ApiService
import id.sharekom.moviedbapp.data.remote.RemoteDataSource
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { RemoteDataSource(get()) }
    single <MovieTvDataSource> { MoviesTvRepository(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModelOf(::MovieViewModel)
}

val appModule = listOf(networkModule, repositoryModule, viewModelModule)
