package com.movie.app.popular.di

import com.movie.app.popular.data.PopularMoviesRepositoryImpl
import com.movie.app.popular.domain.IPopularMoviesRepository
import dagger.Module
import dagger.Provides
import ru.movie.app.network.IErrorRecognizer
import ru.movie.app.network.MovieApi
import java.util.*

@Module
class MainModule{

    @Provides
    fun createRepository(api:MovieApi,
                         errorRecognizer:IErrorRecognizer,
                         currentLocale:Locale):IPopularMoviesRepository{
        return PopularMoviesRepositoryImpl(api = api,
            errorRecognizer = errorRecognizer,
            currentLocale = currentLocale)
    }
}