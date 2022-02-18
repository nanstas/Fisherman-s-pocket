package com.nanoshkin.fishermanspocket.data.exceptions

import java.io.IOException

sealed class AppException(var code: String) : RuntimeException() {
    companion object {
        fun from(e: Throwable): AppException = when (e) {
            is AppException -> e
            is IOException -> ServerException
            else -> UnknownException
        }
    }
}

class ApiException(val statusCode: Int, code: String) : AppException(code)
object ServerException : AppException("error_server")
object UnknownException : AppException("error_unknown")
