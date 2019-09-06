package ru.test.moviesapp.domain

import ru.test.moviesapp.domain.entities.Movie

interface IMovieRepository {

    suspend fun getMovies(page: Int): List<Movie>
}