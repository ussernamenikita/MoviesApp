package com.movie.app.domain.entities

sealed class DomainResponse<T>
class SuccessResponse<T>(val value:T) : DomainResponse<T>()
class ErrorResponse<T>(val errorCode:Int,val errorMsg:String? = null):DomainResponse<T>()