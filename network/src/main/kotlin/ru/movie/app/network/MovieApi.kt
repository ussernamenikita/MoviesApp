package ru.movie.app.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.movie.app.network.response.PopularMoviesResponse

interface MovieApi {

    @GET("/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): List<PopularMoviesResponse>
}