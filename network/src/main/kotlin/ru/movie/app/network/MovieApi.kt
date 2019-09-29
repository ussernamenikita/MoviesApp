package ru.movie.app.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.movie.app.network.response.PopularMoviesResponse
import ru.movies.network.BuildConfig

interface MovieApi {

    @GET("/movie/popular?api_key=${BuildConfig.apiKey}")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<PopularMoviesResponse>
}