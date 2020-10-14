package com.archonalabs.a24idemo.domain

/**
 * Created by Jakub Juroska on 10/14/20.
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error<out T : Any>(val error: ErrorResult, val data: T? = null) : Result<T>()

    fun isFinished() = this is Success || this is Error

    fun isError() = this is Error

    fun errorOrNull() = when {
        this is Error -> error
        else -> null
    }

    fun isSuccess() = this is Success

    fun getOrNull() = when {
        this is Success -> data
        this is Error -> data
        else -> null
    }
}

open class ErrorResult(open var message: String? = null, open var throwable: Throwable? = null, var code : Int = Constants.NO_DATA)

suspend fun <T : Any> safeCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        call()
    } catch (e: Throwable) {
        Result.Error(ErrorResult(message = errorMessage, throwable = e))
    }
}