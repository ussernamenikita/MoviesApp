package com.movie.app.popular.data

import com.movie.app.ErrorCodes
import com.movie.app.domain.entities.DomainResponse
import com.movie.app.domain.entities.ErrorResponse
import com.movie.app.domain.entities.Movie
import com.movie.app.domain.entities.SuccessResponse
import com.movie.app.popular.domain.IPopularMoviesRepository
import ru.movie.app.network.IErrorRecognizer
import ru.movie.app.network.MovieApi
import java.text.SimpleDateFormat
import java.util.*

class PopularMoviesRepositoryImpl(
    private val api: MovieApi,
    private val errorRecognizer: IErrorRecognizer,
    currentLocale: Locale
) : IPopularMoviesRepository {
    /**
     * For release date format
     */
    private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", currentLocale)

    /**
     * Get popular movies
     * @return [SuccessResponse] with movies list if success,
     * or [ErrorResponse] with appropriate error code if fails
     */
    override suspend fun getMovies(): DomainResponse<List<Movie>> {
        val networkResponse = api.getPopularMovies(0)
        val body = networkResponse.body()
        return if (networkResponse.isSuccessful && body != null) {
            SuccessResponse(body.results.map { it.toDomain(simpleDateFormat) })
        }else if(body == null){
            ErrorResponse(ErrorCodes.EMPTY_RESULTS)
        }else{
            ErrorResponse(errorRecognizer.getErrorCode(networkResponse))
        }
    }

}

fun ru.movie.app.network.response.Movie.toDomain(simpleDateFormat: SimpleDateFormat): Movie {
    return Movie(
        posterUrl = poster_path,
        description = overview,
        title = title,
        date = simpleDateFormat.parse(release_date)
    )
}