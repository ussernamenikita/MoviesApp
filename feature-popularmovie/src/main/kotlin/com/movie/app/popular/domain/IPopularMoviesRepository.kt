package com.movie.app.popular.domain

import com.movie.app.domain.entities.DomainResponse
import com.movie.app.domain.entities.Movie

interface IPopularMoviesRepository {
    suspend fun getMovies(): DomainResponse<List<Movie>>
}