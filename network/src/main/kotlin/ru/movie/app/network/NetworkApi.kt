package ru.movie.app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network api interface.
 * Create specific objects for work with network.
 * Use it like dependencies in components which require
 * network api.
 */
interface NetworkApi {

    /**
     * Build new retrofit instance
     */
    fun getAPI(): MovieApi

    fun getErrorRecognizer():IErrorRecognizer
}

class NetworkApiImpl:NetworkApi{

    /**
     * Build new retrofit instance
     */
    override fun getAPI(): MovieApi{
        return Retrofit
            .Builder()
            .baseUrl("https://api.themoviedb.org/3")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    //TODO Implement real recognizer
    override fun getErrorRecognizer():IErrorRecognizer{
        return ErrorRecognizerStubImplementation()
    }

}
