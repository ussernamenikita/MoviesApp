package ru.movie.app.network

import com.movie.app.ErrorCodes
import retrofit2.Response

/**
 * Util class which can recognize error code
 * from different objects.
 * ErrorCodes will be on of [ErrorCodes] constants
 */
interface IErrorRecognizer {
    fun <T> getErrorCode(response:Response<T>):Int
    fun getErrorCode(response:okhttp3.Response):Int
    fun getErrorCode(throwable: Throwable):Int
}

/**
 * Simple stub implementation which always return [ErrorCodes.UNKNOWN_ERROR]
 */
class ErrorRecognizerStubImplementation():IErrorRecognizer{
    override fun <T> getErrorCode(response: Response<T>): Int  = ErrorCodes.UNKNOWN_ERROR

    override fun getErrorCode(response: okhttp3.Response): Int = ErrorCodes.UNKNOWN_ERROR

    override fun getErrorCode(throwable: Throwable): Int = ErrorCodes.UNKNOWN_ERROR

}