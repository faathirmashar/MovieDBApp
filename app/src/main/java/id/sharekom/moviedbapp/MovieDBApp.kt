package id.sharekom.moviedbapp

import android.app.Application
import id.sharekom.moviedbapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

import org.koin.core.logger.Level

class MovieDBApp : Application(){
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger(Level.NONE)
            androidContext(this@MovieDBApp)
            modules(appModule)
        }
    }
}