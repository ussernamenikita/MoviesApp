package ru.movie.app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkApi {

    /**
     * Build new retrofit instance
     */
    fun getAPI(): MovieApi {
        return Retrofit
            .Builder()
            .baseUrl("https://api.themoviedb.org/3")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
}