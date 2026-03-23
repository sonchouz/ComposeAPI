package com.example.networkkit.domain


sealed interface NetworkResult<out T> {
    data object Loading : NetworkResult<Nothing>
    data class Success<out T>(val data: T) : NetworkResult<T>
    data class Error(val exception: NetworkException) : NetworkResult<Nothing>
}

sealed class NetworkException(message: String? = null, cause: Throwable? = null) : Exception(message, cause) {
    class NoInternet(message: String = "Нет интернета") : NetworkException(message)
    class Server(val code: Int, message: String) : NetworkException(message)
    class Unauthorized(message: String = "Не авторизован") : NetworkException(message)
    class Unknown(message: String = "Неизвестная ошибка", cause: Throwable? = null) : NetworkException(message, cause)
}